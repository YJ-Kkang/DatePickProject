package jpabasic.datepickproject.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		return new 	ConcurrentMapCacheManager("searchCache"); // 인메모리 캐시 설정
	}
}
