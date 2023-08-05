package com.document.document.publisher;

import com.document.document.message.DocumentMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class DocumentPublisher {
    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private DocumentPublisher(final AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(final DocumentMessage documentMessage) {
        rabbitTemplate.convertAndSend(exchange, routingKey, documentMessage);
    }

    @PostConstruct
    public void test(){
        DocumentMessage documentMessage = new DocumentMessage(1, "abdcd");
        sendMessage(documentMessage);
    }
}
