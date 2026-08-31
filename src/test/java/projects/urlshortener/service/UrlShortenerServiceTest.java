package projects.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projects.urlshortener.entity.UrlMapping;
import projects.urlshortener.repository.UrlRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    UrlShortenerService urlShortenerService;

    @Test
    void shouldReturnOriginalUrlWhenShortCodeExists() {
        var url = "https://example.com";

        when(urlRepository.findByShortCode("abc123"))
                .thenReturn(Optional.of(new UrlMapping("abc123", url)));

        var urlOptional = urlShortenerService.getOriginalUrl("abc123");

        assertThat(urlOptional).contains(url);
    }

    @Test
    void shouldReturnEmptyWhenShortCodeDoesNotExist() {
        when(urlRepository.findByShortCode(any(String.class)))
                .thenReturn(Optional.empty());

        var urlOptional = urlShortenerService.getOriginalUrl("does-not-exist");
        assertThat(urlOptional).isEmpty();
    }

    @Test
    void shouldGenerateUniqueShortCodes() {
        var code1 = urlShortenerService.shorten("https://google.com");
        var code2 = urlShortenerService.shorten("https://openai.com");

        assertThat(code1).isNotEqualTo(code2);
    }

    @Test
    void shouldSaveUrlMapping() {
        urlShortenerService.shorten("https://example.com");
        verify(urlRepository).save(any(UrlMapping.class));
    }

    @Test
    void shouldReturnGeneratedShortCode() {
        String shortCode = urlShortenerService.shorten("https://example.com");

        assertThat(shortCode).isNotBlank();
    }
}
