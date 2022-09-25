package com.example.teste.event;


import com.example.teste.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DemoEventConsumer {
    //consumidor da mensagem, retira a informação da fila

    @RabbitListener(queues = RabbitMQConfig.QK_EXAMPLE_QUEUE)
    public void onMessageReceived(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {


        try{
            channel.basicAck(tag, false);
            System.out.println("Sua mensagem foi recebida com sucesso :)" + message);
        }catch (Exception e) {
            channel.basicNack(tag, false, true);
            System.out.println("Erro na mensagem :( " + message);
        }
    }
}