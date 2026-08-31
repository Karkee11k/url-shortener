package projects.urlshortener.service;

import org.springframework.stereotype.Service;
import projects.urlshortener.entity.UrlMapping;
import projects.urlshortener.repository.UrlRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository repository) {
        this.urlRepository = repository;
    }

    public String shorten(String originalUrl) {
        var code = UUID.randomUUID().toString();
        var urlMapping = new UrlMapping(code, originalUrl);
        urlRepository.save(urlMapping);
        return code;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return urlRepository
                .findByShortCode(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }
}
