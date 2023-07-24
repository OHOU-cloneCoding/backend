package com.project.ohouclonecoding.dto;


import com.project.ohouclonecoding.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private String itemTitle;
    private int price;
    private String itemImg;

    public ItemResponseDto(Item savedItem) {
        this.itemId = savedItem.getItemId();
        this.itemName = savedItem.getItemName();
        this.itemTitle = savedItem.getItemTitle();
        this.price = savedItem.getPrice();
        this.itemImg = savedItem.getItemImg();
    }
}
