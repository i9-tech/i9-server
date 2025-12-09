package school.sptech.config.redis;

import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisCacheConfig {

  @Bean
  public RedisCacheManager redisCacheManager(RedisConnectionFactory cf) {

      // 1. Criar um ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();

      // 2. Registrar o módulo JavaTimeModule
      objectMapper.registerModule(new JavaTimeModule());

      // 3. Usar o ObjectMapper personalizado no serializador
      var json = new GenericJackson2JsonRedisSerializer(objectMapper);

      var defaults = RedisCacheConfiguration.defaultCacheConfig()
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json))
              .disableCachingNullValues()
              .prefixCacheNameWith("app::")
              .entryTtl(Duration.ofMinutes(5));

      var perCache = Map.of(
              "produtoPorId", defaults.entryTtl(Duration.ofMinutes(10)),
              "listaProdutos", defaults.entryTtl(Duration.ofSeconds(30)),
              "pratoPorId", defaults.entryTtl(Duration.ofMinutes(10)),
              "listaPratos", defaults.entryTtl(Duration.ofSeconds(30))
      );

      return RedisCacheManager.builder(cf)
              .cacheDefaults(defaults)
              .withInitialCacheConfigurations(perCache)
              .build();
    }
}