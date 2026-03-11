package com.mm.smart_link_platform.repository;

import com.mm.smart_link_platform.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository {

    Optional<Link> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
