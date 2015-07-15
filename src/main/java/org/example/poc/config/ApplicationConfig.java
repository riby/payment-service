package org.example.poc.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * Created by riby on 7/14/15.
 */
@Configuration
public class ApplicationConfig {

    public static class WebMvcConfig extends WebMvcConfigurerAdapter {
        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            converter.setObjectMapper(objectMapper);
            converters.add(converter);
            super.configureMessageConverters(converters);
        }
    }


    @Configuration
    @EnableCassandraRepositories(basePackages = {"org.example.poc.dao"})
    public static class CassandraConfig extends AbstractCassandraConfiguration {

        @Autowired
        private Environment env;

        @Override
        protected String getContactPoints() {
            return env.getProperty("cassandra.contactPoints");
        }

        @Override
        public String getKeyspaceName() {
            return env.getProperty("cassandra.keyspace");
        }

        @Override
        protected AuthProvider getAuthProvider() {
            String user = env.getProperty("cassandra.user");
            String pwd = env.getProperty("cassandra.pwd");
            return new PlainTextAuthProvider(user, pwd);
        }

        @Override
        public List<String> getStartupScripts() {

            return Arrays.asList("CREATE TABLE IF NOT EXISTS " + getKeyspaceName() + ".payment (\n" +
                    "                txId text,\n" +
                    "                accountnumber text,\n" +
                    "                cardcategory text,\n" +
                    "                cardtype text,\n" +
                    "                cardnumber text,\n" +
                    "                customername text,\n" +
                    "                expiredate text,\n" +
                    "                securitycode text,\n" +
                    "                PRIMARY KEY (txId)\n" +
                    "                );");
        }

        @Bean
        public CassandraOperations cassandraOperations() throws Exception {
            return new CassandraTemplate(session().getObject());
        }

        @Override
        public String[] getEntityBasePackages() {
            return new String[]{"org.example.poc.model"};
        }
    }

    @Configuration
    public static class RabbitConfiguration {

        @Bean
        public ConnectionFactory connectionFactory() {
            return new CachingConnectionFactory("localhost");
        }

        @Bean
        public AmqpAdmin amqpAdmin() {
            return new RabbitAdmin(connectionFactory());
        }

        @Bean
        public RabbitTemplate rabbitTemplate() {
            return new RabbitTemplate(connectionFactory());
        }

        @Bean
        public Queue myQueue() {
            return new Queue("payment-confirm");
        }
    }

}