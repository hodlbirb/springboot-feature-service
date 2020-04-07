package com.moneylion.feature;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import java.util.Optional;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, FeatureId> {

    @Lock(LockModeType.OPTIMISTIC)
    @Cacheable(value = "features", key = "#id.email")
    Optional<Feature> findById(FeatureId id);
}
