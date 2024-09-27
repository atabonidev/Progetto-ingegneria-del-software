package main_package.controller.handlers.inizializzaParametroHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.MenuTematico;
import main_package.model.user.Session;
import main_package.persistence.user.GestoreRepository;
import main_package.view.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InizializzaMenuTematico implements Handler {
    private GestoreRepository gestoreRepository;

    public InizializzaMenuTematico(GestoreRepository gestoreRepository){
        this.gestoreRepository = gestoreRepository;
    }

    @Override
    public Session execute(Session session, View view) {
        this.initMenuTematico(view);
        return session;
    }

    private void initMenuTematico(View view){
        List<String> listaPiatti = new ArrayList<>();

        int caricoLavoroPerPersona = gestoreRepository.getParametriRistoranteRepository().getCaricoLavoroPerPersona();
        if(isCaricoLavoroPerPersonaInizializzato(caricoLavoroPerPersona)) {
            String nomeMenuTematico = view.leggiStringaNonVuota("Inserisci il nome del menu tematico >>  ");

            LocalDate inizioValidita = view.leggiData("____Data di INIZIO validità____");
            LocalDate fineValidita = view.leggiDataConMinimo("____Data di FINE validità____", inizioValidita);

            List<String> piattiFraCuiScegliere = gestoreRepository.getPiattiRepository().getNomiPiattiPerPeriodoDiValidita(inizioValidita, fineValidita);

            if (!piattiFraCuiScegliere.isEmpty()) {
                gestioneSceltaPiatti(view, listaPiatti, piattiFraCuiScegliere);
                creazioneMenuTematico(view, listaPiatti, caricoLavoroPerPersona, nomeMenuTematico, inizioValidita, fineValidita);
            } else {
                view.print("Impossibile creare menu tematico, non ci sono piatti nel periodo scelto!!");
            }
        } else {
            view.print("Impossibile creare il menu tematico, è necessario inizializzare il CARICO DI LAVORO PER PERSONA prima!!");
        }
    }

    private void creazioneMenuTematico(View view, List<String> listaPiatti, int caricoLavoroPerPersona, String nomeMenuTematico, LocalDate inizioValidita, LocalDate fineValidita) {
        double caricoLavoroMenuTematico = gestoreRepository.getPiattiRepository().getCaricoDiLavoroInsiemePiatti(listaPiatti);
        if (caricoLavoroMenuTematico <= (4.0 / 3 * caricoLavoroPerPersona)) {
            MenuTematico nuovoMenuTematico = new MenuTematico(nomeMenuTematico, listaPiatti, inizioValidita, fineValidita, caricoLavoroMenuTematico);
            gestoreRepository.getMenuTematiciRepository().insertMenuTematico(nuovoMenuTematico);
        } else {
            view.print("Ecceduta soglia di 4/3 del caricoLavPerPersona");
        }
    }

    private void gestioneSceltaPiatti(View view, List<String> listaPiatti, List<String> piattiFraCuiScegliere) {
        do {
            int opzione = 1;
            for (String piatto : piattiFraCuiScegliere) {
                view.print(opzione + " - " + piatto);
                opzione++;
            }

            opzione = view.leggiInteroConMinimoMassimo("Digita il numero del piatto che vuoi aggiungere >>  ", 1, piattiFraCuiScegliere.size());

            String piattoDaAggiungere = piattiFraCuiScegliere.remove(opzione - 1);
            listaPiatti.add(piattoDaAggiungere);

            if(piattiFraCuiScegliere.isEmpty()){
                view.print("I piatti fra cui scegliere sono finiti!");
                break;
            }

        } while (view.leggiYesOrNo("Aggiungere altro? >> "));
    }

    private boolean isCaricoLavoroPerPersonaInizializzato(int caricoLavoroPerPersona){
        return caricoLavoroPerPersona != -1;
    }


}
