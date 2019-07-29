#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ArduinoJson.h>

#include <ESP8266HTTPClient.h>
const char* ssid = "Etisalat 4G iModem-9B77";
const char* password = "17265880";
String postData;

int moisture = 0;
int relay = D3;
char incommingByte = ' ';
int threshold = 1;
int sensorValMapped = 0;

StaticJsonDocument<300> JSONencoder;

WiFiServer server(80);

void setup() {

  delay(1000);
  Serial.begin(115200);
  WiFi.mode(WIFI_OFF);        //Prevents reconnection issue (taking too long to connect)
  delay(1000);
  WiFi.mode(WIFI_STA);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  server.begin();

  Serial.print("Use this URL to connect: ");
  Serial.print("http://");
  Serial.print(WiFi.localIP());
  Serial.println("/");

  pinMode(relay, OUTPUT);
  digitalWrite(relay, HIGH);
  sensorValMapped = map(analogRead(A0), 1000, 0, 0, 1000);


}

void loop() {
  HTTPClient http;    //Declare object of class HTTPClient

  http.begin("http://159.122.174.163:31175/api/user/update-moisture");                                     //Specify request destination

  http.addHeader("Content-Type", "application/json"); //Specify content-type header
  String userRequest = "{\"email\":\"testing\", \"password\":\"testing\", \"positionX\": 0, \"positionY\": 0, \"current_moisture\":" + String(sensorValMapped) + "}";


  int httpCode = http.PUT(userRequest); //Send the request

  String payload = http.getString();    //Get the response payload

  Serial.println(httpCode);   //Print HTTP return code
  Serial.println(payload);    //Print request response payload
  DeserializationError error = deserializeJson(JSONencoder, payload);

  String threshold_str = JSONencoder["data"]["moisture_threhold"];
  int threshold = atoi(threshold_str.c_str() );


  sensorValMapped = map(analogRead(A0), 1023, 0, 0, 1000);
  Serial.println(sensorValMapped);

  
  if (sensorValMapped <= threshold ) {
    digitalWrite(relay, LOW);
  }
  else {
    digitalWrite(relay, HIGH);
  }

  http.end();  //Close connection

  delay(1000);  //Post Data at every 5 seconds
}
