package br.com.zenus.entidades;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Collection;

/**
 * Created by Tuca on 23/04/2017.
 */

@Entity(nameInDb = "local")
public class Local {


    @Id(autoincrement = true)
    @Property(nameInDb = "seq_local")
    @SerializedName("seq_local")
    private Long seqLocal;

    @Property(nameInDb = "nome")
    @SerializedName("nome")
    private String nome;

    @Property(nameInDb = "fone")
    @SerializedName("fone")
    private String telefone;

    @Property(nameInDb = "latitude")
    @SerializedName("latitude")
    private Double latitude;

    @Property(nameInDb = "longitude")
    @SerializedName("longitude")
    private Double longitude;

    @Property(nameInDb = "avaliacao")
    @SerializedName("avaliacao")
    private Double avaliacao;

    @Generated(hash = 1293733909)
    public Local(Long seqLocal, String nome, String telefone, Double latitude,
            Double longitude, Double avaliacao) {
        this.seqLocal = seqLocal;
        this.nome = nome;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avaliacao = avaliacao;
    }

    @Generated(hash = 1337064102)
    public Local() {
    }

    public Long getSeqLocal() {
        return this.seqLocal;
    }

    public void setSeqLocal(Long seqLocal) {
        this.seqLocal = seqLocal;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }




   

}
