package projects.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(nullable = false)
    private String originalUrl;

    public UrlMapping(Long id, String shortCode, String originalUrl) {
        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
    }

    public UrlMapping(String shortCode, String originalUrl) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
    }

    public UrlMapping(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
