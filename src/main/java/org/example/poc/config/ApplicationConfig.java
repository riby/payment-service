package org.example.poc.config;

import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.java.AbstractCqlTemplateConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


/**
 * Created by riby on 7/14/15.
 */
@Configuration
public class ApplicationConfig {

    @Configuration
    @EnableCassandraRepositories(basePackages = {"org.example.poc.dao"})
    public static class CassandraConfig extends AbstractCqlTemplateConfiguration {

        @Autowired
        private Environment env;

        @Override
        public String getKeyspaceName() {
            return env.getProperty("cassandra.keyspace");
        }

        @Bean
        public CassandraTemplate cassandraTemplate(Session session) {
            return new CassandraTemplate(session);
        }
    }

}