package model;

import model.exception.UsuarioNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 24L;

    protected String nome;
    protected String login;
    protected String senha;

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario(String nome, String login, String senha ) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    /*public String getEndereco() {
        return endereco;
    }*/
}
