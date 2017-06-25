package br.com.zenus.vo;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tuca on 25/06/2017.
 */


public class AtualizacaoLocalVO {


    @SerializedName("seq_atualizacao")
    private Long seqAtualizacao;

    @SerializedName("codigoHash")
    private String codigoHash;

    @SerializedName("dtAtualizacao")
    private String dtAtualizacao;

    public Long getSeqAtualizacao() {
        return this.seqAtualizacao;
    }

    public void setSeqAtualizacao(Long seqAtualizacao) {
        this.seqAtualizacao = seqAtualizacao;
    }

    public String getCodigoHash() {
        return this.codigoHash;
    }

    public void setCodigoHash(String codigoHash) {
        this.codigoHash = codigoHash;
    }

    public Date getDtAtualizacao() {

        if(this.dtAtualizacao==null){

            return new Date();
        }

        return new SimpleDateFormat(this.dtAtualizacao).getCalendar().getTime();
    }

    public void setDtAtualizacao(String dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }




}
