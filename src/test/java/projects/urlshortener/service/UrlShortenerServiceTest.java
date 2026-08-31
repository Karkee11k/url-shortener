package projects.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projects.urlshortener.entity.UrlMapping;
import projects.urlshortener.repository.UrlRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        AtomicLong id = new AtomicLong(10L);
        when(urlRepository.save(any(UrlMapping.class)))
                .thenAnswer(invocation -> {
                    UrlMapping saved = invocation.getArgument(0);
                    saved.setId(id.incrementAndGet());
                    return saved;
                });

        var code1 = urlShortenerService.shorten("https://google.com");
        var code2 = urlShortenerService.shorten("https://openai.com");

        assertThat(code1).isNotEqualTo(code2);
    }

    @Test
    void shouldSaveUrlMapping() {
        when(urlRepository.save(any(UrlMapping.class)))
                .thenAnswer(invocation -> {
                    UrlMapping saved = invocation.getArgument(0);
                    saved.setId(10L);
                    return saved;
                });

        urlShortenerService.shorten("https://example.com");

        var captor = ArgumentCaptor.forClass(UrlMapping.class);
        verify(urlRepository, times(2)).save(captor.capture());

        var secondSave = captor.getAllValues().get(1);
        assertThat(secondSave.getShortCode()).isEqualTo("a");
        assertThat(secondSave.getOriginalUrl()).isEqualTo("https://example.com");
    }

    @Test
    void shouldGenerateCodeFromPersistedId() {
        when(urlRepository.save(any(UrlMapping.class)))
                .thenAnswer(invocation -> {
                    UrlMapping saved = invocation.getArgument(0);
                    saved.setId(10L);
                    return saved;
                });

        var code = urlShortenerService.shorten("https://example.com");
        assertThat(code).isEqualTo("a");
    }
}
