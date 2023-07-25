package com.project.ohouclonecoding.repository.Item;

import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.dto.ItemSearchDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.project.ohouclonecoding.entity.QItem.item;
import static org.springframework.util.StringUtils.hasText;


@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ItemResponseDto> searchItems(ItemSearchDto condition) {

        PageRequest pageRequest = PageRequest.of(condition.getPage() - 1, condition.getSize());

        List<ItemResponseDto> content = jpaQueryFactory
                .select(
                        Projections.fields(ItemResponseDto.class,
                                item.itemId,
                                item.itemName,
                                item.itemTitle,
                                item.price,
                                item.itemImg
                        ))
                .from(item)
                .where(
                        itemContainKeyword(condition.getKeyword()),
                        itemPriceGoe(condition.getMinPrice()),
                        itemPriceLoe(condition.getMaxPrice())
                )
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(itemOrderBy(condition.getOrderField(), condition.isASC()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(item.count())
                .from(item)
                .where(
                        itemContainKeyword(condition.getKeyword()),
                        itemPriceGoe(condition.getMinPrice()),
                        itemPriceLoe(condition.getMaxPrice())
                );

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::fetchOne);
    }


    private BooleanExpression itemContainKeyword(String keyword) {
        return hasText(keyword) ? item.itemName.contains(keyword).or(item.itemTitle.contains(keyword)) : null;
    }

    private BooleanExpression itemPriceGoe(Integer minPrice) {
        return minPrice != null ? item.price.goe(minPrice) : null;
    }

    private BooleanExpression itemPriceLoe(Integer maxPrice) {
        return maxPrice != null ? item.price.loe(maxPrice) : null;
    }

    private OrderSpecifier<?> itemOrderBy(String orderField, boolean isASC) {
        if (!hasText(orderField)) {
            return item.itemId.desc();
        }

        switch (orderField) {
            case "itemName" -> {
                return (isASC == true) ? item.itemName.asc() : item.itemName.desc();
            }
            case "price" -> {
                return (isASC == true) ? item.price.asc() : item.price.desc();
            }
            default -> {
                return item.itemId.desc();
            }
        }
    }
}
