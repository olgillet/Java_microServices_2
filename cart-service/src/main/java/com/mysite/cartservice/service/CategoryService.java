package com.mysite.cartservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mysite.cartservice.model.Category;
import com.mysite.cartservice.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CategoryService {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getCategoryFallback")
	public Category getCategory(Product product) {
		return restTemplate.getForObject("http://CATEGORY-SERVICE/myapp/categoryservice/categories/"+product.getCategoryId(), Category.class);
	}
	
	public Category getCategoryFallback(Product product) {
		return new Category(-1, "Category error");
	}
}
