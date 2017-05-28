package br.com.android.chat;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Tuca on 04/05/2017.
 */

@Entity(nameInDb = "usuario")
public class Usuario {

    @Id(autoincrement = true)
    @Property(nameInDb = "seq_usuario")
    private long id;

    @Property(nameInDb = "nome")
    @SerializedName("nome")
    private String nome;

    @Property(nameInDb = "email")
    @SerializedName("email")
    private String email;

    @Property(nameInDb = "senha")
    @SerializedName("senha")
    private String senha;

    @Generated(hash = 1438305259)
    public Usuario(long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Generated(hash = 562950751)
    public Usuario() {
    }

    public long getId() {
        return this.id;
    }



    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}


