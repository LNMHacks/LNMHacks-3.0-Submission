import paho.mqtt.client as mqtt
""" import RPi.GPIO as GPIO """
import pygame.mixer
pygame.init()
pygame.mixer.music.load("beep-01a.wav")

""" GPIO.setmode(GPIO.BOARD)
GPIO.setup(8, GPIO.OUT, initial=0) """

mqtt_username = ""
mqtt_password = ""
mqtt_topic = ""
mqtt_broker_ip = ""

client = mqtt.Client()

client.username_pw_set(mqtt_username, mqtt_password)


def on_connect(client, userdata, flags, rc):
    print("Connected!", str(rc))
    client.subscribe(mqtt_topic)


def on_message(client, userdata, msg):
    print(msg.payload)
    if(msg.payload):
        pygame.mixer.music.play()


client.on_connect = on_connect
client.on_message = on_message

client.connect(mqtt_broker_ip, 15881)

client.loop_forever()
client.disconnect()
