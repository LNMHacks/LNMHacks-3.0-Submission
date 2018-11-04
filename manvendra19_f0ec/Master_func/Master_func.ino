// MasterSwapRoles

#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>

void send();
void showData();
void getData();
byte val;
#define CE_PIN   9
#define CSN_PIN 10
byte node;
const int slaveAddress = 0xB00B1E5000LL;
const int masterAddress[2] = {0xB00B1E50AALL,0xB00B1E50CCLL};


RF24 radio(CE_PIN, CSN_PIN); // Create a Radio

char dataToSend[10] = "Message 0";
char txNum = '0';
int dataReceived[2]={0,0}; // to hold the data from the slave - must match replyData[] in the slave
bool newData = false;

unsigned long currentMillis;
unsigned long prevMillis;
unsigned long txIntervalMillis = 1000; // send once per second

//============

void setup() {

    Serial.begin(9600);

    Serial.println("MasterSwapRoles Starting");

    radio.begin();
    radio.setDataRate( RF24_250KBPS );

    radio.openWritingPipe(slaveAddress);
    radio.openReadingPipe(1, masterAddress[0]);
    radio.openReadingPipe(2, masterAddress[1]);
    
    
    
    radio.setRetries(3,5); // delay, count
    send(); // to get things started
    prevMillis = millis(); // set clock
}

//=============

void loop() {
    if(Serial.available()>0)
    val = Serial.parseFloat();

    sendval(val);

    

    
    switch(val)
    {
      case 10:
          for(int i=0;i<10;i++)
          send();
          break;
      
      case 20:
      for(int j=0;j<20;j++)
      {
      getData();
      //showData();
      }
      break;

      case 101:
      for(int j=0;j<20;j++)
      {
      getData();
      //showData();
      }
      break;
    
    }
}


void sendval(byte val) {

        radio.stopListening();
        bool rslt;
        rslt = radio.write( &val, 1 );
        radio.startListening();
        Serial.print("Data Sent ");
        Serial.print(val);
        if (rslt) 
        {
            Serial.println("  Acknowledge received");
        }
        else {
            Serial.println("  Tx failed");
        }
}
//====================

void send() {

        radio.stopListening();
            bool rslt;
            rslt = radio.write( &dataToSend, sizeof(dataToSend) );
        radio.startListening();
        Serial.print("Data Sent ");
        Serial.print(dataToSend);
        if (rslt) {
            Serial.println("  Acknowledge received");
            updateMessage();
        }
        else {
            Serial.println("  Tx failed");
        }
}

//================

void getData() {
    
    if ( radio.available(&node) ) {
        if(node==1)
        {
        radio.read( &dataReceived[1], sizeof(dataReceived[1]) );
        //Serial.print("Data received from node ");
        //Serial.print(node);
        //Serial.print("is ");
        Serial.println(dataReceived[1]);
        Serial.println();
        }
        //delay(30);
        else if(node==2)
        {
        radio.read( &dataReceived[2], sizeof(dataReceived[2]) );
        Serial.println(dataReceived[2]);
        //Serial.print("Data received from node ");
        //Serial.print(node);
        //Serial.print("is ");
        //Serial.println(dataReceived[node]);
        Serial.println();
        }
    }
    
}

//================

void showData() {
    if (newData == true) {
        Serial.print("Data received from node ");
        Serial.print(node);
        Serial.print("is ");
        Serial.println(dataReceived[node]);
        Serial.println();
        newData = false;
        delay(40);
    }
}

//================

void updateMessage() {
        // so you can see that new data is being sent
    txNum += 1;
    if (txNum > '9') {
        txNum = '0';
    }
    dataToSend[8] = txNum;
}

