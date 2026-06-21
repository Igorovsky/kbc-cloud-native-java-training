# Speed Meter REST (Microservice) Exercise 

This project is a SpringBoot, java, microservice. It ingests and processes and serves, via REST API, speeding registrations.

The project is layered as follows: controller classes receive http requests as to save a speeding registration, or as to serve some technical summary about the registrations so far.

Controller classes have Service classes injected and operate via Service Implementation classes as to implement the business logic for each functionality. Sevice implementations extend a common Base Sevice class as to share common functions and checks.

Service classes have injected Repository classes and via the same they operate as to save or read values from the in memory H2 database.

Repository classes use Converter classes as to convert the database native data to business logic model classes and ship them upwards.

N.B. The last section here shows a technical drawing, color coded and explains the way this microservice is integrating with a frontend.

## JDK 17 - Project and IDE configurations

![Project Settings - JDK 17.png](main/resources/Project%20Settings%20-%20JDK%2017.png)

![Speed meter BE run config.png](main/resources/Speed%20meter%20BE%20run%20config.png)

## Swagger definition
At `http://localhost:8080/api/swagger-ui/index.html#/speed-meter-controller`
![Speed meter BE swagger.png](main/resources/Speed%20meter%20BE%20swagger.png)

## Postman Collection REST API

Postman collection can be imported from src/main/resources/speedmeretrestex.postman_collection.json

## High Level Architectural drawing
This microservice's APIs are consumed by the SpeedRegsUI angular frontend. The technical drawing describes the aforementioned integration.
[Speed Meter High Level Architecture.drawio.html](main/resources/Speed%20Meter%20High%20Level%20Architecture.drawio.html)