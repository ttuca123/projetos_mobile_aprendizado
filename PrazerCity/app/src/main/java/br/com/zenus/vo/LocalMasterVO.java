package br.com.zenus.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.zenus.entidades.Local;

/**
 * Created by Tuca on 14/06/2017.
 */

public class LocalMasterVO {

    @SerializedName("local")
    private List<Local> locais;

    public LocalMasterVO(List<Local> locais){

        this.locais = locais;
    }


    public void setLocais(List<Local> locais)
    {
        this.locais = locais;
    }

    public List<Local> getLocais(){

        return locais;
    }

}
