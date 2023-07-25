package com.project.ohouclonecoding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDto {
    private String keyword;
    private Integer minPrice;
    private Integer maxPrice;

    private String orderField = "itemId";
    private Boolean isASC = true;
    private Integer page = 1;
    private Integer size = 8;
}
