package com.example.spring.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableMongoAuditing
public class MongodbQueryConfig {
    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.setCustomConversions(new MongoCustomConversions(List.of(new LocalDateTimeStringConverter())));
        return converter;
    }

    @Bean
    public PlatformTransactionManager queryTransactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

    private static class LocalDateTimeStringConverter implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            return source.toString();
        }
    }
}
