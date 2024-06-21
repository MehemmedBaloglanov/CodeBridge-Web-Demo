package demo.codeBridge.service.serviceImpl;

import demo.codeBridge.dto.request.NewsRequestDto;
import demo.codeBridge.dto.response.NewsDto;
import demo.codeBridge.entity.NewsEntity;
import demo.codeBridge.exception.NotFoundException;
import demo.codeBridge.repository.NewsRepository;
import demo.codeBridge.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public NewsDto createNews(NewsRequestDto newsRequestDto) {
        NewsEntity newsEntity = NewsEntity.builder()
                .title(newsRequestDto.getTitle())
                .author(newsRequestDto.getAuthor())
                .content(newsRequestDto.getContent())
                .category(newsRequestDto.getCategory())
                .publicationDate(newsRequestDto.getPublicationDate())
                .lastUpdatedDate(LocalDateTime.now())
                .imageUrl(newsRequestDto.getImageUrl())
                .build();
        newsRepository.save(newsEntity);
        return mapToResponseDto(newsEntity);
    }

    @Override
    public NewsDto updateNews(Long id,NewsRequestDto newsRequestDto) {
        final NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found with ID: " + id));
        newsEntity.setTitle(newsRequestDto.getTitle());
        newsEntity.setAuthor(newsRequestDto.getAuthor());
        newsEntity.setContent(newsRequestDto.getContent());
        newsEntity.setCategory(newsRequestDto.getCategory());
        newsEntity.setPublicationDate(newsRequestDto.getPublicationDate());
        newsEntity.setLastUpdatedDate(LocalDateTime.now());
        newsEntity.setImageUrl(newsRequestDto.getImageUrl());
        newsRepository.save(newsEntity);
        return mapToResponseDto(newsEntity);
    }

    @Override
    public NewsDto getNews(Long id) {
        final NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found with ID: " + id));
        incrementViewCount(id);
        return mapToResponseDto(newsEntity);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public Page<NewsDto> getList(Pageable pageable) {
        return newsRepository.findAll(pageable).map(this::mapToResponseDto);
    }

    private NewsDto mapToResponseDto(NewsEntity newsEntity){
        NewsDto newsDto = new NewsDto();
        newsDto.setId(newsEntity.getId());
        newsDto.setTitle(newsEntity.getTitle());
        newsDto.setAuthor(newsEntity.getAuthor());
        newsDto.setContent(newsEntity.getContent());
        newsDto.setCategory(newsEntity.getCategory());
        newsDto.setPublicationDate(newsEntity.getPublicationDate());
        newsDto.setLastUpdatedDate(newsEntity.getLastUpdatedDate());
        newsDto.setImageUrl(newsEntity.getImageUrl());
        newsDto.setViewCount(newsEntity.getViewCount());
        return newsDto;
    }

    private void incrementViewCount(Long id){
        final NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("News not found with ID: " + id));
        newsEntity.setViewCount(newsEntity.getViewCount()+1);
        newsRepository.save(newsEntity);
    }
}
