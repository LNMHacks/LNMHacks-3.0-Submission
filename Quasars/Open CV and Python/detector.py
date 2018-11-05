import cv2
import numpy as np
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
cam = cv2.VideoCapture(0)
rec = cv2.face.LBPHFaceRecognizer_create()
rec.read("recognizer//training.yml")
id=0
font = cv2.FONT_HERSHEY_COMPLEX_SMALL
while (True):
    ret, img = cam.read()
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x,y,w,h) in faces:
        cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
        id,conf = rec.predict(gray[y:y+h, x:x+w])
        if(id==1):
            id ="kartikey"
        elif(id==2):
            id ="Gaurav"
        elif(id==3):
            id ="akash"
        elif(id==4):
            id ="Sammy"            
        cv2.putText(img,'OpenCV',(10,500), font, 4,(255,255,255),2,cv2.LINE_AA)
    cv2.imshow("face",img);
    if(cv2.waitKey(1)==ord('q')):
            break;
cam.release()
cv2.destroyAllWindows()
