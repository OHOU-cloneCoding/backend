package com.project.ohouclonecoding.dto;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ItemRequestDto {
    private String itemName;
    private String itemTitle;
    private Integer price;
    private MultipartFile itemImage;

    public ItemRequestDto(String itemName, String itemTitle, Integer price, MultipartFile itemImage) {
        this.itemName = itemName;
        this.itemTitle = itemTitle;
        this.price = price;
        this.itemImage = itemImage;
    }
}
