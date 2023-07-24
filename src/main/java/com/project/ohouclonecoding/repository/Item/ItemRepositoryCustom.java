package com.project.ohouclonecoding.repository.Item;

import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<ItemResponseDto> searchItems(ItemSearchDto condition);
}
