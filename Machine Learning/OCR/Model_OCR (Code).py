import os
import zipfile
import random
import shutil
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from shutil import copyfile
import matplotlib.pyplot as plt
import pandas as pd

#Extracting data from google drive
from google.colab import drive
drive.mount('/content/gdrive', force_remount=True)
drive_path = '/content/drive/Shareddrives'

#Assigning source
source_path = '/content/gdrive/Shareddrives/CAPSTONE/DATASET/EXP_10' #If needed can be changed

for number in range(0,10):
  source_path_number = os.path.join(source_path, str(number))

# os.listdir returns a list containing all files under the given path
print(f"There are {len(os.listdir(source_path_0))} images of 0.")
print(f"There are {len(os.listdir(source_path_1))} images of 1.")


# Define root directory
root_dir = '/digits'

# Empty directory to prevent FileExistsError is the function is run several times
if os.path.exists(root_dir):
  shutil.rmtree(root_dir)

def create_train_val_dirs(root_path):

  #Creates directories for the train and test sets
  os.makedirs(root_path)
  train_dir = os.path.join(root_path, 'training')
  val_dir = os.path.join(root_path, 'validation')
  test_dir = os.path.join(root_path, 'testing')

  for number in range(0,10):
    train_number_dir  = os.path.join(train_dir, str(number))
    val_number_dir = os.path.join(val_dir, str(number))
    test_number_dir = os.path.join(test_dir, str(number))
    os.makedirs(train_number_dir)
    os.makedirs(val_number_dir)
    os.makedirs(test_number_dir)

try:
  create_train_val_dirs(root_path=root_dir)
except FileExistsError:
  print("You should not be seeing this since the upper directory is removed beforehand")


# Test your create_train_val_dirs function

for rootdir, dirs, files in os.walk(root_dir):
    for subdir in dirs:
        print(os.path.join(rootdir, subdir))
      
#split_data
def split_data(SOURCE_DIR, TRAINING_DIR, VALIDATION_DIR, TESTING_DIR, SPLIT_SIZE, SPLIT_SIZE_TEST):
  """
  Splits the data into train and test sets

  Args:
    SOURCE_DIR (string): directory path containing the images
    TRAINING_DIR (string): directory path to be used for training
    VALIDATION_DIR (string): directory path to be used for validation
    TESTING_DIR (string): directory path to be used for testing
    SPLIT_SIZE (float): proportion of the dataset to be used for training
    SPLIT_SIZE_TEST (float): proportion of the dataset to be used for testing

  Returns:
    None
  """
  file_list = []


  for file in os.listdir(SOURCE_DIR):
    file_path = os.path.join(SOURCE_DIR,file)
    if(os.path.getsize(file_path) == 0):
      print(file + " is zero length, so ignoring.")
    else:
      file_list.append(file_path)

  train_num_items = int(round(len(file_list) * SPLIT_SIZE, 0))
  train_list = random.sample(file_list, train_num_items)
  remain_list = list(set(file_list) - set(train_list))
  test_num_items = int(round(len(remain_list) * SPLIT_SIZE_TEST/(1-SPLIT_SIZE), 0))
  test_list = random.sample(remain_list, test_num_items)
  val_list = list(set(remain_list) - set(test_list))


  for f in train_list:
    copyfile(f,TRAINING_DIR+os.path.basename(f))
  for f in test_list:
    copyfile(f,TESTING_DIR +os.path.basename(f))
  for f in val_list:
    copyfile(f,VALIDATION_DIR+os.path.basename(f))

  pass

# Define paths

TRAINING_DIR = root_dir+"/training/"
VALIDATION_DIR = root_dir+"/validation/"
TESTING_DIR = root_dir+"/testing/"

for number in range(0,10):
    SOURCE_DIR_NUMBER = source_path + "/" + str(number) +"/"
    TRAINING_NUMBER_DIR = os.path.join(TRAINING_DIR, str(number) +"/")
    VALIDATION_NUMBER_DIR = os.path.join(VALIDATION_DIR, str(number) +"/")
    TESTING_NUMBER_DIR = os.path.join(TESTING_DIR, str(number) +"/")

    # Empty directories in case you run this cell multiple times
    if len(os.listdir(TRAINING_NUMBER_DIR)) > 0:
        for file in os.scandir(TRAINING_NUMBER_DIR):
            os.remove(file.path)
    if len(os.listdir(VALIDATION_NUMBER_DIR)) > 0:
        for file in os.scandir(VALIDATION_NUMBER_DIR):
            os.remove(file.path)
    if len(os.listdir(TESTING_NUMBER_DIR)) > 0:
        for file in os.scandir(TESTING_NUMBER_DIR):
            os.remove(file.path)

    # Define proportion of images used for training
    split_size = .6
    split_size_test = 0.2

    split_data(SOURCE_DIR_NUMBER, TRAINING_NUMBER_DIR, VALIDATION_NUMBER_DIR, 
               TESTING_NUMBER_DIR, split_size, split_size_test)

    print(f"\n\nOriginal {number}'s directory has {len(os.listdir(SOURCE_DIR_NUMBER))} images")


    # Training and validation splits
    print(f"There are {len(os.listdir(TRAINING_NUMBER_DIR))} images of {number} for training")
    print(f"There are {len(os.listdir(VALIDATION_NUMBER_DIR))} images of {number} for validation")
    print(f"There are {len(os.listdir(TESTING_NUMBER_DIR))} images of {number} for testing")


