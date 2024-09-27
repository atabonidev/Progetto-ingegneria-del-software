package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Piatto;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.time.LocalDate;

public class InizializzaPiatto implements Handler {
    private GestoreRepository gestoreRepository;

    public InizializzaPiatto(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }
    @Override
    public Session execute(Session session, View view) {
        this.initPiatto(session, view);
        return session;
    }

    private Session initPiatto(Session session, View view){

        if(isCaricoLavoroPerPersonaInizializzato()) {
            String nomePiatto = view.leggiStringaNonVuota("Inserisci il nome del piatto >>  ");
            if(!isPiattoGiaPresente(nomePiatto)){
                String nomeRicettaCorrispondente = view.leggiStringaNonVuota("Inserisci il nome della ricetta >>  ");
                if (isRicettaEsistente(nomeRicettaCorrispondente)) {
                    Piatto nuovoPiatto = istanziazionePiatto(view, nomePiatto, nomeRicettaCorrispondente);
                    gestoreRepository.getPiattiRepository().insertPiatto(nuovoPiatto);
                } else {
                    view.print("La ricetta non è presente -> non è possibile aggiungere il piatto");
                }
            } else {
                view.print("Il piatto " + nomePiatto + " è già presente!");
            }
        }else {
            view.print("Impossibile creare il piatto, è necessario inizializzare il CARICO DI LAVORO PER PERSONA prima!!");
        }

        return session;
    }

    private boolean isPiattoGiaPresente(String nomePiatto) {
        return gestoreRepository.getPiattiRepository().getPiattoFromName(nomePiatto) != null;
    }

    private Piatto istanziazionePiatto(View view, String nomePiatto, String nomeRicettaCorrispondente) {
        double caricoLavoroPiatto = gestoreRepository.getRicetteRepository().getCaricoDiLavoroDellaRicetta(nomeRicettaCorrispondente);
        LocalDate inizioValidita = view.leggiData("____Data di INIZIO validità____");
        LocalDate fineValidita = view.leggiDataConMinimo("____Data di FINE validità____", inizioValidita);

        return new Piatto(nomePiatto, nomeRicettaCorrispondente, caricoLavoroPiatto, inizioValidita, fineValidita);
    }

    private boolean isCaricoLavoroPerPersonaInizializzato(){
        int caricoLavoroPerPersona = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
        return caricoLavoroPerPersona != -1;
    }

    //Pattern di refactoring : estratta variabile come query
    private boolean isRicettaEsistente(String nomeRicettaCorrispondente){
        return gestoreRepository.getRicetteRepository().isRicettaPresente(nomeRicettaCorrispondente);
    }
}
