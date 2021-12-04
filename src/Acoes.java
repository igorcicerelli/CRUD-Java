import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Acoes {
	private int codLeitor;
	private String nomeLeitor;
	private String tipoLeitor;
	
	
	public int getCodLeitor(int cod) {
		return codLeitor;
	}


	public void setCodLeitor(int codLeitor) {
		this.codLeitor = codLeitor;
	}


	public String getNomeLeitor(String string) {
		return nomeLeitor;
	}


	public void setNomeLeitor(String nomeLeitor) {
		this.nomeLeitor = nomeLeitor;
	}


	public String getTipoLeitor(String leitorTipo) {
		return tipoLeitor;
	}


	public void setTipoLeitor(String tipoLeitor) {
		this.tipoLeitor = tipoLeitor;
	}
	

//FUNÇÕES BOTÕES
 public void Gravar() {		// BOTÃO GRAVAR ----------------------------------
		
	 Conexao con = new Conexao();
		if (con.abreConexao() ) { //Conexão com Banco de Dadoss		
			try {
				
				String sql = "INSERT INTO leitor (nomeLeitor, tipoLeitor, codLeitor) VALUES (?, ?, ?)";
				PreparedStatement stmt = con.connection.prepareStatement(sql);
				
				
				stmt.setString(1, nomeLeitor);
				stmt.setString(2, tipoLeitor);
				stmt.setInt(3, codLeitor);
				
				
				int LinhasInseridas = stmt.executeUpdate();			
				if (LinhasInseridas != 0) {
					JOptionPane.showMessageDialog(null,"Registro Inserido com Sucesso!");
				}	
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
			}

		} 	
 }
 
 
public void Excluir() {		//BOTÃO EXCLUIR ----------------------------------
	
	
	Conexao con = new Conexao();
	if (con.abreConexao() ) { //Conexão com Banco de Dados
		
		try {
			
			String sql = "DELETE FROM leitor WHERE codLeitor = ?";
			PreparedStatement stmt = con.connection.prepareStatement(sql);
			        
			// preparo a passagem de parametros 
			stmt.setInt(1, codLeitor);
							
			int LinhasAlteradas = stmt.executeUpdate();
			if (LinhasAlteradas != 0) {
				JOptionPane.showMessageDialog(null, "Registro Excluido com Sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Registro Não Encontrado.");
			}					
			
			stmt.close();			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
		}
	}
	
   }

public void Alterar() {		//BOTÃO ALTERAR ----------------------------------
	
	
	Conexao con = new Conexao();
	if (con.abreConexao() ) { //Conexão com Banco de Dados
		
		try {
			
			String sql = "UPDATE leitor SET nomeLeitor = ?, tipoLeitor = ? WHERE codLeitor = ?";
			PreparedStatement stmt = con.connection.prepareStatement(sql);
			       
			
			stmt.setString(1, nomeLeitor);
			stmt.setString(2, tipoLeitor);
			stmt.setDouble(3, codLeitor);
							
			int LinhasInseridas = stmt.executeUpdate();
			if (LinhasInseridas != 0) {
				JOptionPane.showMessageDialog(null, "Registro Alterado com Sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "Registro Não Encontrado.");
			}					
			
			stmt.close();			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());

   	  }
	}
  }
}


