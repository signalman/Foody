package com.data.foody.configuration

import com.data.foody.domain.Recipe
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@EnableRedisRepositories
@Configuration
class RedisConfiguration {

    @Value("\${spring.redis.host}")
    lateinit var host: String

    @Value("\${spring.redis.port}")
    var port: Int = 0

    @Value("\${spring.redis.password}")
    lateinit var password: String

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val standaloneConfiguration = RedisStandaloneConfiguration(host, port)
        standaloneConfiguration.setPassword(password)
        return LettuceConnectionFactory(standaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Recipe> {
        val redisTemplate = RedisTemplate<String, Recipe>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer() // JSON 직렬화/역직렬화를 위한 Serializer
        return redisTemplate
    }

}