# IOT-System

The IOT device is a simple and small unit that is used to monitor the moisture content of one  cell in the grid (part of the field or garden). It consists of two main parts:

1. The software part
2. Hardware part 

## Software Part

The software part was implemented using Arduino language, the main functionality of the software part is to process the information collected from the sensor, send it to the server and receive and process the server response to be able to provide the plant with the suitable moisture value. It also gives the comand to open the water pump to irregate the grid cell.

## Hardware Part

The Hardware part contained some simple hardware components:
nodeMCU to send and receive data to the server besides receiving data from the moisture sensor, it also operates the water pump when needed.
Water pump to irriate the grid cell.
Moisture sensor to measure the moisture content of the soil

1. nodeMCU to send and receive data to the server besides receiving data from the moisture sensor, it also operates the water pump when needed
2. Moisture sensor to measure the moisture content of the soil
3. Water pump to irriate the grid cell

The following schematic shows the exact wiring of the sensors and actuators.

![Schema](https://user-images.githubusercontent.com/25390378/62079544-3bfb5b80-b24f-11e9-8ae1-28f208748646.jpeg)

## System Configuration

To be able to run the system correctly, each device should be configured and the code should be then uploaded.
To configure your device the string     ``` userRequest ``` should be changed where the email and passowrd should match the email and password that you created your account with when signing up our mobile app. the position_x and position_y should also be changed to match the position of the device in the grid (in the field or in the garden)

Also you have to change the ssid and the password as follows `ssid = "YOUR NETWORK SSID"` and `password = "YOUR WIFI MODEM PASSWORD"`

![IMG_2629](https://user-images.githubusercontent.com/25390378/62079994-10c53c00-b250-11e9-9494-2f102a533427.JPG)

