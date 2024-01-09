package com.mta.stockmanagement.productservice.repository;

import com.mta.stockmanagement.productservice.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product getByIdAndDeletedFalse(Long id);
    List<Product> getAllByDeletedFalse();
}

