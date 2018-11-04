#include<SPI.h>                           // Library used to connect arduino with other devices
#include<nRF24L01.h>                      // Library made by TMRH Group for nRF24L01
#include<RF24.h>                          // Library made by TMRH Group for nRF24L01
#include "printf.h"                       // To display the attributes of the nRF


const int pinCE=9;                        // Chip enable is to enable or disable the connected device
const int pinCSN=10;                      // This pin is to tell the device that the sent information is a command or data 

int counter=0;                           // Since, i am sending counter and it is a small int, i am defining it as byte
bool done=false;                          // done is updated to "True" when all the required bytes are sent


RF24 radio(pinCE, pinCSN);                // Creating an object from the RF24 class
const uint64_t pAddress= 0xB00B1E5000LL;   // Creating the pipe address




void setup() 
{
Serial.begin(57600);                      // Using begin function of Serial library to display info on Monitor
printf_begin;                             // Printing details of nrf module

radio.begin();                             // To start the nrf module
radio.setAutoAck(1);                       // To ensure auto acknowledgement is ON
radio.enableAckPayload();                 //
radio.setRetries(5,15);
radio.openWritingPipe(pAddress);
radio.stopListening();
radio.printDetails();

}

void loop() 
{

  if(!done)                                       // Last counter has not been sent yet
  {
    Serial.print(" Now send packet number : ");
    Serial.println(counter);                      // To print the packet number

      unsigned long time1 = micros();             // Starting time, before sending
      if(!radio.write(&counter,2))                // If sending was not possible
      {
      Serial.println(" Packet delivery failed "); // Display,delivery was failed
      }
      else
      {
      unsigned long time2 = micros();             // Otherwise, note the time when the acknowledgement has come
      time2 = time2 - time1;                      // This is trip time   
      Serial.print(" Time from message sent to receive acknowledgement : ");
      Serial.print(time2);
      Serial.println("microseconds");
      counter++;                                  // Increase counter for next iteration
      if(counter==200)
      counter=0;
      }
  delay(5);
        while(radio.available())                  // If there is anything in the radio
        {
          char gotChars[5];
          radio.read (gotChars,5);                // Store it in gotchar
          Serial.print(gotChars[0]);               // Print gotchar
          Serial.print(gotChars[1]);
          Serial.print(gotChars[2]);
          Serial.print(gotChars[3]);

          done=true;
        }                                                     
    
    }
delay(10);
}  
  

