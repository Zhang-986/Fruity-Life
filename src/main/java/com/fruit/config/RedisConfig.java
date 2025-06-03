package com.fruit.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate，解决序列化乱码问题，并支持存储Java对象
     * 同时标记为 @Primary 解决多Bean注入问题
     */
    @Bean
    @Primary // 将此自定义的 RedisTemplate 设为首选，解决 `stringRedisTemplate` 冲突
    @SuppressWarnings("all") // 抑制一些不必要的警告
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // ==== 配置键（key）的序列化器 ====
        // 通常，键使用字符串序列化器，因为键名通常是字符串
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer); // Hash结构的键也使用字符串序列化

        // ==== 配置值（value）的序列化器 ====
        // 使用 Jackson2JsonRedisSerializer 来序列化和反序列化 Java 对象为 JSON
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定序列化哪些字段
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 这是反序列化时，防止无法反序列化成具体类型的问题（即存储的JSON中包含类信息）
        // 确保反序列化时能够根据JSON中的类型信息正确地实例化对象
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        template.setValueSerializer(jackson2JsonRedisSerializer); // 普通键值对的值序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash结构的值序列化

        template.afterPropertiesSet(); // 初始化配置
        return template;
    }
}