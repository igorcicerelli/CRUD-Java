import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Manutencao extends JFrame {
	
    private static final long serialVersionUID = 1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manutencao frame = new Manutencao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
private class TratadorEventosConexao implements ConexaoListener {
    @Override
    public void mensagem(String mensagem) {
        txtStatus.setText(mensagem);
    }

    @Override
    public void erro(String mensagem, SQLException erro) {
        txtStatus.setText(mensagem + " " + erro.getMessage());
    }
}
	private JPanel contentPane;
	private JLabel codDoLeitor;
	private JLabel NomeDoLeitor;
	private JLabel TipoDoLeitor;
	JTextField codLeitor;
	JTextField nomeLeitor;
	JComboBox<?> tipoLeitor;
	private JTable tbDados;;
	private JTable table;
	private JTextField txtStatus;
    private Conexao conexao;
  
	public Manutencao() {
		setResizable(false);
		setType(Type.POPUP);
		setTitle("Manutenção do Leitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 483);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel TituloManutecaoDoLeitor = new JLabel("Manuten\u00E7\u00E3o do Leitor");
		TituloManutecaoDoLeitor.setBounds(20, 11, 306, 60);
		TituloManutecaoDoLeitor.setForeground(SystemColor.window);
		TituloManutecaoDoLeitor.setFont(new Font("Tw Cen MT", Font.BOLD, 34));
		contentPane.add(TituloManutecaoDoLeitor);

		codDoLeitor = new JLabel("C\u00F3digo do Leitor:");
		codDoLeitor.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		codDoLeitor.setBounds(10, 78, 113, 14);
		codDoLeitor.setForeground(SystemColor.textHighlightText);
		contentPane.add(codDoLeitor);

		NomeDoLeitor = new JLabel("Nome do Leitor:");
		NomeDoLeitor.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		NomeDoLeitor.setBounds(10, 103, 113, 14);
		NomeDoLeitor.setForeground(SystemColor.textHighlightText);
		contentPane.add(NomeDoLeitor);

		TipoDoLeitor = new JLabel("Tipo de Leitor:");
		TipoDoLeitor.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		TipoDoLeitor.setBounds(10, 130, 113, 14);
		TipoDoLeitor.setForeground(SystemColor.textHighlightText);
		contentPane.add(TipoDoLeitor);
		
			
			JLabel Imagem = new JLabel("");
			Imagem.setIcon(new ImageIcon("C:\\Users\\igor_\\OneDrive\\\u00C1rea de Trabalho\\teste2.png"));
			Imagem.setBounds(448, -3, 66, 71);
			contentPane.add(Imagem);

		codLeitor = new JTextField();
		codLeitor.setBounds(121, 76, 396, 20);
		contentPane.add(codLeitor);
		codLeitor.setColumns(10);
		codLeitor.setDocument(new Limitador(4, "[aA-zZ]"));   

		nomeLeitor = new JTextField();
		nomeLeitor.setBounds(121, 101, 396, 20);
		contentPane.add(nomeLeitor);
		nomeLeitor.setColumns(10);
		nomeLeitor.setDocument(new Limitador(40, "[0-9]"));

		txtStatus = new JTextField();
		
		txtStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
			}
		});
		txtStatus.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		txtStatus.setForeground(new Color(0, 0, 128));
		txtStatus.setText("Status...");
		txtStatus.setEditable(false);
		txtStatus.setBackground(new Color(255, 255, 255));
		txtStatus.setBounds(10, 407, 507, 23);
		contentPane.add(txtStatus);
		txtStatus.setColumns(10);
		
		JComboBox<Object> tipoLeitor = new JComboBox<Object>();
		tipoLeitor.setForeground(new Color(0, 0, 139));
		tipoLeitor.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 15));
		tipoLeitor.setBounds(121, 128, 396, 20);
		tipoLeitor.setModel(new DefaultComboBoxModel<Object>(new String[] {"Escolha", "Professor", "Aluno"}));
		contentPane.add(tipoLeitor);
		
		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		btnConsulta.setForeground(SystemColor.textText);
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				Conexao con = new Conexao();
				conexao = new Conexao();
			    conexao.addListener(new TratadorEventosConexao());
			    
				if (con.abreConexao()) {
					conexao.abreConexao();
					try {
						
						if ((codLeitor.getText() != null) && (nomeLeitor.getText().equals(""))) {
							String sql = "select * from leitor where codLeitor=?";
							PreparedStatement stmt = con.connection.prepareStatement(sql);
							stmt.setString(1, codLeitor.getText());
							
							ResultSet rs =  stmt.executeQuery();
							DefaultTableModel modelo = (DefaultTableModel)tbDados.getModel();
							modelo.setNumRows(0);
							
							if (rs.next()) {
							    do {
									codLeitor.setText(rs.getString("codLeitor"));
									nomeLeitor.setText(rs.getString("nomeLeitor"));
									tipoLeitor.setSelectedItem(rs.getString("tipoLeitor"));
									modelo.addRow(new Object[]{rs.getString("codLeitor"), rs.getString("nomeLeitor"), rs.getString("tipoLeitor")});
						        	JOptionPane.showMessageDialog(null, "Consulta pelo Código realizada com Sucesso!");
							    } while (rs.next());

							}else {
					        	JOptionPane.showMessageDialog(null, "ERROR! Digite um ID ou Nome Válido!");
							}
									
							rs.close();
							stmt.close();
						} else if ((nomeLeitor.getText() != null) && (codLeitor.getText().equals(""))) {
							String sql = "select * from leitor where nomeLeitor=?";
							PreparedStatement stmt = con.connection.prepareStatement(sql);
							
							stmt.setString(1, nomeLeitor.getText());
							
							ResultSet rs = stmt.executeQuery();
							DefaultTableModel modelo = (DefaultTableModel)tbDados.getModel();
							modelo.setNumRows(0);
							
							if (rs.next()) {
							    do {
								codLeitor.setText(rs.getString("codLeitor"));
								tipoLeitor.setSelectedItem(rs.getString("tipoLeitor"));
								modelo.addRow(new Object[]{rs.getString("codLeitor"), rs.getString("nomeLeitor"), rs.getString("tipoLeitor")});

								JOptionPane.showMessageDialog(null, "Consulta por nome realizada com sucesso.");
							    } while (rs.next());
								}else
									JOptionPane.showMessageDialog(null,"ERROR! Digite um ID ou Nome Válido!");{
								}
							
							rs.close();
							stmt.close();
						} else if ((codLeitor.getText().equals("")) && (nomeLeitor.getText().equals(""))) {
							JOptionPane.showMessageDialog(null, "Năo foi possível realizar a consulta, Informe o código ou o nome.");
						} else {
							JOptionPane.showMessageDialog(null, "Năo foi possível realizar a consulta, Informe apenas um dos campos.");
						}
						
						conexao.fechaConexao();
					} catch (SQLException e) {
						txtStatus.setText(e.toString());
					}
					
				} else {
					txtStatus.setText("Erro ao conectar");
				}
				
			}
		
		});
		btnConsulta.setBackground(UIManager.getColor("Button.background"));
		btnConsulta.setBounds(114, 166, 94, 23);
		contentPane.add(btnConsulta);
		
		tipoLeitor.setSelectedIndex(0);
		codLeitor.setText("");
		nomeLeitor.setText("");
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		btnAlterar.setBounds(218, 166, 94, 23);
		btnAlterar.setForeground(SystemColor.textText);
		btnAlterar.setBackground(UIManager.getColor("Button.background"));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Acoes ac = new Acoes();
				conexao = new Conexao();
			    conexao.addListener(new TratadorEventosConexao());
			    
				if(codLeitor.getText().equals("")|| nomeLeitor.getText().equals("")|| tipoLeitor.getSelectedItem() .toString() .equals("Escolha")) { 
					JOptionPane.showMessageDialog(null, "Preencha todos os campos para Alterar um Registro.");
				}else {
				           
				String LeitorTipo = (String) tipoLeitor.getSelectedItem();
					
	            ac.setCodLeitor(Integer.parseInt(codLeitor.getText()));
				ac.setNomeLeitor(nomeLeitor.getText());
				ac.setTipoLeitor(LeitorTipo);
				conexao.abreConexao();
				ac.Alterar();
				conexao.fechaConexao();
				
				}
				
			  }
			});
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		btnExcluir.setBounds(423, 166, 94, 23);
		btnExcluir.setForeground(SystemColor.textText);
		btnExcluir.setBackground(UIManager.getColor("Button.background"));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
	            Acoes ac = new Acoes();
	            
				conexao = new Conexao();
			    conexao.addListener(new TratadorEventosConexao());

				if(codLeitor.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "\tERROR! Digite o Código do Leitor para Excluir um Registro. \nObs: Somente Números!"); 
				}else {
			        
	            ac.setCodLeitor(Integer.parseInt(codLeitor.getText()));
	            conexao.abreConexao();
	            ac.Excluir();
	            conexao.fechaConexao();
	            
	            
				}
			}
						
			});
		contentPane.add(btnExcluir);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		btnGravar.setBounds(319, 166, 94, 23);
		btnGravar.setForeground(SystemColor.textText);
		btnGravar.setBackground(UIManager.getColor("Button.background"));
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Acoes ac = new Acoes();
				conexao = new Conexao();
			    conexao.addListener(new TratadorEventosConexao());
			    
				if(codLeitor.getText().equals("")|| nomeLeitor.getText().equals("")|| tipoLeitor.getSelectedItem() .toString() .equals("Escolha")) { 
					JOptionPane.showMessageDialog(null, "Preencha todos os campos para Gravar um novo Registro.");
				}else {
						
			        
				String LeitorTipo = (String) tipoLeitor.getSelectedItem();			    
	            ac.setCodLeitor(Integer.parseInt(codLeitor.getText()));
				ac.setNomeLeitor(nomeLeitor.getText());
				ac.setTipoLeitor(LeitorTipo);
				conexao.abreConexao();
				ac.Gravar();
				conexao.fechaConexao();
				
				}	
			}
			
				
		});
		contentPane.add(btnGravar);
		
		JButton btnlistaDados = new JButton("Lista Todos Dados");
		btnlistaDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				Conexao con = new Conexao();
				conexao = new Conexao();
			    conexao.addListener(new TratadorEventosConexao());
			    
				if (con.abreConexao() ) { //Conexão com Banco de Dados
					conexao.abreConexao();
					try {
						
						String sql = "select *from leitor";
						PreparedStatement stmt = con.connection.prepareStatement(sql);
						
										
						ResultSet rs = stmt.executeQuery();
						
						DefaultTableModel modelo = (DefaultTableModel)tbDados.getModel();
						modelo.setNumRows(0);
						
						
						while(rs.next()) {
							
							modelo.addRow(new Object[]{rs.getString("codLeitor"), rs.getString("nomeLeitor"), rs.getString("tipoLeitor")});
						}
						
						rs.close();
						stmt.close();
						conexao.fechaConexao();
					} catch (SQLException e) {
						txtStatus.setText("Erro:" + e.toString());
					}
					
				} else {
					txtStatus.setText("Erro ao conectar.");

				}
				
			}
		});
		btnlistaDados.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 16));
		btnlistaDados.setBounds(10, 216, 507, 23);
		contentPane.add(btnlistaDados);
		
		JButton btnNewButton = new JButton("Novo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoLeitor.setSelectedIndex(0);
				codLeitor.setText("");
				nomeLeitor.setText("");
				DefaultTableModel modelo = (DefaultTableModel) tbDados.getModel();
				modelo.getDataVector().removeAllElements();
				modelo.setNumRows(8);
				txtStatus.setText("Todos os Dados Foram Limpado com Sucesso!");
				JOptionPane.showMessageDialog(null, "Dados Limpado com Sucesso!");
				

				
			}
		});
		btnNewButton.setBounds(10, 167, 94, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 242, 507, 126);
		contentPane.add(scrollPane);
		
		tbDados = new JTable();
		tbDados.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int linha = tbDados.getSelectedRow();

				codLeitor.setText(tbDados.getValueAt(linha, 0).toString());
				nomeLeitor.setText(tbDados.getValueAt(linha, 1).toString());
				tipoLeitor.setSelectedItem(tbDados.getValueAt(linha, 2).toString());
			}
		});
		tbDados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int linha = tbDados.getSelectedRow();
				codLeitor.setText(tbDados.getValueAt(linha, 0).toString());
				nomeLeitor.setText(tbDados.getValueAt(linha, 1).toString());
				tipoLeitor.setSelectedItem(tbDados.getValueAt(linha, 2).toString());
				
			}
		});
		tbDados.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"C\u00F3digo do Leitor", "Nome do Leitor", "Tipo Leitor"
			}
		));
		tbDados.getColumnModel().getColumn(0).setPreferredWidth(92);
		tbDados.getColumnModel().getColumn(1).setPreferredWidth(86);
		tbDados.getColumnModel().getColumn(2).setPreferredWidth(77);
		scrollPane.setViewportView(tbDados);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"C\u00F3digo do Leitor", "Nome do Leitor", "Tipo Leitor"
			}
		));
	}
}