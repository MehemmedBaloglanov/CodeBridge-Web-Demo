package demo.codeBridge.service;

import demo.codeBridge.dto.request.NewsRequestDto;
import demo.codeBridge.dto.response.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsDto createNews(NewsRequestDto newsRequestDto);

    NewsDto updateNews(Long id,NewsRequestDto newsRequestDto);

    NewsDto getNews(Long id);

    void deleteNews(Long id);

    Page<NewsDto> getList(Pageable pageable);
}
