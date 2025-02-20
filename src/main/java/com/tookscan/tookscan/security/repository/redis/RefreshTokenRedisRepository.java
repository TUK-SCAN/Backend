package com.tookscan.tookscan.security.repository.redis;

import com.tookscan.tookscan.security.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByValue(String value);
}
