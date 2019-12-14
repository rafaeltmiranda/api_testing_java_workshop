package org.workshop.api.testing.api.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

public class StationProperties {

    @JsonProperty("id_expl")
    private String idExpl = null;
    @JsonProperty("id_planeamento")
    private String planningId = null;
    @JsonProperty("estado")
    private String status = null;
    @JsonProperty("desig_comercial")
    private String commercialDesign = null;
    @JsonProperty("tipo_servico_niveis")
    private String serviceLevelType = null;
    @JsonProperty("num_bicicletas")
    private Integer numOfBicycles = null;
    @JsonProperty("num_docas")
    private Integer numOfDocks = null;
    @JsonProperty("racio")
    private Integer reason = null;
    @JsonProperty("update_date")
    private String updateDate = null;
    @JsonProperty("bbox")
    private ArrayList<Float> bbox = null;

    public StationProperties(String idExpl, String planningId, String status, String commercialDesign,
                             String serviceLevelType, Integer numOfBicycles, Integer numOfDocks, Integer reason,
                             String updateDate, ArrayList<Float> bbox) {
        this.idExpl = idExpl;
        this.planningId = planningId;
        this.status = status;
        this.commercialDesign = commercialDesign;
        this.serviceLevelType = serviceLevelType;
        this.numOfBicycles = numOfBicycles;
        this.numOfDocks = numOfDocks;
        this.reason = reason;
        this.updateDate = updateDate;
        this.bbox = bbox;
    }

    public StationProperties() {}

    @ApiModelProperty("")
    public String getIdExpl() {
        return idExpl;
    }

    public void setIdExpl(String idExpl) {
        this.idExpl = idExpl;
    }

    @ApiModelProperty("")
    public String getPlanningId() {
        return planningId;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId;
    }

    @ApiModelProperty("")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ApiModelProperty("")
    public String getCommercialDesign() {
        return commercialDesign;
    }

    public void setCommercialDesign(String comercialDesign) {
        this.commercialDesign = comercialDesign;
    }

    @ApiModelProperty("")
    public String getServiceLevelType() {
        return serviceLevelType;
    }

    public void setServiceLevelType(String serviceLevelType) {
        this.serviceLevelType = serviceLevelType;
    }

    @ApiModelProperty("")
    public Integer getNumOfBicycles() {
        return numOfBicycles;
    }

    public void setNumOfBicycles(Integer numOfBicycles) {
        this.numOfBicycles = numOfBicycles;
    }

    @ApiModelProperty("")
    public Integer getNumOfDocks() {
        return numOfDocks;
    }

    public void setNumOfDocks(Integer numOfDocks) {
        this.numOfDocks = numOfDocks;
    }

    @ApiModelProperty("")
    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    @ApiModelProperty("")
    public ArrayList<Float> getBbox() {
        return bbox;
    }

    public void setBbox(ArrayList<Float> bbox) {
        this.bbox = bbox;
    }

    @ApiModelProperty("")
    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
