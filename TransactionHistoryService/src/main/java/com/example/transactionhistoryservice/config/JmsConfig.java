package com.example.transactionhistoryservice.config;

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

//        converter.setTypeIdMappings(Map.of(
//                "process-transfer", ProcessTransferCommand.class,
//                "transfer-completed", TransferCompleted.class,
//                "transfer-failed", TransferFailed.class
//        ));

        return converter;
    }
}