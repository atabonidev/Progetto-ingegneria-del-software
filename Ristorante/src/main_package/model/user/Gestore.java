package main_package.model.user;

public class Gestore extends Utente {

    public Gestore(int id, String username, String password) {
        super(id, username, password);
        this.setUsertype("gestore");
    }
}
