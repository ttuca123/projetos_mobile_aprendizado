package br.com.zenus.entidades;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tuca on 14/06/2017.
 */

public class LocalMaster {

    @SerializedName("local")
    private List<Local> locais;

    public LocalMaster(List<Local> locais){

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
