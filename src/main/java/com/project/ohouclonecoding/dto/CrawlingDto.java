package com.project.ohouclonecoding.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CrawlingDto {
    private String itemName;
    private String itemTitle;
    private Integer price;
    private String itemImg;

    public CrawlingDto(String itemImg, String itemTitle, String itemName, Integer price) {
        this.itemName = itemName;
        this.itemTitle = itemTitle;
        this.price = price;
        this.itemImg = itemImg;
    }
}
