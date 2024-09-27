package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Ricetta;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.util.HashMap;
import java.util.Map;

public class InizializzaRicetta implements Handler {
    private GestoreRepository gestoreRepository;

    public InizializzaRicetta(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        this.initRicetta(session, view);
        return session;
    }

    private Session initRicetta(Session session, View view){
        String nome;
        Map<String, Double> ingredienti = new HashMap<>();
        int porzioni;
        double caricoLavPerPorzione;
        int caricoLavoroPerPersona = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
        double frazionePerCaricoLavPorzione;

        if(isCaricoLavoroPerPersonaInizializzato(caricoLavoroPerPersona)){
            nome = view.leggiStringaNonVuota("Inserisci nome della ricetta >>  ");

            if(!isRicettaGiaPresente(nome)) {
                inserimentoIngredienti(view, ingredienti);
                porzioni = view.leggiInteroPositivo("Inserisci numero di porzioni >>  ");
                frazionePerCaricoLavPorzione = view.leggiDoubleConMinimoMassimoEsclusi("Inserisci carico lavoro porzione (% di carico lavoro persona) >>  ", 0, 1);
                caricoLavPerPorzione = caricoLavoroPerPersona * frazionePerCaricoLavPorzione;

                Ricetta ricetta = new Ricetta(nome, ingredienti, porzioni, caricoLavPerPorzione);
                gestoreRepository.getRicetteRepository().insertRicetta(ricetta);
            } else {
                view.print("Una ricetta con lo stesso nome e' gia' presente nel ricettario!");
            }
        } else {
            view.print("Impossibile creare ricetta, è necessario inizializzare il CARICO DI LAVORO PER PERSONA prima!!");
        }

        return session;
    }

    private boolean isRicettaGiaPresente(String nome) {
        return gestoreRepository.getRicetteRepository().isRicettaPresente(nome);
    }

    private boolean isCaricoLavoroPerPersonaInizializzato(int caricoLavoroPerPersona){
        return caricoLavoroPerPersona != -1;
    }

    private void inserimentoIngredienti(View view, Map<String, Double> ingredienti) {
        do{
            String nomeIngrediente = view.leggiStringaNonVuota("Inserisci nome ingrediente >>  ");

            if(!ingredienti.containsKey(nomeIngrediente)){
                double quantita = view.leggiDoubleConMinimoEscluso("Inserisci quantità in kg >>  ", 0);
                ingredienti.put(nomeIngrediente, quantita);
            } else {
                view.print("Ingrediente già presente nella ricetta");
            }
        }while(view.leggiYesOrNo("Aggiungere altro? >> "));
    }
}
