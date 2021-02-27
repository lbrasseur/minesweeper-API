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
* Start with some POC before writing unit tests (this might contradict TDD a little).
* Store the board as a serialized JSON using a random generated string as key, this way I can deploy it easily on most storages.
* Simple user table for authentication (no integration with 3rd party authentication services, no OAuth2, etc.)

## Deployment
* Reviewed platforms
  * Google app engine: used before.
  * AWS EC2 or lambda: used before, but even free tier requires credit card.
  * Heroku: not used before.
* While thinking which one to choose, I will create a Spring Bott app which seems to be supported by both AppEngine and Heroku 

## TODO
* Deployment
* Authentication
* Persistence
* Define bindings in a separated class (Like Guice or Dagger) instead og using @Component
  * Using Dagger2 + Spark has better startup than Spring, I can try it later.
* Time tracking

## Missign functionality from original game
* Cell cleaning when clicking 2 buttons at the same time (is there an event for that??) 

## Sample data
```
POST http://localhost:8080/create
{
    "width": 4,
    "height": 3,
    "mines": 5
}
```
```
POST http://localhost:8080/click
{
    "boardId": "48f3466130d957bc31e1fda4b9ae4880f7d96a42ea806b68afb80b520501babe",
    "column": 1,
    "row": 0
}
```
