package com.example.serversided14.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

// @Configuration used on classes which define beans. Java calsses annotated with @Configuration is a configuration by itself and will have methods to instantiate and configure the dependencies.
@Configuration

// @EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
  private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private Optional<Integer> redisPort;

  @Value("${spring.redis.password}")
  private String redisPassword;

  // annotation is used at the method level. @Bean annotation works with
  // @Configuration to create Spring beans. The method annoted with this
  // annotation works as bean ID and it creates and returns the actual bean.
  @Bean
  @Scope("singleton")
  public RedisTemplate<String, Object> redisTemplate() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    logger.info(String.format("REDIS host> %s port> %d password> %s", redisHost, redisPort.get(), redisPassword));
    config.setHostName(redisHost);
    config.setPort(redisPort.get());
    config.setPassword(redisPassword);

    final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
    jedisFac.afterPropertiesSet();

    RedisTemplate<String, Object> template = new RedisTemplate();

    template.setConnectionFactory(jedisFac);

    template.setKeySerializer(new StringRedisSerializer());

    RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
    template.setValueSerializer(serializer);

    return template;
  }
}
