package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.dto.ItemRequestDto;
import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
//    public ItemResponseDto createItem(@ModelAttribute ItemRequestDto requestDto) throws IOException {
    public ItemResponseDto createItem(
            @RequestParam("itemName") String itemName,
            @RequestParam("itemTitle") String itemTitle,
            @RequestParam("price") Integer price,
            @RequestParam("itemImage") MultipartFile itemImage
    ) throws IOException {
        ItemRequestDto requestDto = new ItemRequestDto(itemName, itemTitle, price, itemImage);
        return itemService.createItem(requestDto);
    }

    @GetMapping("/items")
    public List<ItemResponseDto> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/items/{itemId}")
    public ItemResponseDto getOneItem(@PathVariable("itemId") Long itemId) {
        return itemService.getOneItem(itemId);
    }

}
