package main_package.model.user;

public class Magazziniere extends Utente{

    public Magazziniere(int id, String username, String password) {
        super(id, username, password);
        this.setUsertype("magazziniere");
    }
}
