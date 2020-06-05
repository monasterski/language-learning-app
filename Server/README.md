# language-learning-app

to run database and server on docker:
(From this folder)

**generate jar:**

mvn clean

mvn package -DskipTests

**deploy using docker:**

docker-compose -f docker-compose.yml build

docker-compose up


**server on endpoint:** 

localhost:8099/api/

**generate some test values on databases:**

GET request on enpoint: localhost:8099/api/test
