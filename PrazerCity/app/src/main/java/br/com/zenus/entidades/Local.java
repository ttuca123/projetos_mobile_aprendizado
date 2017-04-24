package br.com.zenus.entidades;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Tuca on 23/04/2017.
 */

@Entity(nameInDb = "local")
public class Local {


    @Id(autoincrement = true)
    @Property(nameInDb = "seq_local")
    private Long seqLocal;

    @Property(nameInDb = "nome")
    private String nome;

    @Property(nameInDb = "fone")
    private Long telefone;

    @Property(nameInDb = "latitude")
    private Double latitude;

    @Property(nameInDb = "longitude")
    private Double longitude;

    @Generated(hash = 102496982)
    public Local(Long seqLocal, String nome, Long telefone, Double latitude,
            Double longitude) {
        this.seqLocal = seqLocal;
        this.nome = nome;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Long getTelefone() {
        return this.telefone;
    }

    public void setTelefone(Long telefone) {
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


}
