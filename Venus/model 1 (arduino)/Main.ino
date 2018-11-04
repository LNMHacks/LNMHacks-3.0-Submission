 #include<LiquidCrystal.h>
#include "dht.h"

#define soil_sensor  A0
#define temp_sensor A1
#define rain_sensor A2
#define motor 2

LiquidCrystal lcd(0,1,8,9,10,11); // Creates an LC object. Parameters: (rs, enable, d4, d5, d6, d7)

String state = "Rajasthan";
String crop = "Wheat";
float Kc = 0.25;
float ETo = 4;
float land_area = 0.0086;   //In meter square
float soil_volume = 0.00078; //volume of soil
float minMoist = 50,maxMoist = 80;
float motorTime = 0.01686;    //To run for increase in soil moisture by 1% in 1 L land(Vol of Soil)
float volMotor = 0.25;     //Volume Of Water Coming Out Per Sec
float ET = 1.908;   //Kc*ETo;  //It is in mm/day
float ET_litres = ET*land_area;
float volPerPerc = volMotor*motorTime*soil_volume;

int days=1;
float minTemp,maxTemp;
dht DHT;
float moistValue;
int tank_flag=0;
void setup() 
{
    lcd.begin(16,2); // Initializes the interface to the LCD screen, and specifies the dimensions (width and height) of the display 
    lcd.println("Water Irrigation");
    lcd.setCursor(4,1);
    lcd.println("Welcomes You");
    pinMode(rain_sensor, INPUT);
    pinMode(motor, OUTPUT);
    lcd.clear();
    lcd.println("System Ready    ");
    delay(1000);
}

void loop() 
{
     moistValue = 100 - (analogRead(soil_sensor)/1023.00 * 100);
     DHT.read11(temp_sensor);
     lcd.clear();
     lcd.print("Agrarian        ");
     lcd.setCursor(0,1);
     lcd.print("On Progress     ");
     //Check For Rain
     if(analogRead(rain_sensor)>390)        
     {
           lcd.clear();
           lcd.print("Raining         ");
           while(analogRead(rain_sensor)>390);
           lcd.clear();
           lcd.print("Rain Stopped    ");
           delay(1000);
     }
     //Rain Ends Here
     
     //If Crops Need Water
     if(moistValue < minMoist)
     {
          lcd.clear();
          lcd.println("Motor Started   ");
          lcd.setCursor(0,1);
          lcd.print("Avail. Water: ");
          lcd.print(ET_litres);
          lcd.println("L");
          digitalWrite(motor,HIGH);
          int run_time = motorTime*soil_volume*(maxMoist-moistValue)*1000;
          //Serial.print("Run Time: ");
          //Serial.println(run_time);
          /*while(run_time>0)
          {
                delay(1000);
                run_time--;
          }*/
          delay(5000);
          digitalWrite(motor,LOW);
          lcd.clear();
          lcd.println("Motor Stopped   ");
          while(moistValue < minMoist)
          {
              moistValue = 100 - (analogRead(soil_sensor)/1023.00 * 100);
          }
          ET_litres = ET_litres - (volMotor*run_time);
          lcd.clear();
          lcd.print("Given Water: ");
          lcd.print(ET_litres);
          lcd.println("L");
     }
     //Water Need Fullfilled
     
}
