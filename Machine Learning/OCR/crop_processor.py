import os
import shutil
import tensorflow as tf
from google.colab.patches import cv2_imshow
import cv2
from imutils import contours
from skimage.transform import resize
from PIL import Image
import numpy as np

#Load model
loaded_model = tf.keras.models.load_model("./model_ocr.h5")

# Load image, grayscale, Otsu's threshold
image = cv2.imread('./img_00001.jpg') #Input
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
thresh = cv2.threshold(gray,0,255,cv2.THRESH_OTSU + cv2.THRESH_BINARY_INV)[1]

# Find contours, sort from left-to-right, then crop
cnts = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
cnts = cnts[0] if len(cnts) == 2 else cnts[1]
cnts, _ = contours.sort_contours(cnts, method="left-to-right")

ROI_number = 0
# Making a directory for the crop
CROP_DIR = './crop'
os.makedirs(CROP_DIR)
# Cropping image
for c in cnts:
    area = cv2.contourArea(c)
    if area > 10:
        x,y,w,h = cv2.boundingRect(c)
        ROI = 255 - image[y:y+h, x:x+w]
        cv2.imwrite(os.path.join(CROP_DIR,'ROI_{}.png'.format(ROI_number)), ROI)
        cv2.rectangle(image, (x, y), (x + w, y + h), (36,255,12), 2)
        ROI_number += 1


result_prediction= ""

# Predicting each of the cropped image

for num in range(0,len(os.listdir(CROP_DIR))):
  test_image = tf.keras.utils.load_img("ROI_" + str(num)+ ".png")

  # Resizing and formating
  test_image_array = tf.keras.utils.img_to_array(test_image)
  #print(f"Image (before) has shape: {test_image_array.shape}")
  test_array_corrected = resize(test_image_array, (32, 32)).astype(np.uint8)
  #print(f"Image (after) has shape: {test_array_corrected.shape}")
  test_input = np.array([test_array_corrected])

  # Predicting
  predict_datagen = ImageDataGenerator(rescale = 1.0/255.)
  predict_generator = predict_datagen.flow(test_input, batch_size=1)
  prediction = loaded_model.predict(predict_generator,verbose = 0 )

  # Printing result
  result_prediction = result_prediction + "".join(str(x) for x in np.argmax(prediction, axis=1))

shutil.rmtree(CROP_DIR) #Deleting the crop directory since not used anymore
print(result_prediction)
