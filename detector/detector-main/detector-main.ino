#include <ESP8266WiFi.h>
#include <ESP8266mDNS.h>

//HARDCODED ONTO CONTROLLER
const int ID = 15;
const int FULLNESS_MAX = 55;
const int ITEMS_MAX = 31;
const char WiFiSSID[] = "Brendan's iPhone";
const char WiFiPSK[] = "88888888";

//IO SETTINGS
const int LEDPIN = 5;
const int BUTTON1PIN = 4;
const int BUTTON2PIN = 13;
const int BUTTON3PIN = 15;
const int FULLNESSTRIGPIN = 12;
const int FULLNESSECHOPIN = 16;
const int ITEMSTRIGPIN = 2;
const int ITEMSECHOPIN = 14;

// DATA STORES
int detection_info[6];
const int ID_INFO = 0;
const int FULLNESS_INFO = 1;
const int BUTTON1_INFO = 2;
const int BUTTON2_INFO = 3;
const int BUTTON3_INFO = 4;
const int ITEMS_INFO = 5;

// GLOBAL VARIABLES
WiFiServer server(80);
unsigned long previous_items_time = 0;
unsigned long previous_read_time = 0;
int items_distance;

//INTERRUPTS
ICACHE_RAM_ATTR void increment_button1_info()
{
  static unsigned long previous_interrupt_time1 = 0;
  unsigned long interrupt_time = millis();
  if (interrupt_time - previous_interrupt_time1  >= 1000)
    detection_info[BUTTON1_INFO] ++;
  previous_interrupt_time1 = interrupt_time;
}
ICACHE_RAM_ATTR void increment_button2_info()
{
  static unsigned long previous_interrupt_time2 = 0;
  unsigned long interrupt_time = millis();
  if (interrupt_time - previous_interrupt_time2  >= 1000)
    detection_info[BUTTON2_INFO] ++;
  previous_interrupt_time2 = interrupt_time;
}
ICACHE_RAM_ATTR void increment_button3_info()
{
  static unsigned long previous_interrupt_time3 = 0;
  unsigned long interrupt_time = millis();
  if (interrupt_time - previous_interrupt_time3  >= 1000)
    detection_info[BUTTON3_INFO] ++;
  previous_interrupt_time3 = interrupt_time;
}

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(BUTTON1PIN, INPUT);
  pinMode(BUTTON2PIN, INPUT);
  pinMode(BUTTON3PIN, INPUT);
  pinMode(FULLNESSTRIGPIN, OUTPUT);
  pinMode(FULLNESSECHOPIN, INPUT);
  pinMode(ITEMSTRIGPIN, OUTPUT);
  pinMode(ITEMSECHOPIN, INPUT);
  attachInterrupt(digitalPinToInterrupt(BUTTON1PIN), increment_button1_info, RISING);
  attachInterrupt(digitalPinToInterrupt(BUTTON2PIN), increment_button2_info, RISING);
  attachInterrupt(digitalPinToInterrupt(BUTTON3PIN), increment_button3_info, RISING);
  
  detection_info[ID_INFO] = ID;
  connectWiFi();
  server.begin();
  setupMDNS();
}

void loop() {
  //Update items count
  unsigned long items_time = millis();
  if (items_time - previous_read_time  >= 100)
  {  
    items_distance = readUS(ITEMSTRIGPIN, ITEMSECHOPIN);
    Serial.println(items_distance);
    previous_read_time = items_time;
  }
  if (items_time - previous_items_time  >= 1000)
  {
    if (abs(items_distance - ITEMS_MAX) > 10)
    {
      detection_info[ITEMS_INFO] ++;
      previous_items_time = items_time;
    }
  }
  
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();

  // Prepare the response
  String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\n";

  // Match the request
  bool with_reset = (req.indexOf("/detector_data_reset") != -1);
  if ((req.indexOf("/detector_data") != -1) || with_reset)
  {
    // Collect fullness data and summarise all data
    detection_info[FULLNESS_INFO] = 100- ((100*readUS(FULLNESSTRIGPIN, FULLNESSECHOPIN))/FULLNESS_MAX);
    s += summarise_data();
    if (with_reset)
    {
      detection_info[BUTTON1_INFO] = 0;
      detection_info[BUTTON2_INFO] = 0;
      detection_info[BUTTON3_INFO] = 0;
      detection_info[ITEMS_INFO] = 0;
    }
  }
  s += "</html>\n";
  client.flush();
  client.print(s);
  delay(1);
  Serial.println("Client disonnected");
  
}

String summarise_data() {
  String summary = "";
  for (int i=0; i<6; i++)
  {
    summary += String(detection_info[i]);
    summary += "<br>";
  }
  return summary;
}

//ULTRASONIC FUNCTIONS
int readUS(int trigPin, int echoPin)
{
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  long duration = pulseIn(echoPin, HIGH);
  return (duration*0.034/2);
}

//WIFI FUNCTIONS
void connectWiFi()
{
  byte ledStatus = LOW;
  Serial.println();
  Serial.println("Connecting to: " + String(WiFiSSID));
  // Set WiFi mode to station (as opposed to AP or AP_STA)
  WiFi.mode(WIFI_STA);

  // WiFI.begin([ssid], [passkey]) initiates a WiFI connection
  // to the stated [ssid], using the [passkey] as a WPA, WPA2,
  // or WEP passphrase.
  WiFi.begin(WiFiSSID, WiFiPSK);

  // Use the WiFi.status() function to check if the ESP8266
  // is connected to a WiFi network.
  while (WiFi.status() != WL_CONNECTED)
  {
    digitalWrite(LEDPIN, ledStatus); // Write LED high/low
    ledStatus = (ledStatus == HIGH) ? LOW : HIGH;
    delay(100);
  }
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void setupMDNS()
{
  // Call MDNS.begin(<domain>) to set up mDNS to point to
  // "<domain>.local"
  if (!MDNS.begin("thing")) 
  {
    Serial.println("Error setting up MDNS responder!");
    while(1) { 
      delay(1000);
    }
  }
  Serial.println("mDNS responder started");
}
