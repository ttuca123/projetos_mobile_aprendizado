package br.com.zenus.entidades;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by Tuca on 25/06/2017.
 */

@Entity(nameInDb = "atualizacao_local")
public class AtualizacaoLocal {



    @Id(autoincrement = true)
    @Property(nameInDb = "seq_atualizacao")
    @SerializedName("seq_atualizacao")
    private Long seqAtualizacao;

    @Property(nameInDb = "codigo_hash")
    @SerializedName("codigoHash")
    private String codigoHash;

    @Property(nameInDb = "dt_atualizacao")
    @SerializedName("dtAtualizacao")
    private Date dtAtualizacao;

    @Generated(hash = 655566744)
    public AtualizacaoLocal(Long seqAtualizacao, String codigoHash,
            Date dtAtualizacao) {
        this.seqAtualizacao = seqAtualizacao;
        this.codigoHash = codigoHash;
        this.dtAtualizacao = dtAtualizacao;
    }

    @Generated(hash = 1481804632)
    public AtualizacaoLocal() {
    }

    public Long getSeqAtualizacao() {
        return this.seqAtualizacao;
    }

    public void setSeqAtualizacao(Long seqAtualizacao) {
        this.seqAtualizacao = seqAtualizacao;
    }

    public String getCodigoHash() {
        if(this.codigoHash==null){
            this.codigoHash="";
        }

        return this.codigoHash;
    }

    public void setCodigoHash(String codigoHash) {
        this.codigoHash = codigoHash;
    }

    public Date getDtAtualizacao() {
        return this.dtAtualizacao;
    }

    public void setDtAtualizacao(Date dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }





}
