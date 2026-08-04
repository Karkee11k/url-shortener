package projects.urlshortener.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {
    private final Map<String, String> urls = new HashMap<>();

    public String shorten(String originalUrl) {
        var code = UUID.randomUUID().toString();
        urls.put(code, originalUrl);
        return code;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return Optional.ofNullable(urls.get(shortCode));
    }
}
