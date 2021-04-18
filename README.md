# Room Occupancy Manager

### Expected tests results

**Test 1**

  * (input) Free Premium rooms: 3
  * (input) Free Economy rooms: 3
  * (output) Usage Premium: 3 (EUR 738)
  * (output) Usage Economy: 3 (EUR 167)


**Test 2**

  * (input) Free Premium rooms: 7
  * (input) Free Economy rooms: 5
  * (output) Usage Premium: 6 (EUR 1054)
  * (output) Usage Economy: 4 (EUR 189)

**Test 3**

  * (input) Free Premium rooms: 2
  * (input) Free Economy rooms: 7
  * (output) Usage Premium: 2 (EUR 583)
  * (output) Usage Economy: 4 (EUR 189)

**Test 4**

  * (input) Free Premium rooms: 7
  * (input) Free Economy rooms: 1
  * (output) Usage Premium: 7 (EUR 1153)
  * (output) Usage Economy: 1 (EUR 45)

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