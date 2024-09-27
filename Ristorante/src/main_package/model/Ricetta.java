package main_package.model;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ricetta {
    private String nome;
    private Map<String, Double> ingredienti;
    private int porzioni;
    private double caricoLavPerPorzione;

    public Ricetta(String nome, Map<String, Double> ingredienti, int porzioni, double caricoLavPerPorzione) {
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.porzioni = porzioni;
        this.caricoLavPerPorzione = caricoLavPerPorzione;
    }

    public String getNome() {
        return nome;
    }

    public Map<String, Double> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(Map<String, Double> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public int getPorzioni() {
        return porzioni;
    }

    public double getCaricoLavPerPorzione() {
        return caricoLavPerPorzione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPorzioni(int porzioni) {
        this.porzioni = porzioni;
    }

    public void setCaricoLavPerPorzione(double caricoLavPerPorzione) {
        this.caricoLavPerPorzione = caricoLavPerPorzione;
    }

    public void aggiungiIngrediente(String ingrediente, double quantita) {
        this.ingredienti.put(ingrediente, quantita);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ricetta ricetta = (Ricetta) o;
        return getPorzioni() == ricetta.getPorzioni() && Double.compare(ricetta.getCaricoLavPerPorzione(), getCaricoLavPerPorzione()) == 0 && getNome().equals(ricetta.getNome()) && equalsIngedienti(ricetta.getIngredienti());
    }

    private boolean equalsIngedienti(Map<String, Double> ingredientiRicetta) {
        if (ingredienti == null || ingredientiRicetta == null) return false;
        if (ingredienti.size() != ingredientiRicetta.size()) return false;

        for (Map.Entry<String, Double> entry : ingredienti.entrySet()) {
            String thisName = entry.getKey();
            Double thisQuantita = entry.getValue();
            Double quantitaIngredientiRicetta = ingredientiRicetta.get(thisName);

            if (Double.compare(thisQuantita, quantitaIngredientiRicetta) != 0) {
                return false;
            }
        }

        return true;
    }

}
