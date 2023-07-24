package com.project.ohouclonecoding.dto;


import lombok.Getter;

@Getter
public class ItemRequestDto {
    private String itemName;
    private String itemTitle;
    private Integer price;


    public ItemRequestDto(String itemName, String itemTitle, Integer price) {
        this.itemName = itemName;
        this.itemTitle = itemTitle;
        this.price = price;

    }
}
