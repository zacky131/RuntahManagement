
#include <Firebase.h>
#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>
#include "DHT.h"


#include <Ultrasonic.h>
#include <ESP8266WiFi.h>  
#include <WiFiClient.h>  
#include <time.h>

#define FIREBASE_HOST "alamatFirebaseAnda.firebaseio.com"  //Firebase Project URL goes here without "http:" , "\" and "/"
#define FIREBASE_AUTH "kodeRahasiaFirebaseAnda" //Firebase Database Secret


// Pin
#define DHTPIN 14

// Use DHT11 sensor
#define DHTTYPE DHT11  

// Initialize DHT sensor
DHT dht(DHTPIN, DHTTYPE); 

// defines pins numbers ultrasonic
Ultrasonic ultrasonic(5, 4);   //(trigger, echo) D1, D2

// defines variables
int distance;
int distance_percent;
float hum = 100;
float temp = 0;

int trashbin_size = 73; // in cm, harus di ukur, tiap tempat sampah beda ukuran
int trashbinSize;
int timezone = 7;
int dst = 0;
//Lokasi pemasangan sensor
float latitude = -6.208446;
float longitude = 106.821161;

String ClientName = "Testing";
int h = 6; // urutan folder di firebase (untuk menandakan ID tempat sampah)
String SN = "Sensor001";
String Addr = "MRT Setiabudi";

//Wifi setting!!!!!!!!!!!!!!!!!!!!! ini harus di ubah berdasarkan lokasi test
const char* ssid = "namaSSIDAnda";  
const char* password = "passwordRouterAnda";  


void setup(){  
  Serial.begin(115200);  
  delay(1000);
  
  // Connect to WiFi network  
  Serial.println();  
  Serial.println();  
  Serial.print("Connecting to ");  
  Serial.println(ssid);  
  WiFi.begin(ssid, password);  
  while (WiFi.status() != WL_CONNECTED){  
   delay(500);  
   Serial.print(".");  
  }  
  Serial.println("");  
  Serial.println("WiFi connected");  
  
  // Print the IP address  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());  

  //Setup timestamp
  configTime(timezone * 3600, dst * 0, "pool.ntp.org", "time.nist.gov");
  Serial.println("\nWaiting for time");
  while (!time(nullptr)) {
    Serial.print(".");
    delay(500);
  }
  Serial.println(""); 

  //Connect to firebase
  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);

  // Define initial variable in Firebase database
  // Harus di ubah tiap device akan beda
  Firebase.setString("locations/"+ String(h) + "/SerialNumber",SN);
  Firebase.setString("locations/"+ String(h) + "/ClientName",ClientName);
  Firebase.setString("locations/"+ String(h) + "/Address",Addr);
  Firebase.setFloat("locations/"+ String(h) + "/Latitude",latitude); 
  Firebase.setFloat("locations/"+ String(h) + "/Longitude", longitude); 
  Firebase.setInt("locations/"+ String(h) + "/TrashbinSize", trashbin_size);
  Firebase.setFloat("locations/"+ String(h) + "/Temperature", temp);
  Firebase.setFloat("locations/"+ String(h) + "/Humidity", hum);  
  
}

void firebasereconnect(){
  Serial.println("Trying to reconnect");
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}


void loop(){  
  //time setup
  time_t now = time(nullptr);
  Serial.println(ctime(&now));
  delay(100);
 
  //firebase setup
   if (Firebase.failed()){
      Serial.print("setting number failed:");
      Serial.println(Firebase.error());
      firebasereconnect();
      return;
   }
   
  //read measurement
  distance = ultrasonic.read();
  delay(1000);
  
  // Calculating the distance percentage
  trashbin_size = Firebase.getInt("locations/"+ String(h) + "/TrashbinSize");
  distance_percent = 100 - distance*100/trashbin_size;
  Serial.print("Distance: ");
  Serial.print(distance);
  Serial.println("cm");
  Serial.print("Fill level: ");
  Serial.print(distance_percent);
  Serial.println("%");

    // Reading temperature and humidity
   hum = dht.readHumidity();
  // Read temperature as Celsius
   temp = dht.readTemperature();
  delay(1000);

    // Display data
  Serial.print("Temperature :");  
  Serial.print(temp);  
  Serial.println("C");  
  Serial.print("Humidity :");  
  Serial.print(hum);  
  Serial.println("%"); 
  delay(10);
  
  // Repeat every 1 seconds
  delay(1000);

  Firebase.setString("locations/"+ String(h) + "/Time",ctime(&now));

  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();
  root["Fill Level Raw"] = distance_percent;
  root["Distance Raw"] = distance;
  root["Temperature"] = temp;
  root["Humidity"] = hum;
  root["Time"] = ctime(&now);

  Firebase.push("locations/" + String(h),root);
  Firebase.setFloat("locations/"+ String(h) + "/Temperature",temp);
  Firebase.setFloat("locations/"+ String(h) + "/Humidity",hum);

  // Push data to serial and Firebase
 if (distance >= 0 && distance <= 100){
     Firebase.setInt("locations/"+ String(h) + "/FillLevel",distance_percent);
   
 }
  
 if (distance_percent >= 0 && distance_percent <= 100){
     Firebase.setInt("locations/"+ String(h) + "/TrashDistance",distance); 
      
  }
  
   delay(5000);

} 
