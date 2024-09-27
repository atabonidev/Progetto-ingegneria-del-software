package main_package.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
Classe che fa da modello per tutte le classi costituite da una lista lista di coppie (o mappa) da memorizzare nel sistema di persistenza
Es: RegistroMagazzino, Lista della spesa, Consumi pro capite di bevante e generi extra -> sono istanze e non classi.
 */
public class TabellaProdottoQuantita {
    private Map<Prodotto, Double> tabella;

    public TabellaProdottoQuantita(Map<Prodotto, Double> tabella) {
        this.tabella = tabella;
    }

    public void inserisciProdotto(Prodotto daAggiungere, Double quantita){
        boolean esistente = false;
        for (Prodotto p : tabella.keySet()) {
            if(p.getNome().equals(daAggiungere.getNome()) && p.getTipo().equals(daAggiungere.getTipo())) {
                tabella.put(p, tabella.get(p) + quantita);
                esistente = true;
                break;
            }
        }
        if(!esistente){
            tabella.put(daAggiungere, quantita);
        }
    }

    public void inserisciProdotti(TabellaProdottoQuantita prodottiDaAggiungere) {
        for (Prodotto daAggiungere: prodottiDaAggiungere.getTabella().keySet()){
            this.inserisciProdotto(daAggiungere, prodottiDaAggiungere.getTabella().get(daAggiungere));
        }
    }

    public void sostituisciProdotto(Prodotto prodotto, Double quantita){
        tabella.put(prodotto, quantita);
    }

    public void rimuoviProdotto(Prodotto daRimuovere, Double quantita){
        for(Prodotto p : tabella.keySet()) {
            if(p.getNome().equals(daRimuovere.getNome()) && p.getTipo().equals(daRimuovere.getTipo())) {
                if(quantita >= tabella.get(p)){
                    tabella.remove(p);
                }
                tabella.put(p, tabella.get(p) - quantita);
                break;
            }
        }
    }

    public void rimuoviProdotti(Map<Prodotto, Double> prodottiDaRimuovere){
        for(Prodotto daRimuovere : prodottiDaRimuovere.keySet()){
            this.rimuoviProdotto(daRimuovere, prodottiDaRimuovere.get(daRimuovere));
        }
    }

    public Map<Prodotto, Double> getTabella() {
        return tabella;
    }
    public Double get(Prodotto key){
        return this.tabella.get(key);
    }

    public static TabellaProdottoQuantita differenzaTraTabelle(TabellaProdottoQuantita t1, TabellaProdottoQuantita t2){
        TabellaProdottoQuantita tabellaDifferenza = new TabellaProdottoQuantita(new HashMap<>());

        Map<Prodotto, Double> tabella1 = t1.getTabella();
        Map<Prodotto, Double> tabella2 = t2.getTabella();

        for (Prodotto prodotto: tabella1.keySet()) {
            double valoreDaSottrarre = tabella2.get(prodotto) != null ? tabella2.get(prodotto) : 0;
            double differenza = tabella1.get(prodotto) - valoreDaSottrarre;
            if(differenza > 0) {
                tabellaDifferenza.getTabella().put(prodotto, differenza);
            } else {
                tabellaDifferenza.getTabella().put(prodotto, 0.0);
            }
        }

        return tabellaDifferenza;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TabellaProdottoQuantita altraTabella = (TabellaProdottoQuantita) o;
        return this.isUguale(altraTabella.getTabella());
    }

    private boolean isUguale(Map<Prodotto, Double> altraTabella) {
        if (tabella == null || altraTabella == null) return false;
        if (tabella.size() != altraTabella.size()) return false;

        for (Map.Entry<Prodotto, Double> entry : tabella.entrySet()) {
            Prodotto thisProdotto = entry.getKey();
            Double thisQuantita = entry.getValue();

            if(altraTabella.get(thisProdotto) == null) return false;
            if (Double.compare(thisQuantita, altraTabella.get(thisProdotto)) != 0)  return false;
        }

        return true;
    }
}
