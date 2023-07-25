//package com.project.ohouclonecoding.repository;
//
//import com.project.ohouclonecoding.dto.ItemResponseDto;
//import com.project.ohouclonecoding.dto.ItemSearchDto;
//import com.project.ohouclonecoding.entity.Item;
//import com.project.ohouclonecoding.repository.Item.ItemRepository;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//class ItemRepositoryTest {
//
//    @Autowired
//    ItemRepository itemRepository;
//    @Autowired
//    EntityManager entityManager;
//
//
//    @BeforeEach
//    void setUp() {
//        Item item1 = new Item("name1", "111", 18000, "storeimagename1");
//        Item item2 = new Item("name2", "133", 8000, "storeimagename1");
//        Item item3 = new Item("name3", "222g", 15000, "storeimagename2");
//        Item item4 = new Item("name4", "333", 12000, "storeimagename2");
//        Item item5 = new Item("ngame5", "123", 7000, "storeimagename2");
//        Item item6 = new Item("name6", "233g", 5000, "storeimagename2");
//        List<Item> items = new ArrayList<>(Arrays.asList(item1, item2, item3, item4, item5, item6));
//        itemRepository.saveAll(items);
//
//        entityManager.flush();
//        entityManager.clear();
//    }
//
//    @Test
//    public void itemHomeTest() throws Exception {
//
//        PageRequest pageRequest = PageRequest.of(0, 3);
//
//
//        Page<Item> all = itemRepository.findAllByOrderByItemIdDesc(pageRequest);
//
//        for (Item item : all) {
//            System.out.println("item.getItemName() = " + item.getItemName());
//        }
//
//        // then
//        assertThat(all.getSize()).isEqualTo(3);
//        assertThat(all.getTotalElements()).isEqualTo(6);
//    }
//
//    @Test
//    public void searchTestJPQL() throws Exception {
//
//        List<ItemResponseDto> itemResponseDtos = itemRepository.searchItemsByKeywordAndPriceAndTitle(100, 10000, "3");
//
//        for (ItemResponseDto itemResponseDto : itemResponseDtos) {
//            System.out.println("itemResponseDto.getItemTitle() = " + itemResponseDto.getItemTitle());
//        }
//
//    }
//
//    @Test
//    public void searchQuerydsl() throws Exception {
//        // given
//        ItemSearchDto dto = ItemSearchDto.builder()
//                .keyword("g")
//                .size(5)
//                .page(1)
//                .isASC(false)
//                .build();
//
//        // when
//        Page<ItemResponseDto> result = itemRepository.searchItems(dto);
//        for (ItemResponseDto itemResponseDto : result) {
//            System.out.println("itemResponseDto = " + itemResponseDto);
//        }
//
//        // then
////        assertThat(result.getSize()).isEqualTo(5);
////        assertThat(result.getTotalElements()).isEqualTo(3);
////        assertThat(result.getTotalPages()).isEqualTo(1);
////        assertThat(result.getContent()).extracting("itemName").contains("ngame5", "name3", "name6");
//    }
//}