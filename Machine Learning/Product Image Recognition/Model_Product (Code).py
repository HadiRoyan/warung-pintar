import os
import zipfile
import random
import shutil
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from shutil import copyfile
import matplotlib.pyplot as plt
import pandas as pd

#Extract data from drive
from google.colab import drive
drive.mount('/content/gdrive', force_remount=True)
drive_path = '/content/drive/Shareddrives'

#Sourcing

source_path = '/content/gdrive/Shareddrives/CAPSTONE/DATASET/images/' #KALAU GANTI LOKASI, HARUS GANTI#
train_path = source_path + 'train/'
test_path = source_path + 'test/'

train_path_aqua = os.path.join(train_path, 'aqua')
train_path_chitato = os.path.join(train_path, 'chitato')
train_path_indomie = os.path.join(train_path, 'indomie')
train_path_pepsodent = os.path.join(train_path, 'pepsodent')
train_path_shampoo = os.path.join(train_path, 'shampoo')
train_path_tissue = os.path.join(train_path, 'tissue')

test_path_aqua = os.path.join(test_path, 'aqua')
test_path_chitato = os.path.join(test_path, 'chitato')
test_path_indomie = os.path.join(test_path, 'indomie')
test_path_pepsodent = os.path.join(test_path, 'pepsodent')
test_path_shampoo = os.path.join(test_path, 'shampoo')
test_path_tissue = os.path.join(test_path, 'tissue')


# os.listdir returns a list containing all files under the given path
print(f"There are {len(os.listdir(train_path_aqua))} images of aqua.")
print(f"There are {len(os.listdir(test_path_chitato))} images of chitato.")


# Define the desired image size
target_size = (224, 224)

# Load the images from the train and test directories
train_datagen = tf.keras.preprocessing.image.ImageDataGenerator(rotation_range=10, width_shift_range=0.2, height_shift_range=0.2,
                                          shear_range=0.1, zoom_range=0.3, horizontal_flip=False, fill_mode='nearest')
train_generator = train_datagen.flow_from_directory(
    train_path,
    target_size=target_size,
    batch_size=32,
    class_mode='categorical'
)

test_datagen = tf.keras.preprocessing.image.ImageDataGenerator()
test_generator = test_datagen.flow_from_directory(
    test_path,
    target_size=target_size,
    batch_size=32,
    class_mode='categorical'
)

#preprocessing
preprocess_input = tf.keras.applications.mobilenet_v2.preprocess_input
IMG_SHAPE = target_size + (3,)
base_model = tf.keras.applications.MobileNetV2(input_shape=IMG_SHAPE,
                                               include_top=False,
                                               weights='imagenet')

image_batch, label_batch = next(iter(train_generator))
feature_batch = base_model(image_batch)

base_model.trainable = False

#Creating the model
global_average_layer = tf.keras.layers.GlobalAveragePooling2D()
feature_batch_average = global_average_layer(feature_batch)

prediction_layer = tf.keras.layers.Dense(6, activation = 'softmax')
prediction_batch = prediction_layer(feature_batch_average)

inputs = tf.keras.Input(shape=(224,224, 3))
x = preprocess_input(inputs)
x = base_model(x, training=False)
x = global_average_layer(x)
#x = tf.keras.layers.Dropout(0.2)(x)
outputs = prediction_layer(x)
model = tf.keras.Model(inputs, outputs)

base_learning_rate = 0.0001
model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=base_learning_rate),
              loss=tf.keras.losses.CategoricalCrossentropy(),
              metrics=[tf.keras.metrics.CategoricalAccuracy(name='accuracy')])

def step_decay(epoch):
  if epoch < 50:
    initial_lrate = 0.0001
    return initial_lrate
  else:
    initial_lrate = 0.0001
    drop = 0.01 
    lr = initial_lrate * drop #When the condition is met, learning rate is multiplied with the drop variable
    return lr

lrate = tf.keras.callbacks.LearningRateScheduler(step_decay, verbose = 1) #Schedule, meaning when the epoch starts to change
callbacks_list = [lrate]
history = model.fit(train_generator,
                    epochs=200,
                    validation_data=test_generator,
                    callbacks=[callbacks_list])

# Saving the trained model as a Keras H5 file.
import h5py

saved_model_path = "./model_products.h5"
model.save(saved_model_path)