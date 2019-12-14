package org.workshop.api.testing.api.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

public class StationList {

    @JsonProperty("type")
    private String type;
    @JsonProperty("totalFeatures")
    private Integer totalFeatures;
    @JsonProperty("features")
    private ArrayList<Feature> features;

    public StationList(String type, Integer totalFeatures, ArrayList<Feature> features) {
        this.type = type;
        this.totalFeatures = totalFeatures;
        this.features = features;
    }

    public StationList() {
    }

    @ApiModelProperty("")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ApiModelProperty("")
    public Integer getTotalFeatures() {
        return totalFeatures;
    }

    public void setTotalFeatures(Integer totalFeatures) {
        this.totalFeatures = totalFeatures;
    }

    @ApiModelProperty("")
    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
}
