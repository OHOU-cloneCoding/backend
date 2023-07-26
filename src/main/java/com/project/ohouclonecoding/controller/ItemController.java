package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.dto.ItemRequestDto;
import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.dto.ItemSearchDto;
import com.project.ohouclonecoding.dto.MessageResponseDto;
import com.project.ohouclonecoding.repository.Item.ItemRepository;
import com.project.ohouclonecoding.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    @PostMapping("/items")
//    public ItemResponseDto createItem(@ModelAttribute ItemRequestDto requestDto) throws IOException {
    public MessageResponseDto createItem(
            @RequestPart ItemRequestDto requestDto,
            @RequestPart("itemImage") MultipartFile itemImage
    ) throws IOException {
        itemService.createItem(requestDto, itemImage);
        return new MessageResponseDto("상품 등록 성공");
    }


    @GetMapping("/items/home")
    public Page<ItemResponseDto> getItems(Pageable pageable) {
        return itemService.getItems(pageable);
    }

    @GetMapping("/items/{itemId}")
    public ItemResponseDto getOneItem(@PathVariable("itemId") Long itemId) {
        return itemService.getOneItem(itemId);
    }

    @GetMapping("/items/search")
    public Page<ItemResponseDto> searchItems(@ModelAttribute ItemSearchDto condition) {
        return itemRepository.searchItems(condition);
    }
}
