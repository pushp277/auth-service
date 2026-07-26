package org.sageDelta.auth_service.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(JedisConfigProperties.class)
@RequiredArgsConstructor
@Slf4j
public class JedisConfig {

    private final JedisConfigProperties jedisConfigProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        log.info("Jedis configurations:- \n host: {}, port: {}, totalPool: {}",
                jedisConfigProperties.host(),
                jedisConfigProperties.port(),
                jedisConfigProperties.totalPool());

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisConfigProperties.totalPool());
        jedisPoolConfig.setMaxIdle(jedisConfigProperties.maxIdle());
        jedisPoolConfig.setMinIdle(jedisConfigProperties.minIdle());
        return jedisPoolConfig;
    }
    @Bean
    public JedisPool jedisPool(){
        return new JedisPool(jedisPoolConfig(),
                jedisConfigProperties.host(),
                jedisConfigProperties.port());
    }

}
