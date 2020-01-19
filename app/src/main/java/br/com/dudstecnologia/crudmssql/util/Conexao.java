package br.com.dudstecnologia.crudmssql.util;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    String ip = "172.17.0.1";
    String class_driver = "net.sourceforge.jtds.jdbc.Driver";
    String banco = "empresa";

    String usuario = "sa";
    String senha = "1234Abcd";

    public Connection Conn() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        String URL_CONEXAO;

        try {

            Class.forName(class_driver);
            URL_CONEXAO = "jdbc:jtds:sqlserver://" + ip + ";"
                        + "databaseName=" + banco + ";"
                        + "user=" + usuario + ";"
                        + "password=" + senha + ";";

            conn = DriverManager.getConnection(URL_CONEXAO);
        } catch (SQLException erro) {
            Log.d("CONEXAO_MSSQL", erro.getMessage());
        } catch (ClassNotFoundException erro) {
            Log.d("CONEXAO_MSSQL", erro.getMessage());
        } catch (Exception erro) {
            Log.d("CONEXAO_MSSQL", erro.getMessage());
        }

        return conn;
    }
}
