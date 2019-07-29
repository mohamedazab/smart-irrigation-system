# IOT-System

The IOT device is a simple and small unit that is used to monitor the moisture content of one  cell in the grid (part of the field or garden). It consists of two main parts:

<ol>
  <li>The software part</li>
  <li>Hardware part</li>
</ol> 

## Software Part

The software part was implemented using Arduino language, the main functionality of the software part is to process the information collected from the sensor, send it to the server and receive and process the server response to be able to provide the plant with the suitable moisture value. It also gives the comand to open the water pump to irregate the grid cell.

## Hardware Part

The Hardware part contained some simple hardware components:
nodeMCU to send and receive data to the server besides receiving data from the moisture sensor, it also operates the water pump when needed.
Water pump to irriate the grid cell.
Moisture sensor to measure the moisture content of the soil

<ol>
  <li>nodeMCU to send and receive data to the server besides receiving data from the moisture sensor, it also operates the water pump when needed</li>
  <li>Moisture sensor to measure the moisture content of the soil</li>
  <li>Water pump to irriate the grid cell{\li>
</ol> 
The following schematic shows the exact wiring of the sensors and actuators.

![Schematic](https://raw.githubusercontent.com/MoghazyCoder/IOT-System/master/assets/Schema.jpeg)


## System Configuration

To be able to run the system correctly, each device should be configured and the code should be then uploaded.
To configure your device the string     ``` userRequest ``` should be changed where the email and passowrd should match the email and password that you created your account with when signing up our mobile app. the position_x and position_y should also be changed to match the position of the device in the grid (in the field or in the garden)

Also you have to change the ssid and the password as follows
```ssid = "YOUR NETWORK SSID```
```password = "YOUR WIFI MODEM PASSWORD"```

![System after integration](https://github.com/MoghazyCoder/IOT-System/blob/master/assets/IMG_2629.JPG)

