# Challenge

## :computer: How to execute

```console
docker-compose up --build -d
```

Tail payment-processor logs:
```console
docker logs --follow payment-processor
```
## :memo: Notes

The proposed solution is based on spring boot framework 5 in java 11 and maven as project management. To improve the code and reuse in future implementations, the following **multi-module** structure has been designed:
* **Delivery**: Base project that contains the configurations and dependencies inherited by the modules. It also contains the initial infrastructure.
* **Domain**: Module that contains all Entities and DTOs to be used in the project.
* **Repository** Module that contains all classes of data access layer used in the project.
* **PaymentProcessor** Module that contains the kafka stream consumer and business logic to persist payments and send logs to the API.

In kafka environment, it has been chosen to use two consumers in the **PaymentConsumer** component, one to consume the streams of the offline topic and the other for the online one in different groups.

To deserialize the payment message to java class, the following methods have been implemented in **KafkaConsumerConfig**: ConsumerFactory and KafkaListenerContainerFactory using the Jackson JSON processor.

## :pushpin: Things to improve

It would be interesting to save the logs that have been failed during the sending to the log API. They could be stored in the Postgresql database or in a non-relational one such as Elasticsearch, instead of being stored in a log file on the system.
Later, a scheduled task could be implemented that tries to send the logs again in a defined time interval.

On the other hand, to avoid saturation of the payment validation API. Payments could be saved in a temporary table in the database, to later be validated in a homogeneous way.

Finally, to improve the scalability of the system. A reverse proxy could be developed with Spring Cloud + Netflix Zuul + Eureka technology and deploy several instances of the PaymentProcessor application.
