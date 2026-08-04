package projects.urlshortener.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UrlShortenerServiceTest {
    private final UrlShortenerService urlShortenerService = new UrlShortenerService();

    @Test
    void shouldStoreAndRetrieveUrl() {
        var url = "https://example.com";
        var shortCode = urlShortenerService.shorten(url);
        var urlOptional = urlShortenerService.getOriginalUrl(shortCode);

        assertThat(urlOptional).contains(url);
    }

    @Test
    void shouldReturnEmptyWhenShortCodeDoesNotExist() {
        var urlOptional = urlShortenerService.getOriginalUrl("does-not-exist");
        assertThat(urlOptional).isEmpty();
    }

    @Test
    void shouldGenerateUniqueShortCodes() {
        var code1 = urlShortenerService.shorten("https://google.com");
        var code2 = urlShortenerService.shorten("https://openai.com");

        assertThat(code1).isNotEqualTo(code2);
    }
}
