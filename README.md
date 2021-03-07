# Documentation [WIP]
## Assumptions
* The board will be small, so it can be completely loaded in memory.
* It will be deployed on some cloud environment, probably using some no-sql solution as storage.

## Design decisions
* Define a clear separation between interface APIs and their implementations.
* Using dependency injection to wire the components.  If possible, use Dagger2 for performance and build time validations.
* Making the services stateless in order to make it scale better.
* Not following REST strictly. I prefer, for example, returning status information in payload because that provides more flexibility than just HTTP codes. The same logic applies for HTTP methods.
* The board will be implemented in memory, since querying storage for each cell could be non performant.
* Simple user table for authentication (no integration with 3rd party authentication services, no OAuth2, etc.)
* Implement a generic key-value storage API, so it can easily be migrated to different storages.
* Using SpringBoot for implementing the server and Angular for implementing the client.

## Deployment
* Deployed to Heroku:
  * [https://awesome-minesweeper.herokuapp.com/](https://awesome-minesweeper.herokuapp.com/) (the name is obviously a joke :D)
* Test users:
  * username: admin / password: hello
  * username: cacho / password: deicas

## TODO
* Persistence (current implementations uses a in-memory key-value storage).
* Complete code coverage adding more unit tests.
* Define bindings in a separated class (Like Guice or Dagger) instead og using @Component
  * Using Dagger2 + Spark has better startup than Spring.
* Service error handling
* UI: If you click too fast an inconsistent action could be triggered (but the state is not broken)

## Missing functionality from original game
* Cell cleaning when clicking 2 buttons at the same time (is there an event for that??) 
* Show pending mines count
