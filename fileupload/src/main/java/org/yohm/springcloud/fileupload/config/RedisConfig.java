package org.yohm.springcloud.fileupload.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 功能简述
 * (redis配置)
 *
 * @author 海冰
 * @date 2019-06-16
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

//    @Value("spring.redis.lettuce.shutdown-timeout")
//    private Long shutdownTimeout;

//    @Bean
//    public LettuceConnectionFactory defaultConnectionFactory(GenericObjectPoolConfig defaultPoolConfig,RedisStandaloneConfiguration defaultRedisConfig){
////        LettuceClientConfiguration clientConfiguration= LettuceClientConfiguration.builder().shutdownTimeout(Duration.ofMillis(shutdownTimeout)).build();
//        return new LettuceConnectionFactory(defaultRedisConfig);
//
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> defaultRedisTemplate(LettuceConnectionFactory defaultLettuceConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
    @Bean
    public RedisTemplate<String, Object> defaultRedisTemplate(RedisConnectionFactory defaultLettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(defaultLettuceConnectionFactory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Configuration
    public static class DefaultRedisConfig {

        @Value("${spring.redis.host}")
        private String host;
        @Value("${spring.redis.port}")
        private String port;
        @Value("${spring.redis.password}")
        private String password;

        @Value("${spring.redis.lettuce.pool.maxActive}")
        private String maxActive;
        @Value("${spring.redis.lettuce.pool.maxIdle}")
        private String maxIdle;
        @Value("${spring.redis.lettuce.pool.minIdle}")
        private String minIdle;
        @Value("${spring.redis.lettuce.pool.maxWait}")
        private String maxWait;

//        @Bean
//        public GenericObjectPoolConfig defaultPoolConfig(){
//            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
////            config.setMaxTotal(Integer.getInteger(maxActive));
////            config.setMaxIdle(Integer.getInteger(maxIdle));
////            config.setMinIdle(Integer.getInteger(minIdle));
////            config.setMaxWaitMillis(Long.getLong(maxWait));
//            return config;
//        }

//        @Bean
//        public RedisStandaloneConfiguration defaultRedisConfig() {
//            System.err.println(port);
//            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//            config.setHostName(host);
//            config.setPort(Integer.getInteger(port));
//            config.setPassword(RedisPassword.of(password));
//            return config;
//        }
    }

}
