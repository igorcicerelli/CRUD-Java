import java.sql.SQLException;

public interface ConexaoListener {

    public void mensagem(String mensagem);

    public void erro(String mensagem, SQLException erro);
}