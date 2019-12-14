package org.workshop.api.testing.api.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

public class Geometry {

    @JsonProperty("type")
    private String type;
    @JsonProperty("coordinates")
    private ArrayList<ArrayList<String>> coordinates;

    public Geometry() {}

    public Geometry(String type, ArrayList<ArrayList<String>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    @ApiModelProperty("")
    public ArrayList<ArrayList<String>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<String>> coordinates) {
        this.coordinates = coordinates;
    }

    @ApiModelProperty("")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
