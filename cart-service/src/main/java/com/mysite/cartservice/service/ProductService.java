package com.mysite.cartservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mysite.cartservice.model.Product;
import com.mysite.cartservice.model.ProductList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductService {

	@Autowired
	public RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getProductsFallback")
	public ProductList getProducts(int id) {
		return restTemplate.getForObject("http://PRODUCT-SERVICE/myapp/productservice/products", ProductList.class);
	}
	
	public ProductList getProductsFallback(int id) {
		ProductList productList = new ProductList();
		productList.setProductList(Arrays.asList(new Product(-1, "Product error")));
		return productList;
	}
}
