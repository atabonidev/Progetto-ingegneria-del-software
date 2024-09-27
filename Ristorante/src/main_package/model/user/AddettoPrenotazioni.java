package main_package.model.user;

public class AddettoPrenotazioni extends Utente {

    public AddettoPrenotazioni(int id, String username, String password) {
        super(id, username, password);
        this.setUsertype("addetto_prenotazioni");
    }
}
