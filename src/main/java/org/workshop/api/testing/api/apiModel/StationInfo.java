package org.workshop.api.testing.api.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class StationInfo {
    @JsonProperty("id_expl")
    private String id_expl = null;
    @JsonProperty("id_planeamento")
    private String id_planeamento = null;
    @JsonProperty("descricao")
    private String descricao = null;
    @JsonProperty("ocupacao")
    private String ocupacao = null;
    @JsonProperty("estado")
    private String estado = null;
    @JsonProperty("desig_comercial")
    private String desig_comercial = null;
    @JsonProperty("tipo_servico_niveis")
    private String tipo_servico_niveis = null;
    @JsonProperty("num_bicicletas")
    private String num_bicicletas = null;
    @JsonProperty("num_docas")
    private String num_docas = null;
    @JsonProperty("racio")
    private String racio = null;
    @JsonProperty("implantacao_em")
    private String implantacao_em = null;
    @JsonProperty("tipo_implantacao")
    private String tipo_implantacao = null;
    @JsonProperty("descont_docas")
    private String descont_docas = null;
    @JsonProperty("observacoes")
    private String observacoes = null;
    @JsonProperty("latitude")
    private String latitude = null;
    @JsonProperty("longitude")
    private String longitude = null;

    public StationInfo(String id_expl, String id_planeamento, String descricao, String ocupacao, String estado,
                       String desig_comercial, String tipo_servico_niveis, String num_bicicletas, String num_docas,
                       String racio, String implantacao_em, String tipo_implantacao, String descont_docas,
                       String observacoes, String latitude, String longitude) {
        this.id_expl = id_expl;
        this.id_planeamento = id_planeamento;
        this.descricao = descricao;
        this.ocupacao = ocupacao;
        this.estado = estado;
        this.desig_comercial = desig_comercial;
        this.tipo_servico_niveis = tipo_servico_niveis;
        this.num_bicicletas = num_bicicletas;
        this.num_docas = num_docas;
        this.racio = racio;
        this.implantacao_em = implantacao_em;
        this.tipo_implantacao = tipo_implantacao;
        this.descont_docas = descont_docas;
        this.observacoes = observacoes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StationInfo() {}

    @ApiModelProperty("")
    public String getId_expl() {
        return id_expl;
    }

    public void setId_expl(String id_expl) {
        this.id_expl = id_expl;
    }

    @ApiModelProperty("")
    public String getId_planeamento() {
        return id_planeamento;
    }

    public void setId_planeamento(String id_planeamento) {
        this.id_planeamento = id_planeamento;
    }

    @ApiModelProperty("")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @ApiModelProperty("")
    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    @ApiModelProperty("")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @ApiModelProperty("")
    public String getDesig_comercial() {
        return desig_comercial;
    }

    public void setDesig_comercial(String desig_comercial) {
        this.desig_comercial = desig_comercial;
    }

    @ApiModelProperty("")
    public String getTipo_servico_niveis() {
        return tipo_servico_niveis;
    }

    public void setTipo_servico_niveis(String tipo_servico_niveis) {
        this.tipo_servico_niveis = tipo_servico_niveis;
    }

    @ApiModelProperty("")
    public String getNum_bicicletas() {
        return num_bicicletas;
    }

    public void setNum_bicicletas(String num_bicicletas) {
        this.num_bicicletas = num_bicicletas;
    }

    @ApiModelProperty("")
    public String getNum_docas() {
        return num_docas;
    }

    public void setNum_docas(String num_docas) {
        this.num_docas = num_docas;
    }

    @ApiModelProperty("")
    public String getRacio() {
        return racio;
    }

    public void setRacio(String racio) {
        this.racio = racio;
    }

    @ApiModelProperty("")
    public String getImplantacao_em() {
        return implantacao_em;
    }

    public void setImplantacao_em(String implantacao_em) {
        this.implantacao_em = implantacao_em;
    }

    @ApiModelProperty("")
    public String getTipo_implantacao() {
        return tipo_implantacao;
    }

    public void setTipo_implantacao(String tipo_implantacao) {
        this.tipo_implantacao = tipo_implantacao;
    }

    @ApiModelProperty("")
    public String getDescont_docas() {
        return descont_docas;
    }

    public void setDescont_docas(String descont_docas) {
        this.descont_docas = descont_docas;
    }

    @ApiModelProperty("")
    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @ApiModelProperty("")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @ApiModelProperty("")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}