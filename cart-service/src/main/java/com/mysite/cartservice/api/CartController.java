package com.mysite.cartservice.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.cartservice.model.Category;
import com.mysite.cartservice.model.Product;
import com.mysite.cartservice.model.ProductItem;
import com.mysite.cartservice.service.CategoryService;
import com.mysite.cartservice.service.ProductService;

@RestController
@EnableCircuitBreaker
@EnableHystrixDashboard
public class CartController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/carts/{id}")
	public List<ProductItem> getAll(@PathVariable("id") int id) {
		System.out.println("----- Cart - get cart by id : " + id);
		List<ProductItem> productItemList = new ArrayList<>();
		
		for( Product product : productService.getProducts(id).getProductList()) {
			Category category = categoryService.getCategory(product);
			productItemList.add(new ProductItem(product.getId(), product.getName(), category.getName()));
		}
		
		return productItemList;
	}
}
