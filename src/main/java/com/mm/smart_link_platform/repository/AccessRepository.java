package com.mm.smart_link_platform.repository;

import com.mm.smart_link_platform.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessRepository extends JpaRepository<AccessLog, UUID> {

    Long countByLinkId(UUID linkId);

    @Query("SELECT COUNT(DISTINCT a.ip) FROM access a WHERE a.linkId = :linkId")
    Long countUniqueVisitors(@Param("linkId") UUID linkId);

    List<AccessLog> findTop10ByLinkIdOrderByAccessTimeDesc(UUID linkId);

}
