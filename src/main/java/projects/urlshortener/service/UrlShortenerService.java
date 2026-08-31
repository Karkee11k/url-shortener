package projects.urlshortener.service;

import org.springframework.stereotype.Service;
import projects.urlshortener.encoder.Base62Encoder;
import projects.urlshortener.entity.UrlMapping;
import projects.urlshortener.repository.UrlRepository;

import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final Base62Encoder encoder = new Base62Encoder();

    public UrlShortenerService(UrlRepository repository) {
        this.urlRepository = repository;
    }

    public String shorten(String originalUrl) {
        var urlMapping = new UrlMapping(originalUrl);
        var savedUrlMapping = urlRepository.save(urlMapping);
        var code = encoder.encode(savedUrlMapping.getId());
        savedUrlMapping.setShortCode(code);
        urlRepository.save(savedUrlMapping);
        return code;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return urlRepository
                .findByShortCode(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }
}
