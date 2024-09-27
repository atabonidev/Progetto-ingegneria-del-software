package main_package.model.user;

public abstract class Utente {
    private int id;
    private String username;
    private String password;
    private String usertype;

    public Utente(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

}
