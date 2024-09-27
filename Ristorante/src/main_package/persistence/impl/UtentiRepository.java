package main_package.persistence.impl;

import main_package.model.user.AddettoPrenotazioni;
import main_package.model.user.Gestore;
import main_package.model.user.Magazziniere;
import main_package.model.user.Utente;
import main_package.persistence.IUtentiRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtentiRepository implements IUtentiRepository {
    private Connection connection;

    public UtentiRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getId(String username) {
        String sql = "SELECT Id FROM Utente WHERE NomeUtente = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt("Id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public Utente checkLogin(String usr, String pwd, String tipoUtente) {
        String sql = "SELECT Id, NomeUtente, Password, TipoUtente FROM Utente"
                + " WHERE NomeUtente = ?"
                + " AND Password = ?"
                + " AND TipoUtente = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, usr);
            pstmt.setString(2, pwd);
            pstmt.setString(3, tipoUtente);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int id = rs.getInt("Id");
                String user = rs.getString("NomeUtente");
                String pass = rs.getString("Password");
                String userType = rs.getString("TipoUtente");

                if(userType.equals("gestore")){
                    return new Gestore(id, user, pass);
                } else if(userType.equals("addetto_prenotazioni")) {
                    return new AddettoPrenotazioni(id, user, pass);
                } else {
                    return new Magazziniere(id, user, pass);
                }
            }
            return null;  //l'utente non è registrato, executeQuery è vuota

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean checkUsername(String user) {
        String sql = "SELECT NomeUtente FROM Utente"
                + " WHERE NomeUtente = ?";
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            String username = rs.getString("NomeUtente");
            //se user esiste
            return (username != null);
        } catch (SQLException e) {
            return false;
        }
    }
}
