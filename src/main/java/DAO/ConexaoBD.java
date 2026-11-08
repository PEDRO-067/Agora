package DAO;


import Service.Configuracao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gyudi
 */
public class ConexaoBD {
    
    private ConexaoBD() {
    }
    
    public static Connection getConexao() throws SQLException {
        String url = Configuracao.get("db.url", "jdbc:postgresql://localhost:5432/agora");
        String usuario = Configuracao.get("db.user", "postgres");
        String senha = Configuracao.get("db.password", "postgres");
        java.util.Properties propriedades = new java.util.Properties();
        propriedades.setProperty("user", usuario);
        propriedades.setProperty("password", senha);
        propriedades.setProperty("connectTimeout", "10");
        propriedades.setProperty("socketTimeout", "30");
        return DriverManager.getConnection(url, propriedades);
    }
    
}
