package projects.urlshortener.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import projects.urlshortener.entity.UrlMapping;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Test
    void shouldFindUrlByShortCode() {
        var urlMapping = new UrlMapping("abc123", "https://example.com");

        urlRepository.save(urlMapping);

        var urlMappingOptional = urlRepository.findByShortCode("abc123");

        assertThat(urlMappingOptional).isPresent();

        assertThat(urlMappingOptional.get().getShortCode())
                .isEqualTo("abc123");
        assertThat(urlMappingOptional.get().getOriginalUrl())
                .isEqualTo("https://example.com");
    }

    @Test
    void shouldReturnEmptyWhenShortCodeDoesNotExist() {
        var urlMappingOptional = urlRepository.findByShortCode("does-not-exist");

        assertThat(urlMappingOptional).isEmpty();
    }
}