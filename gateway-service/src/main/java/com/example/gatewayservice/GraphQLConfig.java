package com.example.gatewayservice;


import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GraphQLConfig {
    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("DateTime")
                .description("Java LocalDateTime scalar")
                .coercing(new Coercing<LocalDateTime, String>() {
                    @Override
                    public String serialize(Object input) {
                        if (input instanceof LocalDateTime) {
                            return ((LocalDateTime) input).toString();
                        }
                        throw new CoercingSerializeException("Expected LocalDateTime object.");
                    }

                    @Override
                    public LocalDateTime parseValue(Object input) {
                        if (input instanceof String) {
                            return LocalDateTime.parse((String) input);
                        }
                        throw new CoercingParseValueException("Expected String");
                    }

                    @Override
                    public LocalDateTime parseLiteral(Object input) {
                        if (input instanceof StringValue) {
                            return LocalDateTime.parse(((StringValue) input).getValue());
                        }
                        throw new CoercingParseLiteralException("Expected StringValue");
                    }
                }).build();
    }

}
