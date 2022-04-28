The work was performed as part of a test challenge. Therefore, the details of the business processes implemented within the solution are quite synthetic and in the real life should be discussed with the customer.

These details include:

- Life cycles of the Order from the point of view of Administrator, Courier and Customer

- The status flow of the Order from the perspective of each role

- Detail of the visibility of the data for each participant

I mainly focused on the technical aspects of the project in a whole.
At the same time, given the test nature of this task, I omitted some points that in a real project must be elaborated with care. 

Among them:

- The Acceptance Tests that I've created cover only the most basic scenarios. The e2e test is written in a style of a single stream of events against a single order,  so in case if this tests crashes, it's hard to keep track of what exactly not works. In the real world, I would create separate small tests for each specific case.

- I implemented the authorization function directly in PublicAPI. In a real project, I would better make it a separate module.

- The PublicAPI module is implemented in a synchronous style, that is not good for handling the requests that should just  be delegate to downline. In a real project it would better use something like spring-flux.

- I didn't implement bin validation at all. That may be done fairly straightforwardly, but it requires careful attention to each field to be validated and as a consequence that is time consuming task as for the test. 

- I use FeignClient to access Services. In this project, I don't handle errors that occur when service communicating via FejgnClient. In a real project, it is necessary to implement additional error handling  with reformulation of these errors into terms of the appropriate application level.

- I have introduced mechanisms for tracking call-flow by requestId and correlationId headers, but I have not provided an end-to-end correlationId transition within the inter-service exchange via AMQP.

- I have not worked out in detail the error handling resulting in the processing of incoming messages from AMQP. 

- I haven't implemented unit testing for Rest API. Development of such tests are also pretty time-consuming because of lot of details that should be checked.


What else can be done within the solution like this one:

- Add a service monitoring system, kinda zabbix

- Publish logs in kafka and collect logs in Elasticsearch for further analysis and performing RCA  using ELK stack

