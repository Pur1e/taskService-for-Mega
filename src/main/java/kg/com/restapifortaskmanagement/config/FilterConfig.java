package kg.com.restapifortaskmanagement.config;

import kg.com.restapifortaskmanagement.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<LoggingFilter> loggingFilter() {
		FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new LoggingFilter());
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}
	
}