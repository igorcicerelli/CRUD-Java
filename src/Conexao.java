import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Conexao {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBNAME = "biblioteca?useTimezone=true&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    private static final String LOGIN = "root";
    private static final String SENHA = "";

    public Connection connection;
    
    private List<ConexaoListener> listeners = new LinkedList<>();
    
    public boolean abreConexao() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, LOGIN, SENHA);
            mensagem("Banco Conectado com Sucesso!"); // notifica os listeners
            return true;
        } catch (ClassNotFoundException e) {
            mensagem("Driver nao encontrado: " + e.getMessage()); // notifica os listeners
            return false;
        } catch (SQLException e) {
            erro("Erro ao conectar.", e); // notifica os listeners
            return false;
        }
    }
    
    public void addListener(ConexaoListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void fechaConexao() {
        try {
            connection.close();
            mensagem("Classe Conexão - Desconectou!"); // notifica os listeners
        } catch (SQLException e) {
            mensagem("Erro ao fechar.");
            
        }
    }
    
    private void erro(String mensagem, SQLException erro) {
        System.out.println(mensagem);
        erro.printStackTrace();
        for (ConexaoListener listener : listeners) {
            listener.erro(mensagem, erro);
        }
    }

    private void mensagem(String mensagem) {
        System.out.println(mensagem);
        for (ConexaoListener listener : listeners) {
            listener.mensagem(mensagem);
        }
    }
}