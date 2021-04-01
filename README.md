# Tick
The solution consists of 2 main services called `api` and `statistics`. 
Besides the two services, there is an another service called `eureka` which leads as discovery service.

### Prerequisites
- Java 11
- Maven 3.x
- Docker Compose

### Implementation
Basic micro-services principles are followed.

`api` service is responsible from giving response to `POST /ticks`, `GET /statistics` and `GET /statistics/{instrument_identifier}`

`statistics` service is responsible from aggregation and calculation. 

Two services are communicating with `kafka` messaging.

With this way, api instantce will be safe against concurrent requests as theoretically.  

### Assumptions
- The price must be positive number. It should have at most 2 decimal points. It can not be zero.
- The instrument name `ABC` or `abc` are referring to the same instrument.
- If there are duplicate tick requests with the same timestamp, the first one is counted.
- The api will not accept any timestamp from the future.
- The api will return 204 if the input is not valid
- The api will return 204 if the instrument is not found 
- The api will return 204 if there is no data overall

### Run
Simply, execute the `run.sh`. It'll initialize the docker compose and micro-services.
```bash
sh run.sh
```

### Testing
You can find the necessary tests inside for each service (api & statistics). On the other hand, I've created a new module called `integration-tests`. 
It is firstly initializing the services, and creates randomly POST tick requests. There is another thread which is requesting GET statistics.
So as a result you can view all the details about what was going on services. It should end at most at `3` minutes in your local test.

You can play the numbers in `application-test.yaml` in integration-tests test resources folder.  
```bash
sh runTests.sh
```

### Improvement
- First of all, I'm not used to kafka :) I personally like to try new things for these kind of homework.
And I didn't like my implementation of broker package. (See `KafkaConsumer.class` or `KafkaProducer.class`). The serialization of messages should be happened in `spring.framework` with directives. I should not do it manually with a new service called `ObjectSerializer`
- I've used kafka as messaging system, but gRPC or direct socket communication should be a better choice for real world.
- I would like to run the whole application and integration tests with only one single docker-compose command.
- Fine tuning with kafka - Probably I don't need `Quartz` if I used kafka already :)

### Overall Feedback
Although it seems a simple problem, there are lots of caveats inside it. Generally, I'm used to for concurrency problems, I hope I understood the problem definition correct and you'll like my solution. 
