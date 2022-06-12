package com.accolite;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/a")
public class ServiceAController {
	int count=1;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping
	//@CircuitBreaker(name="serviceA", fallbackMethod = "serviceAFallBack")
	//@Retry(name="serviceA")
	@RateLimiter(name="serviceA")
	public String serviceA() {
		System.out.println("retrying "+ count++ +"times at "+new Date());
		return restTemplate.getForObject("http://localhost:8081/b", String.class);
	}
	
	public String serviceAFallBack(Exception e) {
		return "This is the fallback method from service A";
	}

}
