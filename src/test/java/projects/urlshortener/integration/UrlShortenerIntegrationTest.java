package projects.urlshortener.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import projects.urlshortener.dto.CreateShortUrlResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlShortenerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCreateUrlAndRedirect() throws Exception {
        var result =
                mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "url": "https://example.com"
                                }
                                """))
                        .andExpect(status().isCreated())
                        .andReturn();

        var response = result.getResponse().getContentAsString();

        CreateShortUrlResponse createShortUrlResponse =
                objectMapper.readValue(response, CreateShortUrlResponse.class);

        var shortUrl = createShortUrlResponse.shortUrl();
        var uri = URI.create(shortUrl);

        mockMvc.perform(get(uri.getPath()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://example.com"));
    }
}
