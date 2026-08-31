package projects.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.urlshortener.entity.UrlMapping;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
}
