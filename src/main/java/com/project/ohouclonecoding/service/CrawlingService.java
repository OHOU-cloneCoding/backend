package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.CrawlingDto;
import com.project.ohouclonecoding.dto.ItemResponseDto;
import com.project.ohouclonecoding.entity.Item;
import com.project.ohouclonecoding.repository.Item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final ItemRepository itemRepository;
    @Transactional

    public void crawling() {
        Document document = null;
        String url = "https://www.guud.com/index";
        try {
            document = Jsoup.connect(url).get();
        }catch (IOException e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        Elements elements = document.select("div > div.swiper-wrapper > div");
        ArrayList<CrawlingDto> crawlingDtos = new ArrayList<>();
        int count = 40;
        for (Element element : elements) {
            // image
            String itemImg = element.select("span.img > a > img").attr("data-src");
            String itemTitle = element.select("span.img > a > img").attr("data-alt");
            String itemName = element.select("span.brand").text();
            String price = (element.select("span.del").text());
            if(!itemImg.isEmpty() && !itemTitle.isEmpty() && !itemName.isEmpty() && !price.isEmpty()){
                price = price.replace(",", "");
                Integer inputPrice =Integer.parseInt(price);
                CrawlingDto crawlingDto = new CrawlingDto(itemImg, itemTitle, itemName, inputPrice);
                crawlingDtos.add(crawlingDto);
                count --;
            }
            if(count<0){
                break;
            }
        }
        List<Item> crawlingList = crawlingDtos.stream().map(Item::new).toList();
        itemRepository.saveAll(crawlingList);
    }
}
