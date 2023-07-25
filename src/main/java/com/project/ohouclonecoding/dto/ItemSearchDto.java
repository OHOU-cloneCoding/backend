package com.project.ohouclonecoding.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDto {
    private String keyword;
    private Integer minPrice;
    private Integer maxPrice;

    private String orderField = "itemId";
    private boolean isASC = false;
    private Integer page = 1;
    private Integer size = 8;
}
