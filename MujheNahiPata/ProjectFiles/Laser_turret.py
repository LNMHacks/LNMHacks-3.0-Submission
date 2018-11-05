import RPi.GPIO as gp
import time
gp.setmode(gp.BCM)
gp.setup(4,gp.OUT)
gp.setup(14,gp.OUT)
gp.setup(21,gp.OUT)
p = gp.PWM(4,50)
q = gp.PWM(14,50)
p.start(0)
q.start(0)
def set(x,y):
        dutyx = x/18 + 2
        dutyy = y/18 + 2
        gp.output(4,1)
        p.ChangeDutyCycle(dutyx)
        gp.output(4,0)
        time.sleep(1)
        gp.output(14,1)
        q.ChangeDutyCycle(dutyy)
        gp.output(14,0)
        time.sleep(1)
        #p.ChangeDutyCycle(0)
        #p.stop()
        #gp.cleanup()

x = int(input("X > "))
y = int(input("Y > "))
set(x,y)
gp.output(21,1)
time.sleep(5)
gp.output(21,0)
p.stop()
q.stop()
gp.cleanup()
