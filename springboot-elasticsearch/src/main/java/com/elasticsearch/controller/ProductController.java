package com.elasticsearch.controller;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.entity.Product;
import com.elasticsearch.service.ElasticSearchService;
import com.elasticsearch.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apis")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ElasticSearchService elasticSearchService;

    public ProductController(ProductService productService, ElasticSearchService elasticSearchService){
        this.elasticSearchService = elasticSearchService;
        this.productService = productService;
    }
    @GetMapping("/findAll")
    Iterable<Product> findAll(){
        return productService.getProducts();

    }

    @GetMapping("/matchAll")
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse = elasticSearchService.matchAllServices();
        log.info("MatchAll Response" + searchResponse.hits().toString());
        return searchResponse.hits().hits().toString();
    }
    @GetMapping("/matchAllproducts")
    public List<Product> matchAllproducts() throws IOException{

        SearchResponse<Product> searchResponse = elasticSearchService.matchAllProductsServices();
        log.info(searchResponse.hits().hits().toString());

        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();

        for (Hit<Product> hit : hitList){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }
}