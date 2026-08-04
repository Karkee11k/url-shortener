package projects.urlshortener.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.urlshortener.dto.CreateShortUrlRequest;
import projects.urlshortener.dto.CreateShortUrlResponse;
import projects.urlshortener.service.UrlShortenerService;

import java.util.Optional;

@RestController
public class UrlController {
    private final UrlShortenerService urlShortenerService;

    UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/urls")
    public ResponseEntity<CreateShortUrlResponse> createShortUrl(@Valid @RequestBody CreateShortUrlRequest request) {
        var shortUrl = urlShortenerService.shorten(request.url());
        var response = new CreateShortUrlResponse("http://localhost:8080/" + shortUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{shortCode}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortCode) {
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        return originalUrl.<ResponseEntity<Void>>map(s -> ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", s)
                .build()).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
