package com.project.ohouclonecoding.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long itemId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String itemImg;
}
