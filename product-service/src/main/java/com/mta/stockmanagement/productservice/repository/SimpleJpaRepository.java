package com.mta.stockmanagement.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimpleJpaRepository<T> extends JpaRepository<T,Long> {

    T getByIdAndDeletedFalse(Long ID);
    List<T> getAllByDeletedFalse();
}
