

// SlaveSwapRoles

#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>


#define CE_PIN   9
#define CSN_PIN 10

const int slaveAddress = 0xB00B1E5000LL ;
const int masterAddress = 0xB00B1E50CCLL ;

RF24 radio(CE_PIN, CSN_PIN); // Create a Radio

char dataReceived[10]; // must match dataToSend in master
int replyData = 200; // the two values to be sent to the master
bool newData = false;
byte val;
unsigned long currentMillis;
unsigned long prevMillis;
unsigned long txIntervalMillis = 1000; // send once per second


void setup() {

    Serial.begin(9600);

    Serial.println("SlaveSwapRoles Starting");

    radio.begin();
    radio.setDataRate( RF24_250KBPS );

    radio.openWritingPipe(masterAddress); // NB these are swapped compared to the master
    radio.openReadingPipe(1, slaveAddress);

    radio.setRetries(3,5); // delay, count
    radio.startListening();

}

//====================

void loop() {
    getvaldata();
 byte Value=   showvaldata();
Serial.print("Value now ");
Serial.println(Value);

switch(val)
    {
      case 20:
          for(int i=0;i<10;i++)
          send();
          break;
      
      case 10:
      for(int j=0;j<20;j++)
      {
      getData();
      showData();
      }
      break;

          case 101:
          for(int i=0;i<10;i++)
          break;

    }
}

//====================

void send() {
    if (newData == true) {
        radio.stopListening();
            bool rslt;
            rslt = radio.write( &replyData, sizeof(replyData) );
        radio.startListening();

        Serial.print("Reply Sent ");
        Serial.print(replyData);
        //Serial.print(", ");
        //Serial.println(replyData[1]);

        if (rslt) {
            Serial.println("Acknowledge Received");
            //updateReplyData();
        }
        else {
            Serial.println("Tx failed");
        }
        Serial.println();
        newData = false;
    }
}

//================
void getData() {

    if ( radio.available() ) {
        radio.read( &dataReceived, sizeof(dataReceived) );
        newData = true;
    }
}

void getvaldata() {

    if ( radio.available() ) {
        radio.read( &val, 1 );
        newData = true;
    }
}

//================

byte showvaldata() {
    if (newData == true) {
        Serial.print("Val Data received ");
        Serial.println(val);
        return val;
    }
}

void showData() {
    if (newData == true) {
        Serial.print(" Data received ");
        Serial.println(dataReceived);

    }
}

//================

//void updateReplyData() {
   // replyData[0] -= 1;
    //replyData[1] -= 1;
    //if (replyData[0] < 100) {
      //  replyData[0] = 109;
    //}
    //if (replyData[1] < -4009) {
      //  replyData[1] = -4000;
    //}
//}

