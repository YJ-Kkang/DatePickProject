package jpabasic.datepickproject.config;

import jpabasic.datepickproject.common.filter.JwtFilter;
import jpabasic.datepickproject.common.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	private final JwtUtil jwtUtil;  // JwtUtil 주입

	public FilterConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> registrationBean
			= new FilterRegistrationBean<>(); // JwtFilter가 Spring bean으로 등록

		registrationBean.setFilter(new JwtFilter(jwtUtil));  // JwtUtil을 필터에 주입

		registrationBean.addUrlPatterns("/api/*");  // "api/"로 시작하는 모든 URL에 대해 필터를 적용
		registrationBean.setOrder(1);  // 필터 우선순위 설정

		return registrationBean;
	}
}
