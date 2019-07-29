# Smart Irrigation System

### Modules (in orphaned branches)
  - [Web server with Django](https://github.com/mohamedazab/smart-irrigation-system/tree/Server)
  - [Android Mobile App](https://github.com/mohamedazab/smart-irrigation-system/tree/Android-App)
  - [Machine Learning model with Tensorflow](https://github.com/mohamedazab/smart-irrigation-system/tree/ML-models)
  - [Arduino C++ code for NodeMCU IOT device](https://github.com/mohamedazab/smart-irrigation-system/tree/IoT-system)

### Integrated features:
  - CircleCI
  - Dockerhub
  - IBM Cloud
  - IBM Watson Studio
  - IBM Cloud Object Storage

## Description
### Summary
Our solution aims to improve water irrigation to fight drought. This is done by minimizing the amount of water administered to a plant using the moisture level of the soil containing it as a reference to when it needs more water. We use a combination of IoT and ML tools to achieve this goal.

![Dig](https://user-images.githubusercontent.com/25390378/62081295-96e28200-b252-11e9-8a03-897f12778ba0.jpg)

### Problem Statement:
For over 7000 years, Egyptians have relied on agriculture as their main source of food, and for many families, it is their main source of income. As the country developed, agriculture became one of the largest industries in Egypt and one of the most profitable.

Most Egyptians settled close to the Nile River to have a constant stream of water from which they can drink, and water their fields. Agriculture is also useful in many other industries, such as clothes and dairy products among other things.

Egypt is facing a monumental natural disaster in the near future primarily due to the declining levels of the Nile as well as the reduced frequency of rainfall in recent history. That, combined with Egypt’s shrinking agricultural land and arid weather, will result in a countrywide drought that may lead to loss of life if left unaddressed.
  
The drought would result in a major shift in economic balance, where plant products would greatly increase in value. This uncontrolled inflation will result in lower purchasing power for citizens. Eventually, the amount produced by fields will decrease to a level that cannot sustain one family, let alone a country.
  
Another effect of the drought would be the decrease in clean drinking water available for everyone; this alone will cause tremendous loss of human and animal life.

### What We Are Trying To Solve:
We aim at reducing water consumption used in irrigation which wastes huge amounts of water.

According to caaes.org (Central Administration for Agricultural Extension and Environment in Egypt), ninety percent of fields in Egypt is irrigated using flood irrigation. Flood irrigation is one of the oldest methods. Farmers flow water down small trenches running through their crops and flooding the field. According to caaes.org, it is a low-efficiency method.

According to icid.org (International Commission on Irrigation & Drainage), flood irrigation is used mostly in less developed areas. Almost 94% of the irrigated areas are irrigated using flood irrigation, whilst the remaining 6% use other irrigation methods which require a lot of energy, pipe systems and hydraulic pressure techniques.

At times of drought, farmers should move from methods like flood irrigation to other methods which might need costly equipment initially but will be more beneficial as it will reduce the amount of water used which will, in turn, reduce the cost on the long run. 

### Our Solution:
We propose a solution that monitors the crops in a field and administers the exact amount of water needed only when necessary.

Our solution uses an IoT device that measures the moisture in the soil in order to determine whether the plant needs to be watered.

The system consists of five main components: first, a database containing some known plants. Second, a mobile application that shows the user the state of his fields. Third, an IoT device that contains a moisture sensor, water pump and NodeMCU. Fourth, a server that interfaces with all the previous components. Finally, two machine learning models; one for plant recognition and one for wilt detection.

The system works as follows; the user creates an account and logs in through the mobile app. The app transfers that request to the server which creates an entry for that user in the database. Once the user logs in he is presented with a grid, where each cell represents a fixed area of land containing a certain crop. That area of land contains our IoT device that measures the moisture every fixed period of time. It sends that data to the server, which replies with the moisture threshold for that specific plant. If the current moisture is lower than the threshold, the water pump is turned on, then turned off again as soon as the moisture level passes the threshold.

The user can add a plant at any time by clicking the desired cell in the grid and inserting the plant’s scientific name in the given text box, or taking a picture of the plant and using our machine learning model to identify it.

The machine learning models serve two different purposes: the plant recognition model is designed to identify different types of plants for those who are not familiar with agriculture (e.g. garden owners), or those who find it convenient to just take a picture of the plant rather than write its scientific name. The wilt detection model is designed to detect wilted or wilting plants so that the proper care may be given or the plant may be removed.

We call this a precision irrigation method, and we believe our solution can minimize the water consumption while maximizing the yield from fields where it is used.

The server is Django server written in python and deployed on IBM Kubernetes cluster which supports continuous integration and continuous deployment which will benefit extending the server functionalities in the future.

The application is an android application written in Java using Android Studio.

The ML models are hosted on IBM machine learning and watson studios.

The database is a MongoDB database hosted on atlas.
