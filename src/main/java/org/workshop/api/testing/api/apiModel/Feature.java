package org.workshop.api.testing.api.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Feature {

    @JsonProperty("type")
    private String type;
    @JsonProperty("geometry")
    private Geometry geometry;
    @JsonProperty("properties")
    private StationProperties properties;

    public Feature(String type, Geometry geometry, StationProperties properties) {
        this.type = type;
        this.geometry = geometry;
        this.properties = properties;
    }

    public Feature() {
    }

    @ApiModelProperty("")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ApiModelProperty("")
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @ApiModelProperty("")
    public StationProperties getProperties() {
        return properties;
    }

    public void setProperties(StationProperties properties) {
        this.properties = properties;
    }
}
