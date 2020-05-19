package webcrawler.shopping.swipe.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webcrawler.shopping.swipe.domain.Item;
import webcrawler.shopping.swipe.model.ItemIdImageUrlMap;
import webcrawler.shopping.swipe.service.impl.CollectorServiceImpl;
import webcrawler.shopping.swipe.service.impl.CommonCrawlingServiceImpl;

import java.io.IOException;
import java.util.List;


/**
 * 크롤링 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/item")
public class CrawlingController {

    private final CommonCrawlingServiceImpl commonCrawlingService;
    private final CollectorServiceImpl collectorService;

    public CrawlingController(CommonCrawlingServiceImpl commonCrawlingService,
                              CollectorServiceImpl collectorService){
        this.commonCrawlingService = commonCrawlingService;
        this.collectorService = collectorService;
    }

    /**
     * 전체 쇼핑몰 데이터 크롤링 + DB 업데이트
     * 1시간에 한 번으로 스케쥴링
     * @throws IOException
     */
    @Scheduled(cron = "0 */1 * * * *")
    public List<Item> updateItems() throws IOException {
        return collectorService.collectAndUpdateAllItems();
    }

    /**
     * 카드덱 전체 상품 조회 (100개)
     * @return List<Item>
     */
    @GetMapping
    public List<Item> getItemsForCardDeck(){
        return commonCrawlingService.get100Items();
    }

    /**
     * 카드덱 전체 상품(id-imageUrl) 조회 (100개)
     * @return List<ItemIdImageUrlMap>
     */
    @GetMapping("/image")
    public List<ItemIdImageUrlMap> getItemsIdImageUrlForCardDeck(){
        return commonCrawlingService.get100ItemsIdImageUrlMap();
    }
}
