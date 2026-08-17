package com.example.transactionhistoryservice.config;

import com.example.transactionhistoryservice.messaging.event.TransactionCreated;
import com.example.transactionhistoryservice.messaging.event.TransactionUpdated;
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
                "transaction-created" , TransactionCreated.class,
                "transaction-updated" , TransactionUpdated.class
        ));


        return converter;
    }
}