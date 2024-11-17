package kg.com.restapifortaskmanagement.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class LoggingFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, jakarta.servlet.ServletException {
		if (request instanceof HttpServletRequest httpRequest) {
			logRequest(httpRequest);
		}
		
		chain.doFilter(request, response);
		
		if (response instanceof HttpServletResponse httpResponse) {
			logResponse(httpResponse);
		}
	}
	
	private void logRequest(HttpServletRequest request) {
		log.info("Incoming request: method={}, URI={}, queryParams={}",
				request.getMethod(),
				request.getRequestURI(),
				request.getQueryString());
	}
	
	private void logResponse(HttpServletResponse response) {
		log.info("Outgoing response: status={}", response.getStatus());
	}
	
}