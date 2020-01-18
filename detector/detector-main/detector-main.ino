//HARDCODED ONTO CONTROLLER
int ID = 1;

//IO SETTINGS
const int button1 = 4;
const int button2 = 13;

// DATA STORES
int detection_info[6];
const int BUTTON1_INFO = 0;
const int BUTTON2_INFO = 1;
const int FULLNESS_INFO = 3;

ICACHE_RAM_ATTR void increment_button1_info()
{
  static unsigned long previous_interrupt_time = 0;
  unsigned long interrupt_time = millis();
  if (interrupt_time - previous_interrupt_time  >= 1000)
  {
    //register button press
    detection_info[BUTTON1_INFO] ++;
    Serial.println(detection_info[BUTTON1_INFO]);
    digitalWrite(LED_BUILTIN, HIGH);
  }
  previous_interrupt_time = interrupt_time;
}

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
  attachInterrupt(digitalPinToInterrupt(button1), increment_button1_info, RISING);
  
  
}

void loop() {

}
