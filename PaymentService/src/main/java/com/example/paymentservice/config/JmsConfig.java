package com.example.paymentservice.config;

import com.example.paymentservice.messaging.command.ProcessTransferCommand;
import com.example.paymentservice.messaging.event.TransferCompleted;
import com.example.paymentservice.messaging.event.TransferFailed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.Map;

@Configuration
public class JmsConfig {


    @Bean
    public MessageConverter jacksonJmsMessageConverter() {

        JacksonJsonMessageConverter converter =
                new JacksonJsonMessageConverter();

        converter.setTypeIdPropertyName("_type");

        converter.setTypeIdMappings(Map.of(
                "process-transfer", ProcessTransferCommand.class,
                "transfer-completed", TransferCompleted.class,
                "transfer-failed", TransferFailed.class
        ));

        return converter;
    }
}