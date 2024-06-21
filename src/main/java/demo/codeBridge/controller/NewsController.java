package demo.codeBridge.controller;

import demo.codeBridge.dto.request.NewsRequestDto;
import demo.codeBridge.dto.response.NewsDto;
import demo.codeBridge.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsDto> create(@RequestBody NewsRequestDto newsRequestDto){
       NewsDto createNews=newsService.createNews(newsRequestDto);
       return ResponseEntity.ok(createNews);
    }


    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> update(@PathVariable Long id,@RequestBody NewsRequestDto newsRequestDto){
        NewsDto updateNews=newsService.updateNews(id,newsRequestDto);
        return ResponseEntity.ok(updateNews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> get(@PathVariable Long id){
        NewsDto news= newsService.getNews(id);
        return ResponseEntity.ok(news);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<NewsDto>> list(Pageable pageable){
        Page<NewsDto> listNews= newsService.getList(pageable);
        return ResponseEntity.ok(listNews);
    }

}
