import cv2
import numpy as np
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
cam = cv2.VideoCapture(0)
id=raw_input("enter user id")
sampleNum =0;

while (True):
    ret, img = cam.read()
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        sampleNum = sampleNum+1
        cv2.imwrite("dataset/user."+str(id)+"."+str(sampleNum))
        cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
        cv2.imshow('face',img);
        if(cv2.waitKey(1)==ord('q')):
            break;
cam.release()
cv2.destroyAllWindows()
