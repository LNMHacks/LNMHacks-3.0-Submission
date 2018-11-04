
import cv2
print('Video: ')
name = raw_input()
cap = cv2.VideoCapture('./'+name)
 
car_cascade = cv2.CascadeClassifier('cars.xml')
 
while True:
    ret, frames = cap.read()
     
    gray = cv2.cvtColor(frames, cv2.COLOR_BGR2GRAY)
     
 
    cars = car_cascade.detectMultiScale(gray, 1.1, 1)
     
    for (x,y,w,h) in cars:
        cv2.rectangle(frames,(x,y),(x+w,y+h),(0,255,0),2)
        
    cv2.putText(frames,str(cars.shape[0]), (50,50),cv2.FONT_HERSHEY_SIMPLEX, 2, 255)
 
    cv2.imshow('video2', frames)
     
    if cv2.waitKey(33) == 27:
        break
 
# De-allocate any associated memory usage
cv2.destroyAllWindows()
