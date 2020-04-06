package com.moneylion.feature;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(FeatureId.class)
public class Feature {
    @Id
    @Column(nullable = false)
    private String email;
    @Id
    @Column(nullable = false)
    private String featureName;

    @Column(nullable = false)
    private boolean state;

    public Feature() {
        super();
    }
    public Feature(String email, String featureName, boolean state) {
        super();
        this.email = email;
        this.featureName = featureName;
        this.state = state;
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
    public boolean getState() {
        return this.state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
}

