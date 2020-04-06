package com.moneylion.feature;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@RestController
public class FeatureController {

    public class GetFeatureResponseWrapper {
        public boolean canAccess;
    }

    public static class PostFeatureRequestWrapper {
        public String email;
        public String featureName;
        public boolean enable;
    }

    @Autowired
    private FeatureService service;

    @GetMapping("/feature")
    public ResponseEntity<GetFeatureResponseWrapper> getFeature(
        @RequestParam(value = "email") String email,
        @RequestParam(value = "featureName") String featureName) {

        GetFeatureResponseWrapper res = new GetFeatureResponseWrapper();
        FeatureId id = new FeatureId(email, featureName);
        Optional<Feature> feature = service.repository.findById(id);

        if (feature.isPresent()) {
            res.canAccess = feature.get().getState();
        } else {
            res.canAccess = false;
        }
        return new ResponseEntity<GetFeatureResponseWrapper>(res, HttpStatus.OK);
    }
    @PostMapping("/feature")
    public ResponseEntity<HttpStatus> updateFeature(@RequestBody PostFeatureRequestWrapper req) {

        FeatureId id = new FeatureId(req.email, req.featureName);
        Optional<Feature> feature_old = service.repository.findById(id);
        if (feature_old.isPresent()) {
            boolean isModified = req.enable != feature_old.get().getState();
            if(!isModified) {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_MODIFIED);
            }
            feature_old.get().setState(req.enable);
            service.repository.save(feature_old.get());
        } else {
            Feature feature_new = new Feature(req.email, req.featureName, req.enable);
            service.repository.save(feature_new);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
