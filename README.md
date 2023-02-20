# playerDirectory

Spring boot project with the ability to add players, their geography, which games they play and the level they are at 
in that game.

## Scope 
The focus of this project is to make a proof of concept of a matchmaking application setting up games between different
players based on their level and geography. Therefore, the project does not have an actual database but an in-memory 
database taking care of data. Furthermore, the typings are not as strong as they could be hence this is not the scope 
of the project.

## Getting started
You can run the tests with `mvn verify` to verify that the project builds correctly,
or start the service with `mvn spring-boot:run` - then you can interact with it at
`http://localhost:8080/playerproject`. The available endpoints are:

* Get request for getting all players. URL: `localhost:8080/playerproject/players`
* Post request for creating a player. URL: `localhost:8080/playerproject/players` 
Body:
```
{
    "playerId": 25,
    "name": "Christine",
    "countryCode": "DNK"
}
```
* Post request for adding a level to a player URL: `localhost:8080/playerproject/levels/{playerId}/{id}/{playerLevel}/{game}`.
If we were to add a level to the just created player the specific URL would be `localhost:8080/playerproject/levels/25/9/PRO/GOLF`

* Get request for getting matching players. URL: `localhost:8080/playerproject/players/{countryCode}/{level}`
* Get request for getting players matched by level. URL: `localhost:8080/playerproject/players/{game}/{playerLevel}`

## Technologies 
Project is created with:
* Java 18.
* Spring Boot 3
* Maven version 3.8.6 


## Assumptions and shortcomings
Throughout the making of the project there have been made some assumptions. Furthermore, there have been discovered some
shortcomings. All of which will be addressed here.
### Assumptions 
In this project it is assumed that the game played is WII Sports. For this reason there is a fixed number of games, 
which is defined in an enum (`Game`). If this were an actual matchmaking application it would make sense to be able to 
add more games via an API. 

### Shortcomings
As for data types, the typing could be stronger, e.g. the id's (`PlayerId` and `LevelId`) should be of their own type
instead of the generalized int.

In this version the ability to get matching players in solely based on the country code and the level. In an optimized
version games would also be taken into account, since it has no purpose to match a pro tennis player with a pro bowling
player.

In the `JdbcPlayerDAOTest` it would have been beneficial to test more failure states, such as what happens when there is
no players to match with.

When adding levels to specific players, it would also be beneficial to have a method getting a player based on id
(`getPlayerById`), this does however require a more complicated setup, looking into the database for the specific
player, to then create a level with this same playerId.

Due to the scope of the project only unit tests have been preformed - no end-to-end / integration test of the controller.

When creating a new player through the API the response will be `201 Created` in all cases. Even if it is an existing
`playerId`, this is a flaw since it should return `409 Conflict` as the response. 
