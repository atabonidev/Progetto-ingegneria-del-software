package main_package.model;

import java.util.Objects;

public class Prodotto {
    private String nome;
    private TipoProdotto tipo;
    private UnitaMisura unitaMisura;

    public Prodotto(String nome, TipoProdotto tipo, UnitaMisura unitaMisura) {
        this.nome = nome;
        this.tipo = tipo;
        this.unitaMisura = unitaMisura;
    }

    @Override
    public boolean equals(Object altroProdotto) {
        if (altroProdotto == null || getClass() != altroProdotto.getClass()) return false;
        Prodotto prodotto = (Prodotto) altroProdotto;
        return nome.equals(prodotto.nome) && tipo.equals(prodotto.getTipo()) && unitaMisura.equals(prodotto.getUnitaMisura());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, tipo, unitaMisura);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoProdotto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProdotto tipo) {
        this.tipo = tipo;
    }

    public UnitaMisura getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(UnitaMisura unitaMisura) {
        this.unitaMisura = unitaMisura;
    }
    
}
