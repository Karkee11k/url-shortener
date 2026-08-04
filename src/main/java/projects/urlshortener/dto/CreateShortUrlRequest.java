package projects.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateShortUrlRequest(
        @NotBlank(message = "URL must not be blank")
        @URL(message = "Provide a valid URL")
        String url
) {}
