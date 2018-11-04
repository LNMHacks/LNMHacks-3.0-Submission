#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>

ESP8266WiFiMulti WiFiMulti;
#define WLAN_SSID       "awesome"
#define WLAN_PASS       "awesome12"
// led pin and sensor pin define
#define green_led D5
#define red_led D4 
#define knock_sensor A0  

String Webhooks_key = "hWZoxXEPmEtAuCcB0zOVj_OG3-iJ7fz4KZGxVnfx70D";

void Knock1_Action(String task);
void Knock2_Action(String task);
void Knock3_Action(String task);


int thrshld = 0;           

// constants for tuning
const int not_recognizable = 25;        
const int averagenot_recognizable = 15; 
const int fading_time = 150;     

const int max_Knocks = 20;       
const int max_time = 1200;     

// secret Knock Patterns
int secret_code1[max_Knocks] = {100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // ..     light on
int secret_code2[max_Knocks] = {100, 90, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // ...   light off
int secret_code3[max_Knocks] = {25, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  // ..   .   start music


int knockReadings[max_Knocks];   // When someone knocks this array fills with delays between knocks.
int knock_sensorValue = 0;           // Last reading of the knock sensor.
int recording = false;   // Flag so we remember the programming button setting at the end of the cycle.


void setup()
{

  pinMode(red_led, OUTPUT);
  pinMode(green_led, OUTPUT);
  digitalWrite(red_led, LOW);
  digitalWrite(green_led, LOW);

//calibration of surface
  digitalWrite(red_led, HIGH);

  Serial.begin(115200);

  for (uint8_t t = 4; t > 0; t--)
  {
    Serial.printf("wait %d...\n", t);
    Serial.flush();
    delay(1000);
  }

  for (int c = 0; c <= 20 ; c++)
  {
    int thresho = analogRead(knock_sensor);
    thrshld = thresho + thrshld;
    delay(10);
  }

  thrshld = (thrshld / 20) - 2; 

  Serial.print("Threshold is: "); 
  Serial.println(thrshld);

  digitalWrite(red_led, LOW);
  digitalWrite(green_led, HIGH);
  delay(500);
  digitalWrite(green_led, LOW);

  // Connect to WiFi access point.
  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP("awesome", "awesome12");
  Serial.println("connected and program execution starts");


}

void loop()
{
 while (WiFiMulti.run() != WL_CONNECTED)
  {
    Serial.println("WiFi not Connected.");
    delay(100);
  }
  // Listen for any knock at all.
  knock_sensorValue = analogRead(knock_sensor);

  if (knock_sensorValue >= thrshld)
  {
    listen_secretknocks();
  }
}

// Records the knocks.
void listen_secretknocks()
{
  Serial.println("knock0");
  int i = 0;
  // reset listening array.
  for (i = 0; i < max_Knocks; i++)
  {
    knockReadings[i] = 0;
  }

  int currnt_knocknumber = 0;              
  int startTime = millis();                
  int now = millis();

  delay(fading_time);                               
  do
  {
    //listen for the next knock 
    knock_sensorValue = analogRead(knock_sensor);

    if (knock_sensorValue >= thrshld)                  //next knock...
    {

      Serial.println("knock");
      //record the delay time.
      now = millis();
      knockReadings[currnt_knocknumber] = now - startTime;
      currnt_knocknumber ++;                             
      startTime = now;
      delay(fading_time);                            
    }
    now = millis();

    
  } while ((now - startTime < max_time) && (currnt_knocknumber < max_Knocks));

 
  if (recording == false)          //check the  recorded pattern
  {
    if (validate_knock1() == true)
    {
      Knock1_Action("on");
    }
    else if (validate_knock2() == true)
    {
      Knock2_Action("off");
    }
    else if (validate_knock3() == true)
    {
      Knock3_Action("music");
    }
    else
    {
      Serial.println("incorrect pattern");
      for (int i = 0; i < 3; i++)
      {
        digitalWrite(red_led, HIGH);
        delay(500);
        digitalWrite(red_led, LOW);
        delay(300);
      }
    }
  }
  else
  {

  }

}

boolean validate_knock1()
{
  int i = 0;

  
  int currnt_knockcount = 0;
  int secret_knockcount = 0;
  int maxt_knockintrvl = 0;                

  for (i = 0; i < max_Knocks; i++) {
    if (knockReadings[i] > 0) {
      currnt_knockcount++;
    }
    if (secret_code1[i] > 0) {            
      secret_knockcount++;
    }

    if (knockReadings[i] > maxt_knockintrvl) {  //  normalization of data 
      maxt_knockintrvl = knockReadings[i];
    }
  }

  
  if (recording == true) {
    for (i = 0; i < max_Knocks; i++) {         // normalize the times
      secret_code1[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    }

    return false;   
  }

  if (currnt_knockcount != secret_knockcount) {
    return false;
  }

  
  int totaltimeDifferences = 0;
  int timeDiff = 0;
  for (i = 0; i < max_Knocks; i++) { // Normalize the times
    knockReadings[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    timeDiff = abs(knockReadings[i] - secret_code1[i]);
    if (timeDiff > not_recognizable) { 
      return false;
    }
    totaltimeDifferences += timeDiff;
  }
  
  if (totaltimeDifferences / secret_knockcount > averagenot_recognizable) {
    return false;
  }
  Serial.println("Pattern is Matched..");
  return true;

}

boolean validate_knock2() {
  int i = 0;

  
  int currnt_knockcount = 0;
  int secret_knockcount = 0;
  int maxt_knockintrvl = 0;                
  for (i = 0; i < max_Knocks; i++) {
    if (knockReadings[i] > 0) {
      currnt_knockcount++;
    }
    if (secret_code2[i] > 0) {            
      secret_knockcount++;
    }

    if (knockReadings[i] > maxt_knockintrvl) {  
      maxt_knockintrvl = knockReadings[i];
    }
  }

  
  if (recording == true) {
    for (i = 0; i < max_Knocks; i++) { // normalize the times
      secret_code2[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    }
    return false;   // We don't unlock the door when we are recording a new knock.
  }

  if (currnt_knockcount != secret_knockcount) {
    return false;
  }

  
  int totaltimeDifferences = 0;
  int timeDiff = 0;
  for (i = 0; i < max_Knocks; i++) { // Normalize the times
    knockReadings[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    timeDiff = abs(knockReadings[i] - secret_code2[i]);
    if (timeDiff > not_recognizable) { 
      return false;
    }
    totaltimeDifferences += timeDiff;
  }
 
  if (totaltimeDifferences / secret_knockcount > averagenot_recognizable) {
    return false;
  }
  Serial.println("Pattern is Matched..");
  return true;

}

boolean validate_knock3() {
  int i = 0;

  
  int currnt_knockcount = 0;
  int secret_knockcount = 0;
  int maxt_knockintrvl = 0;                

  for (i = 0; i < max_Knocks; i++) {
    if (knockReadings[i] > 0) {
      currnt_knockcount++;
    }
    if (secret_code3[i] > 0) {           
      secret_knockcount++;
    }

    if (knockReadings[i] > maxt_knockintrvl) {  
      maxt_knockintrvl = knockReadings[i];
    }
  }

 
  if (recording == true) {
    for (i = 0; i < max_Knocks; i++) { // normalize the times
      secret_code3[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    }
    return false;   
  }

  if (currnt_knockcount != secret_knockcount) {
    return false;
  }


  int totaltimeDifferences = 0;
  int timeDiff = 0;
  for (i = 0; i < max_Knocks; i++) { // Normalize the times
    knockReadings[i] = map(knockReadings[i], 0, maxt_knockintrvl, 0, 100);
    timeDiff = abs(knockReadings[i] - secret_code3[i]);
    if (timeDiff > not_recognizable) { 
      return false;
    }
    totaltimeDifferences += timeDiff;
  }
 
  if (totaltimeDifferences / secret_knockcount > averagenot_recognizable) {
    return false;
  }
  Serial.println("Pattern is Matched...");
  return true;

}


void Knock1_Action(String task)
{


  digitalWrite(green_led, HIGH);
  if ((WiFiMulti.run() == WL_CONNECTED))
  {

    HTTPClient http;
    Serial.print("[HTTP] begin...\n");
    http.begin("http://maker.ifttt.com/trigger/" + task + "/with/key/" + Webhooks_key); //HTTP

    Serial.print("[HTTP] GET...\n");
    
    int httpCode = http.GET();
    if (httpCode > 0) {
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
      digitalWrite(green_led,LOW);
      digitalWrite(red_led,HIGH);
      delay(2000);
      digitalWrite(red_led,LOW);       
    }

    http.end();
  }
  digitalWrite(green_led, LOW);
}

void Knock2_Action(String task)
{
  digitalWrite(green_led, HIGH);
  if ((WiFiMulti.run() == WL_CONNECTED))
  {

    HTTPClient http;
    Serial.print("[HTTP] begin...\n");
   
    http.begin("http://maker.ifttt.com/trigger/" + task + "/with/key/" + Webhooks_key); //HTTP

    Serial.print("[HTTP] GET...\n");
    
    int httpCode = http.GET();

    
    if (httpCode > 0) {
    
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
      digitalWrite(green_led,LOW);
      digitalWrite(red_led,HIGH);
      delay(2000);
      digitalWrite(red_led,LOW);       
    }

    http.end();
  }
  digitalWrite(green_led, LOW);
}

void Knock3_Action(String task)
{
  digitalWrite(green_led, HIGH);
  if ((WiFiMulti.run() == WL_CONNECTED))
  {

    HTTPClient http;
    Serial.print("[HTTP] begin...\n");
    http.begin("http://maker.ifttt.com/trigger/" + task + "/with/key/" + Webhooks_key); //HTTP

    Serial.print("[HTTP] GET...\n");
    // start connection and send HTTP header
    int httpCode = http.GET();

    // httpCode will be negative on error
    if (httpCode > 0) {
      // HTTP header has been send and Server response header has been handled
      Serial.printf("[HTTP] GET... code: %d\n", httpCode);

      // file found at server
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println(payload);
      }
    } else {
      Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
      digitalWrite(green_led,LOW);
      digitalWrite(red_led,HIGH);
      delay(2000);
      digitalWrite(red_led,LOW);       
    }

    http.end();
  }
  digitalWrite(green_led, LOW);
}

