package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String description;
    private String parentCategory;
    private boolean Active;

    @Getter
    @Setter
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<InventoryItem> items = new ArrayList<>();
}
