package com.mta.stockmanagement.productservice.repository;

import com.mta.stockmanagement.productservice.repository.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends SimpleJpaRepository<Product> {

}

