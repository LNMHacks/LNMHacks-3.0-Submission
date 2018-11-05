import RPi.GPIO as rp
rp.setmode(rp.BOARD)
rp.setwarnings(False)
import time as t
import datetime as dt
import vlc
import serial
ser = serial.Serial('/dev/ttyUSB0',9600)
#from technician import et,kc,water,land_area,soil_volume
from RPLCD import CharLCD
lcd=CharLCD(cols=16,rows=2,pin_rs=37,pin_e=35,pins_data=[33,31,29,32],numbering_mode=rp.BOARD)
rp.setwarnings(False)
kc=0.25
Et0=4
land_area=0.065
soil_volume=2.925
maxMoist=50
minMoist=35
motor_time=0.446
vol_motor=1/60
Et=kc*Et0*0.001
ET_litres=Et*land_area 
volperperc =vol_motor*motor_time*soil_volume 

def moisture():
    read1=int(ser.readline(),10)
    read2=int(ser.readline(),10)
    return (read1)


def tank():
    read1=int(ser.readline(),10)
    read2=int(ser.readline())
    if(read2>100):
        return 1
    else:
        return 0


def today():
    r=rain()


def display(val2='',val1='SMART IRRIGATION'):
    lcd.clear()
    lcd.write_string(val1)
    lcd.cursor_pos=(1,0)
    lcd.write_string(val2)
    t.sleep(2)


def motor(run_time):
    rp.setup(7,rp.OUT)
    display('Motor Started')
    rp.output(7,0)
    t.sleep(run_time)
    display('Motor Stop')
    rp.output(7,1)

##def alert():
##    i=0
##    while(i<3):
##        instance=vlc.Instance()
##        player=instance.media_player_new()
##        ch='123.wav'
##        media=instance.media_new(ch)
##        media.get_mrl()
##        player.set_media(media)
##        player.play()
##        i=i+1
##        t.sleep(3)
##        

def rain(moistval):
    rp.setup(36,rp.IN)
    r=rp.input(36)
    if(r==1):
        while(rp.input(36)==1):
            display('Its raining')
            t.sleep(5)

        if(r==0):
            display('RAINING STOPS')
            new_moist=moisture()
            rainwater=(new_most-moistval)*volperperc
            display('RainWater(l):{}'.format(rainwater))##how much rainwater              
            curr_moist=new_moist
            #req=
            if(ET_litres>3):#req):
                give_litres=4
                while(tank()!=1):
                    display('Tank Emptys')
                    led()
                rp.output(13,0)
                if(tank()==1):
                    run_time=motorTime*soil_volume*(maxMoist-moistValue)
                    display('Water req(l):{}'.format(give_litres))
                    motor(run_time)
            else:
                display('No water req')
                return
    else:
        return

'''def led_on():
    rp.setmode(rp.BOARD)
    rp.setup(13,rp.OUT)
    rp.output(13,1)'''


while(True):
    lcd.clear()
    display('Welcome')
    moistval=moisture()
    display(moistval)
    h=dt.datetime.now().hour
    m=dt.datetime.now().minute
    if(h==6 and m==0):
        today()
    rain(moistval)
    if(moistval<65):
        while(tank()!=1):
            display('Empty tank')
            print('No water In Tank')
            #malert()
        if(tank()==1):
            print('Tank has enough water')
            display('Tank has Water')
            run_time=5# motorTime*soil_volume*(maxMoist-moistValue)
            motor(run_time)
            ET_litres=4#ET_litres - (vol_motor*run_time)
            display('Given water(l):{}'.format(ET_litres))
    else:
        display('No water req')
    


