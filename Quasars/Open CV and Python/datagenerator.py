import cv2
import sqlite3
cam = cv2.VideoCapture(0)
detector = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

def insertOrUpdate(Id,Name):
    conn=sqlite3.connect("Database.db")
    cmd = "SELECT * FROM Check WHERE ID ="+str(Id )
    cursor = conn.execute(cmd)
    isRecordExist=0
    for row in cursor:
        isRecordExist =1
    if(isRecordExist ==1):
        cmd = "UPDATE ID SET NAME="+srt(Name)+"WHERE ID="+srt(Id)
    else:
        cmd= "INSERT INTO Check Attendance(ID, NAME) Values("+str(Id)+",'"+str(Name)+"')"
    conn.execute(cmd)
    conn.commit()


id=input('enter your id')
name=input('enter your name')
sampleNum=0
while True:
    ret, im = cam.read()
    gray = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY)
    faces = detector.detectMultiScale(gray, scaleFactor= 1.2, minNeighbors =5, minSize=(30,30))
    for(x,y,w,h) in faces:
        sampleNum = sampleNum+1
        cv2.imwrite("dataset/user."+id+'.'+str(sampleNum)+ ".jpg", gray[y:y+h, x:x+w])
        cv2.rectangle(im, (x-50,y-50),(x+w+50, y+h+50),(255,0,0),2)
    cv2.imshow('im',im)
    cv2.waitKey(100)
    if sampleNum > 20:
        cam.release()
        cv2.destroyAllWindows()
        break
