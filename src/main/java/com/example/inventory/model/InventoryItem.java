package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;
    private String description;
    private Double unitPrice;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "supplier_Id")
    private Supplier supplier;
    private Long reOrderLevel;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
