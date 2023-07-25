package com.project.ohouclonecoding.service;


import com.project.ohouclonecoding.dto.ItemRequestDto;
import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.dto.ItemSearchDto;
import com.project.ohouclonecoding.entity.Item;
import com.project.ohouclonecoding.repository.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    @Qualifier(value = "itemS3ImageService")
    private final ImageMangerService itemS3Service;
    private final ItemRepository itemRepository;


    @Transactional
    public ItemResponseDto createItem(ItemRequestDto requestDto, MultipartFile itemImage) throws IOException {
        // S3 이미지 저장
        String storedImageName = itemS3Service.uploadImageFile(itemImage);


        try {
            Item savedItem = itemRepository.save(new Item(requestDto.getItemName(), requestDto.getItemTitle(), requestDto.getPrice(), storedImageName));
            return new ItemResponseDto(savedItem);

        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new IllegalArgumentException("저장에 실패하였습니다");
        }
    }

    public Page<ItemResponseDto> getItems(Pageable pageable) {
        Page<Item> items = itemRepository.findAllByOrderByItemIdDesc(pageable);
        return items.map(ItemResponseDto::new);
    }

    public ItemResponseDto getOneItem(Long itemId) {
        Item findItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 찾을 수 없습니다"));

        return new ItemResponseDto(findItem);
    }
}
