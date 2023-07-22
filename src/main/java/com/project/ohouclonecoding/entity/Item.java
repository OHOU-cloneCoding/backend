package com.project.ohouclonecoding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemTitle;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String itemImg;

    public Item(String itemName, String itemTitle, int price, String storedImageName) {
        this.itemName = itemName;
        this.itemTitle = itemTitle;
        this.price = price;
        this.itemImg = storedImageName;
    }
}
