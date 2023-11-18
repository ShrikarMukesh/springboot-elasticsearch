package com.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elasticsearch.entity.Product;
import com.elasticsearch.util.ElasticSearchUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
@Slf4j
public class ElasticSearchService {

    private final ElasticsearchClient  elasticsearchClient;

    public ElasticSearchService(ElasticsearchClient  elasticsearchClient){
        this.elasticsearchClient = elasticsearchClient;
    }
    public SearchResponse<Map> matchAllServices() throws IOException {

        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s -> s.query(supplier.get()), Map.class);
        log.info("elasticsearch matchAll query is " + supplier.get().toString());
        return searchResponse;
    }

    //matchAllProducts video content
    public SearchResponse<Product> matchAllProductsServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()),Product.class);
        log.info("elasticsearch matchAllProductsServices Query is "+supplier.get().toString());
        return searchResponse;
    }

    //matchProductWithName
    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient
                .search(s->s.index("products")
                        .query(supplier.get()),Product.class);
        log.info("matchProductsWithName: elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}