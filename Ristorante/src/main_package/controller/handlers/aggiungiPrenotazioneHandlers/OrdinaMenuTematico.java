package main_package.controller.handlers.aggiungiPrenotazioneHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Prenotazione;
import main_package.model.user.Session;
import main_package.view.View;

import java.util.List;

public class OrdinaMenuTematico implements Handler {
    private Prenotazione prenotazioneDaAggiungere;
    private List<String> listaNomiMenuTematici;

    public OrdinaMenuTematico(Prenotazione prenotazioneDaAggiungere, List<String> listaNomiMenuTematici){
        this.prenotazioneDaAggiungere = prenotazioneDaAggiungere;
        this.listaNomiMenuTematici = listaNomiMenuTematici;
    }
    //una persona può ordinare un solo menu tematico per se stesso
    @Override
    public Session execute(Session session, View view) {
        view.creaMenu("Scegli Menu tematico");
        view.setVociMenu(listaNomiMenuTematici);

        String nomeMenuTematicoSelezionato = listaNomiMenuTematici.get(view.scegliVoceMenu());
        this.prenotazioneDaAggiungere.aggiungiMenuTematicoOrdinato(nomeMenuTematicoSelezionato);

        return session;
    }
}
