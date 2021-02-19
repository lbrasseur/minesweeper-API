# Documentation [WIP]
## Assumptions
* The board will be small, so it can be completely loaded in memory.
* It will be deployed on some cloud environment, probably using some no-sql solution as storage.

## Design decisions
* Define a clear separation between interface APIs and their implementations.
* Using dependency injection to wire the components.  If possible, use Dagger2 for performance and build time validations.
* Making the services stateless in order to make it scale better.
* I will start with a blocking approach, refactoring for a non-blocking later if it is considered a need.
* Not following REST strictly. I prefer, for example, returning status information in payload because that provides more flexibility than just HTTP codes. The same logic applies for HTTP methods.
* The board will be implemented in memory, since querying storage for each cell could be non performant.
* Start with some POC before writing unit tests (this might contradict TDD a little).