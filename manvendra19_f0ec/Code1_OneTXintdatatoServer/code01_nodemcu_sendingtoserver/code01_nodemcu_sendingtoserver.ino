#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>

SoftwareSerial NodeMCU(D2,D3);

float val;
const char* ssid     = "Manu"; // Your ssid
const char* password = "manu1234"; // Your Password
float temp_celsius = 0;
float temp_fahrenheit = 0;
WiFiServer server(80);
void setup() {
  
 Serial.begin(115200);
 NodeMCU.begin(115200);
 pinMode(D2,INPUT);
 pinMode(D3,OUTPUT);
 pinMode(A0, INPUT);   
Serial.println();
Serial.println();
Serial.print("Connecting to ");
Serial.println(ssid);
WiFi.begin(ssid, password);
while (WiFi.status() != WL_CONNECTED) {
delay(500);
Serial.print(".");
}
Serial.println("");
Serial.println("WiFi is connected");
server.begin();
Serial.println("Server started");
Serial.println(WiFi.localIP());
}
void loop() {
temp_celsius = (analogRead(A0) * 330.0) / 1023.0;   // To convert analog values to Celsius We have 3.3 V on our board and we know that output voltage of LM35 varies by 10 mV to every degree Celsius rise/fall. So , (A0*3300/10)/1023 = celsius
temp_fahrenheit = temp_celsius * 1.8 + 32.0;
Serial.print("  Temperature = ");
Serial.print(temp_celsius);
Serial.print(" Celsius, ");
Serial.print(temp_fahrenheit);
Serial.println(" Fahrenheit");

  if(NodeMCU.available()>0)
  {
    val = NodeMCU.read();
    Serial.println(val);
  }

WiFiClient client = server.available();
client.println("HTTP/1.1 200 OK");
client.println("Content-Type: text/html");
client.println("Connection: close");  // the connection will be closed after completion of the response
client.println("Refresh: 10");  // update the page after 10 sec
client.println();
client.println("<!DOCTYPE HTML>");
client.println("<html>");
client.print("<p style='text-align: center;'><span style='font-size: x-large;'><strong>Hello Pushpendra Ji</strong></span></p>");
client.print("<p style='text-align: center;'><span style='color: #0000ff;'><strong style='font-size: large;'>nRF data= ");
client.println(val);
//client.print("<p style='text-align: center;'><span style='color: #0000ff;'><strong style='font-size: large;'>Temperature (F) = ");
//client.println(temp_fahrenheit);
client.print("</p>");
client.println("</html>");
delay(500);
}
