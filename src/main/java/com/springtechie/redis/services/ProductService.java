package com.springtechie.redis.services;

import com.springtechie.redis.entity.Product;
import com.springtechie.redis.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // client request with id
    // it checks in the redis db with the given id.
    // if data is present it return from redis.
    // if not present then it goes to main db or actual db.
    // just before returning the data to client , it update the redis db.
    // {key : data}
    // 120 : data


    @Cacheable(value = "productcache",key = "#id",unless = "#result==null",condition = "#id>10")
    public Product getProduct(int id) { // 1 : data
        System.out.println("Hello");
        Optional<Product> product = productRepository.findById(id);
            return product.get();
    }

    // to create Product
    public String saveProduct(Product product) {
        productRepository.save(product);
        return "Product created Successfully";
    }

    // to delete the product
    @CacheEvict(value = "productcache", key = "#id")
    public String deleteProduct(int id) {
        productRepository.deleteById(id);
        return "Product deleted Successfully of"+ id;
    }

    // to update the product
    @CachePut(value = "productcache",key = "#product.id")
    public Product updateProduct(Product product) {
       return productRepository.save(product);
    }

    // to delete all data from cache
    @CacheEvict(value = "productcache",allEntries = true)
    public void removeCachedValues() {
        System.out.println("Cache is removed");
    }
}
