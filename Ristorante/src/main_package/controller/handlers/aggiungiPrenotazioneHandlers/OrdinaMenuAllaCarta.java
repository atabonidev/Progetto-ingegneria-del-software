package main_package.controller.handlers.aggiungiPrenotazioneHandlers;

import main_package.controller.handlers.Handler;
import main_package.model.Prenotazione;
import main_package.model.user.Session;
import main_package.view.View;

import java.util.List;

public class OrdinaMenuAllaCarta implements Handler {
    private Prenotazione prenotazioneDaAggiungere;
    private List<String> listaPiattiDelGiorno;

    public OrdinaMenuAllaCarta(Prenotazione prenotazioneDaAggiungere, List<String> listaPiattiDelGiorno){
        this.prenotazioneDaAggiungere = prenotazioneDaAggiungere;
        this.listaPiattiDelGiorno = listaPiattiDelGiorno;
    }
    //supponiamo che una stessa persona possa ordinare più volte lo stesso piatto per se stessa
    @Override
    public Session execute(Session session, View view) {
        do{
            view.creaMenu("Scegli Piatto");
            view.setVociMenu(listaPiattiDelGiorno);

            String piattoSelezionato = listaPiattiDelGiorno.get(view.scegliVoceMenu());
            this.prenotazioneDaAggiungere.aggiungiPiattoOrdinato(piattoSelezionato);
        }while (view.leggiYesOrNo("Aggiungere altri piatti? >>  "));

        return session;
    }
}
