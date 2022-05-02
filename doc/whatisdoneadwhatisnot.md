### Preamble

The work was performed as part of a test challenge. Therefore, the details of the business processes implemented within the solution are quite synthetic and in the real life should be discussed with the customer.

These details include:

- Life cycles of the Order from the point of view of Administrator, Courier and Customer

- The status flow of the Order from the perspective of each role

- Detail of the visibility of the data for each participant


### What is done:

 - Microservices that represent specific parts of domain area  developed: service-client, service-courier, service-admin 
 
 - Common public API service, that includes JWT Auth functionality ( yes, I know that this mix of responsibilities is not good from architectural point of view but I done it so for reducing the time of development.)
 
 - All the parts of system (including Postgre servers and RabbitMQ server) are being run in docker-compose environment
 
 - Public API is being exposed externally using Traefik
 
 - Interactive REST API documentation exposed externally
 
 - A mechanism for end-to-end identification of requests that come to public API up to their execution by services in respond to events from AMQP queues is implemented.
 
 - Acceptance tests (only most necessary) for testing solution end to end implemented using PyTest

### Reservations
I mainly focused on the technical aspects of the project in a whole.
At the same time, given the test nature of this task, I omitted some points that in a real project must be elaborated with care. 

Among them:

- The Acceptance Tests that I've created cover only the most basic scenarios. The e2e test is written in a style of a single stream of events against a single order,  so in case if this tests crashes, it's hard to keep track of what exactly not works. In the real world, I would create separate small tests for each specific case.

- I implemented the authorization function directly in PublicAPI. In a real project, I would better make it a separate module.

- The PublicAPI module is implemented in a synchronous style, that is not good for handling the requests that should just  be delegate to downline. In a real project it would better use something like spring-flux.

- I have not worked out in detail the error handling resulting in the processing of incoming messages from AMQP. 

- I haven't implemented unit testing for Rest API. Development of such tests are also pretty time-consuming because of lot of details that should be checked.

### What else can be done within the solution like this one:

- Add a service monitoring system, kinda zabbix

- Publish logs in kafka and collect logs in Elasticsearch for further analysis and performing RCA  using ELK stack

