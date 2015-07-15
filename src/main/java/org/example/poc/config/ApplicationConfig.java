package org.example.poc.config;

import com.datastax.driver.core.Session;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.java.AbstractCqlTemplateConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.SchemaAction;
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

//        @Override
//        protected String getContactPoints() {
//            return "contactPointsIP";
//        }

        @Override
        public String getKeyspaceName() {
            return env.getProperty("cassandra.keyspace");
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

}