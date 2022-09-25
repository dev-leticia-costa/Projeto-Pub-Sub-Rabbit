package com.example.teste.event;

import com.example.teste.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;

@Controller
//produtor da mensagem - publisher - responsável pelo envio da mensagem
public class DemoEventController {

    private final RabbitTemplate rabbitTemplate;

    public DemoEventController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/event")
    ResponseEntity<Void> postEventMessage() {
        final String timeNowMessage = String.format("%s - %s", "Testando mensagem", getTimeNowRepresentation());
        rabbitTemplate.convertAndSend(RabbitMQConfig.QK_EXAMPLE_QUEUE, timeNowMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getTimeNowRepresentation() {
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(now);
    }
}
