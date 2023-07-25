package com.project.ohouclonecoding.repository.Item;

import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Page<Item> findAllByOrderByItemIdDesc(Pageable pageable);

    @Query("select i from Item i " +
            "where i.price between :minPrice and :maxPrice "+
            "and i.itemTitle like %:keyword% "+
            "or i.itemName like %:keyword%")
    List<ItemResponseDto> searchItemsByKeywordAndPriceAndTitle(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("keyword") String keyword
    );
}
