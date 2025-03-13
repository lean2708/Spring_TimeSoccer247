# Spring TimeSoccer247

## Tech Stack
- **Build Tool:**: Maven >= 3.2.3
- **Java**: 17
- **Framework**: Spring Boot 3.2.x
- **Database**: MySQL
## Build and Run
**1. Clone Repository :**
```java
git clone https://github.com/lean2708/Spring_TimeSoccer247
cd Spring_TimeSoccer247
```
- Then, create and configure the **.env** file

## 2. Docker Guideline
**Stop and Remove Old Containers:**
```java
docker stop $(docker ps -q)
```
**Run Your Application**
```java
docker-compose up -d
```
**3. Swagger Documentation Guide (API Document):**
- **After running the application, you can access Swagger UI at:**
```java
http://localhost:8080/timesoccer247/swagger-ui/index.html
```
- The default URL to retrieve the API documentation in JSON format **(Explore section)**:
```java
/timesoccer247/v3/api-docs
```
**4. Sample VNPAY Payment Information (Test):**
| Bank                  | NCB                      |
|-----------------------|--------------------------|
| Card Number           | 9704198526191432198      |
| Cardholder Name       | NGUYEN VAN A             |
| Issue Date            | 07/15                    |
| OTP Password          | 123456                   |
