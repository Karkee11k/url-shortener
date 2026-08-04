package projects.urlshortener.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import projects.urlshortener.service.UrlShortenerService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UrlShortenerService urlShortenerService;

    @Test
    void shouldCreateShortUrl() throws Exception {
        var originalUrl = "https://example.com";

        when(urlShortenerService.shorten(originalUrl))
                .thenReturn("abc123");

        mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "url": "%s"
                         }
                        """.formatted(originalUrl)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/abc123"));

        verify(urlShortenerService).shorten(originalUrl);
    }

    @Test
    void shouldReturnBadRequestWhenUrlIsBlank() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                           "url": ""
                         }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUrlFieldIsMissing() throws Exception {
        mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUrlIsNotValid() throws Exception {
        mockMvc.perform(post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "url": "hello"
                        }
                        """))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldRedirectToOriginalUrl() throws Exception {
        var originalUrl = "https://example.com";

        when(urlShortenerService.getOriginalUrl("abc123"))
                .thenReturn(Optional.of(originalUrl));

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalUrl));

        verify(urlShortenerService).getOriginalUrl("abc123");
    }

    @Test
    void shouldReturnNotFoundWhenOriginalUrlIsNotPresent() throws Exception {
        when(urlShortenerService.getOriginalUrl("xyz123"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/xyz123"))
                .andExpect(status().isNotFound());

        verify(urlShortenerService).getOriginalUrl("xyz123");
    }
}