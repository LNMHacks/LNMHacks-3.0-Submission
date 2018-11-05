import cv2
import sys
import os

cam = cv2.VideoCapture(0)

cv2.namedWindow("test")

img_counter = os.environ["a"]

ret, frame = cam.read()
cv2.imshow("test", frame)
img_name = "opencv_frame_{}.jpg".format(img_counter)
pathh=os.getcwd()+'/images/'+img_name
cv2.imwrite(pathh, frame)
print("python pushblob.py "+" "+img_name +" "+os.getcwd()+"/images/{}".format(img_name))

cam.release()

cv2.destroyAllWindows()
