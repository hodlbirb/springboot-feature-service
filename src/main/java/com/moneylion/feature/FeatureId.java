package com.moneylion.feature;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class FeatureId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String featureName;

    public FeatureId() {
    }
    public FeatureId(String email, String featureName) {
        this.email = email;
        this.featureName = featureName;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFeatureName() {
        return this.featureName;
    }
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, featureName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureId id = (FeatureId) o;
        return Objects.equals(this.email, id.getEmail())
               && Objects.equals(this.featureName, id.getFeatureName());
    }
}