# train_val_generators
def train_val_generators(TRAINING_DIR, VALIDATION_DIR):
  """
  Creates the training and validation data generators

  Args:
    TRAINING_DIR (string): directory path containing the training images
    VALIDATION_DIR (string): directory path containing the testing/validation images

  Returns:
    train_generator, validation_generator - tuple containing the generators
  """

  # Instantiate the ImageDataGenerator class (don't forget to set the rescale argument)
  train_datagen = ImageDataGenerator( rescale = 1.0/255., rotation_range=10, width_shift_range=0.2,
                                     height_shift_range=0.2,shear_range=0.1, zoom_range=0.3,
                                      horizontal_flip=False, fill_mode='nearest')

  # Pass in the appropiate arguments to the flow_from_directory method
  train_generator = train_datagen.flow_from_directory(directory=TRAINING_DIR,
                                                      batch_size=32,
                                                      class_mode='categorical',
                                                      target_size=(32,32))

  # Instantiate the ImageDataGenerator class (don't forget to set the rescale argument)
  validation_datagen = ImageDataGenerator( rescale = 1.0/255. )

  # Pass in the appropiate arguments to the flow_from_directory method
  validation_generator = validation_datagen.flow_from_directory(directory=VALIDATION_DIR,
                                                                batch_size=32,
                                                                class_mode='categorical',
                                                                target_size=(32,32))
  return train_generator, validation_generator

# Test your generators
train_generator, validation_generator = train_val_generators(TRAINING_DIR, VALIDATION_DIR)

#MODEL
def create_model():

  model = tf.keras.models.Sequential([
      tf.keras.layers.Conv2D(32, (3,3), activation='relu', input_shape=(32,32, 3), padding="same"),
      tf.keras.layers.Conv2D(32, (3,3), activation='relu'),
      tf.keras.layers.MaxPooling2D(2,2),
      #tf.keras.layers.Dropout(0.25),
      tf.keras.layers.Conv2D(32, (3,3), activation='relu',padding="same"),
      tf.keras.layers.Conv2D(64, (3,3), activation='relu'),
      tf.keras.layers.MaxPooling2D(2,2),
      #tf.keras.layers.Dropout(0.25),
      tf.keras.layers.Conv2D(64, (3,3), activation='relu',padding="same"),
      tf.keras.layers.Conv2D(128, (3,3), activation='relu'),
      tf.keras.layers.MaxPooling2D(2,2),
      #tf.keras.layers.Dropout(0.25),
      tf.keras.layers.Flatten(),
      tf.keras.layers.Dense(1024, activation='relu'),
      tf.keras.layers.Dense(256, activation='relu'),
      tf.keras.layers.Dense(10, activation='softmax')
  ])

  from tensorflow.keras.optimizers import RMSprop

  model.compile(optimizer=tf.keras.optimizers.Adam(learning_rate=0.0001),
                loss=tf.keras.losses.CategoricalCrossentropy(),
                metrics=['accuracy'])

  return model

model = create_model()

def step_decay(epoch):
  if epoch < 250:
    initial_lrate = 0.0001
    return initial_lrate
  else:
    initial_lrate = 0.0001
    drop = 0.01
    lr = initial_lrate * drop #When the condition is met, learning rate is multiplied with the drop variable
    return lr

lrate = tf.keras.callbacks.LearningRateScheduler(step_decay, verbose = 1) #Schedule, meaning when the epoch starts to change


history = model.fit(train_generator,
                    epochs=550,
                    validation_data=validation_generator,
                    callbacks=[lr_callback, callbacks_list]) #For changing the learning rate as the programm runs overtime


# Plot the chart for accuracy and loss on both training and validation
acc = history.history['accuracy']
val_acc = history.history['val_accuracy']
loss = history.history['loss']
val_loss = history.history['val_loss']

epochs = range(len(acc))

plt.plot(epochs, acc, 'r', label='Training accuracy')
plt.plot(epochs, val_acc, 'b', label='Validation accuracy')
plt.title('Training and validation accuracy')
plt.legend()
plt.figure()

plt.plot(epochs, loss, 'r', label='Training Loss')
plt.plot(epochs, val_loss, 'b', label='Validation Loss')
plt.title('Training and validation loss')
plt.legend()

plt.show()


# Saving the trained model as a Keras H5 file.
import h5py

saved_model_path = "./model_ocr.h5"
model.save(saved_model_path)
