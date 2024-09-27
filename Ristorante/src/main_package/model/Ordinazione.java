package main_package.model;

/*
classe di astrazione per indicare che menu tematico e piatto sono prenotabili.
non abbiamo la classe Menu perché si tratta semplicemente di una lista di piatti, quelli validi il tal giorno e non ha neanche
associato un nome.
 */
abstract public class Ordinazione {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
