# springbootAMQPRabbitPOC
POC for Spring boot AMQP messaging with RabbitMQ

## Environment setup
* Start RabbitMQ with docker:
    ```
    docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3.8.3-management
    ```

## Running app
* Rabbit Console: http://localhost:15672 (guest / guest)
* Page for sending messages: http://localhost:8080/
* Page for received messages: http://localhost:8080/orders

# AMQP / RabbitMQ concepts
* https://www.rabbitmq.com/tutorials/amqp-concepts.html

# References
* https://sivalabs.in/2018/02/springboot-messaging-rabbitmq/
* https://www.e4developer.com/2018/01/28/setting-up-rabbitmq-with-spring-cloud-stream/
* https://hub.docker.com/_/rabbitmq/
