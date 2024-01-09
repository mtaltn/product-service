package com.mta.stockmanagement.productservice.repository.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "stock_management")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Builder.Default
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate = new Date();

    @Builder.Default
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate = new Date();

    @Column(name = "is_deleted")
    private boolean deleted;
}

/*
*  The second choice is given in the example below
*/
/*
public record Product(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long ID,
        @Column(name = "name")
        String name,
        @Column(name = "quantity")
        int quantity,
        @Column(name = "price")
        double price
) {
}
*/