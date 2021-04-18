# Room Occupancy Manager

### Build
```shell
mvn clean package
```

### Run & Test

Application uses default port 8080.

```shell

java -jar target/room-occupancy-manager-0.0.1-SNAPSHOT.jar

#set potential guests payments
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/guests/payments -d '{ "payments": [ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]}'

#get occupancy calculation
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 3, "freeEconomyRooms": 3}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 7, "freeEconomyRooms": 5}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 2, "freeEconomyRooms": 7}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 7, "freeEconomyRooms": 1}'
```