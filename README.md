# Room Occupancy Manager


### Run & Test
```shell

curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 3, "freeEconomyRooms": 3, "prices": [ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 7, "freeEconomyRooms": 5, "prices": [ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 2, "freeEconomyRooms": 7, "prices": [ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]}'
curl -X POST -H 'Content-Type: application/json' http://localhost:8080/occupancy/calculation -d '{"freePremiumRooms": 7, "freeEconomyRooms": 1, "prices": [ 23, 45, 155, 374, 22, 99, 100, 101, 115, 209 ]}'
```