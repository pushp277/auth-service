package com.sageDelta.auth_service.repositories;

import com.sageDelta.auth_service.configs.JedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final JedisPool jedisPool;

    public void addSession(String key, String value){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.set(key, value);
        }
    }

    public Optional<String> getSession(String key){
        try(Jedis jedis = jedisPool.getResource()){
            return Optional.ofNullable(jedis.get(key));
        }
    }

}
