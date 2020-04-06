package com.moneylion.feature;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.Cacheable;
import java.util.Optional;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, FeatureId> {

  @Cacheable("feature")
  Optional<Feature> findById(FeatureId id);
}
