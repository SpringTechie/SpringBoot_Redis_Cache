package com.springtechie.redis.controller;

import com.springtechie.redis.entity.Product;
import com.springtechie.redis.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;
    private final CacheManager cacheManager;

    @Autowired
    public ProductController(ProductService productService, CacheManager cacheManager) {
        this.productService = productService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/get/id/{id}")
    public Product getProductById(@PathVariable int id) {
        Product product = productService.getProduct(id);
        System.out.println(product.getId());
        return product;
    }

    @PostMapping(value = "/save")
    public String saveProduct( @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/delete/id/{id}")
    public String deleteProductById(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/update")
    public String updateProduct( @RequestBody Product product) {
         productService.updateProduct(product);
         return "product updated successfully";
    }

    @GetMapping("/delete/cache")
    public String updateProduct() {
        productService.removeCachedValues();
        return "cache deleted successfully";
    }

    @GetMapping("/cachemanager")
    public void cachemanager() {
        Product productcache = (Product) cacheManager.getCache("productcache").get(1).get();
        System.out.println( cacheManager.getCacheNames());
        cacheManager.getCache("productcache").putIfAbsent(3,new Product(1,"fan",45.0));
        System.out.println(productcache.getName());
    }

    @GetMapping("/demo/jenkins")
    public String demo() {
        return "cache deleted successfully";
    }

}
