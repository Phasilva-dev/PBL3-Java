package model;

import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    protected String nome;
    protected double valor;
    private final int produtoID;

    public Produto (String nome, double valor) throws ProdutoNaoCadastrouException{
        if (nome.isBlank() || valor <= 0){
            throw new ProdutoNaoCadastrouException("Nome ou valor invalido");
        }
        this.nome = nome;
        this.valor = valor;
        this.produtoID = IDGenerator.getInstancia().getNovoProdutoID();
    }
    public Produto(Produto produto){
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.produtoID = produto.getProdutoID();
    }
    public String getNome() {
        return nome;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public int getProdutoID() {
        return produtoID;
    }
}
