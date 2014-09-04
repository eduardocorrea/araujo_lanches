package pdv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import objetos.Filial;
import objetos.Funcionario;
import objetos.ItemVenda;
import objetos.Produto;
import objetos.Venda;
import controle.EstoqueControle;
import controle.FilialControle;
import controle.FuncionarioControle;
import controle.VendaControle;

public class Principal {

	private JFrame frmSistemaPdv;
	private JTextField tFNome;
	private JTextField tFValorUnitario;
	private JTextField tFQuantidade;
	private JLabel lblFuncionario;
	private Produto p;
	private VendaControle vendaControle;
	private FuncionarioControle funcionarioControle;
	private EstoqueControle estoqueControle;
	private Filial fil;
	private FilialControle filialControle;
	private Venda venda;
	private ItemVenda itemVenda;
	private DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
	private String nomeFuncionario;
	private String localFilial;
	
	private JTextField tFValorTotal;
	private JLabel lblNome;
	/**
	 * Launch the application.
	 */
	public static void main(String usuario, String filial) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Principal window = new Principal(usuario, filial);	
					window.frmSistemaPdv.setVisible(true);
					window.lblFuncionario = new JLabel("Funcionario" + usuario);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Principal(String login, String filial) throws ClassNotFoundException, SQLException {
		carregarValoresAutenticacao(login, filial);
		initialize();
	}
	
	public void limpar(){
		tFNome.setText("");
		tFQuantidade.setText("");
		tFValorUnitario.setText("");
		tFNome.grabFocus();
	}
	
	
	public void excluirItemVenda(String elemento){
		
		for(int x = 0; x <= venda.getItemDaVenda().size(); x++){
			if(venda.getItemDaVenda().get(x).getProduto().getDescricao().equals(elemento)){
				venda.getItemDaVenda().remove(x);
				break;
			}
		}
		
		String valor = gerarValorTotal();
		
		tFValorTotal.setText("R$ " + valor);
		
	}
	
	public String gerarValorTotal(){
		ArrayList<Produto> result = new  ArrayList<Produto>();
		DecimalFormat formatado = new DecimalFormat("0.00");
		float vt = 0;
		String retorno;
		
		for(int i = 0; i<=venda.getItemDaVenda().size() - 1; i++){
			result.add(venda.getItemDaVenda().get(i).getProduto());
		}
		
		for(Produto prod : result){
			vt += prod.getValorUnitario();
		}
		
		
		
		retorno = formatado.format(vt) + "";
		
		return retorno;
	}
	
	public void carregarValoresAutenticacao(String usuario, String filial) throws ClassNotFoundException, SQLException{
		func = new Funcionario();
		fil = new Filial();		
		funcionarioControle = new FuncionarioControle();
		filialControle = new FilialControle();
		
		func = funcionarioControle.funcionarioPeloLogin(usuario);
		fil = filialControle.filialPeloLocal(filial);
		nomeFuncionario = func.getNome();
		localFilial = fil.getLocal();
	}
	
	public void novaVenda(){
		venda = new Venda();
		
		tFValorTotal.setText("");
		tFQuantidade.setText("");
		tFValorUnitario.setText("");
		tFNome.setText("");
		defaultListModel.removeAllElements();
		
		tFNome.grabFocus();
	}
	
	public void imprimirCupom(Venda venda){
		//ESCREVER TXT      
		  try{  
		      File arquivo = new File("C:\\Users\\eduardo\\Desktop\\araujo_lanches\\saida.txt");  
		      if(arquivo.exists()){  
		          //se existir  
		          FileWriter arquivoTxt = new FileWriter(arquivo, true);  
		          PrintWriter linhasTxt = new PrintWriter(arquivoTxt);  
		          //ACREDITO QUE SO PODE TER 42 CARACTERES  
		          linhasTxt.println("===========================================");  
		          linhasTxt.println("              Araujo Lanches               ");  
		          linhasTxt.println("===========================================");  
		          linhasTxt.println("********** NAO E DOCUMENTO FISCAL *********");  
		          linhasTxt.println("===========================================");  
		          linhasTxt.println("PRODUTO      QTDE      VALOR UN.      VALOR");  
		          //dados da tabela  
		          for(int x = 0; x < venda.getItemDaVenda().size(); x++){ 
		        	  linhasTxt.print(String.format("%-10.10s",venda.getItemDaVenda().get(x).getProduto().getDescricao())); 
		              linhasTxt.print(String.format("%7s     ",1)); 
		              linhasTxt.print(String.format("%10s    ",venda.getItemDaVenda().get(x).getProduto().getValorUnitario())); 
		              linhasTxt.print(String.format("%7s    ",venda.getItemDaVenda().get(x).getValor()));
		              linhasTxt.println(); 
		          }  

		          linhasTxt.println();  
		          linhasTxt.println("===========================================");    
		          linhasTxt.println("Total                                " + venda.getValor().toString());  
		          linhasTxt.println("===========================================");  
		          linhasTxt.println("      AGRADECEMOS SUA PREFERÊNCIA          ");
		          linhasTxt.println("               VOLTE SEMPRE                ");
		  
		          int i = 0;  
		          while(i < 10){  
		              i++;  
		              linhasTxt.println();  
		          }                  
		          arquivoTxt.close();  
		          //emiteComanda();  
		            
		      }else{  
		          //se nao existir  
		          arquivo.createNewFile();  
		          //criaTxt();  
		      }  
		}catch(IOException error){  
		    System.out.println("nao encontrei arquivo");  
		}  
		    
		    
		  // imprime arquivo   
		  try {  
		      java.io.InputStream is = new FileInputStream("saida.txt");  
		      Scanner sc = new Scanner(is);  
		      FileOutputStream fs = new FileOutputStream("LPT1:");  
		      PrintStream ps = new PrintStream(fs);  
		  
		      while(sc.hasNextLine()){  
		          String linhas = sc.nextLine();  
		          ps.println(linhas);  
		      }  
		      fs.close();  
		  } catch (IOException ex) {  
		      JOptionPane.showMessageDialog(null, "Erro encontrado ao imprimir comanda.");  
		  }    
	}
	
	public void logout(){
		setFunc(null);
		setFil(null);
		String[] args = null;
		Autenticacao.main(args);
		System.exit(0);
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	private void initialize() throws ClassNotFoundException, SQLException {
		vendaControle = new VendaControle();
		funcionarioControle =  new FuncionarioControle();
		filialControle = new FilialControle();
		estoqueControle = new EstoqueControle();
		
		venda = new Venda();
		func = new Funcionario();
		fil = new Filial();
		JList<Object> list = new JList<Object>(defaultListModel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 20));
		list.setBackground(new Color(255, 255, 204));
		list.setVisibleRowCount(100);
		
		frmSistemaPdv = new JFrame();
		frmSistemaPdv.getContentPane().setBackground(new Color(255, 165, 0));
		frmSistemaPdv.setTitle("Sistema PDV - Lanchonete Araujo Lanches");
		frmSistemaPdv.setBounds(100, 100, 821, 620);
		frmSistemaPdv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelHeader = new JPanel();
		panelHeader.setBorder(null);
		panelHeader.setBackground(new Color(255, 140, 0));
		panelHeader.setForeground(Color.ORANGE);
		
		
		
		/****************************************************************************************************************************/
		
		/*Campo de Nome do produto e evento KeyPressed*/
		
		tFNome = new JTextField();
		tFNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					DecimalFormat df = new DecimalFormat("0.00");
					String nomeProduto = tFNome.getText();
		            try {

		             p = vendaControle.carregarProduto(nomeProduto);
		             if(p.getValorUnitario() == 0.0){
		            	 JOptionPane.showMessageDialog(null, "Produto não encontrado!");
		            	 tFNome.setText("");
		            	 tFNome.grabFocus();
		             }else{
			             tFValorUnitario.setText("R$ " + df.format(p.getValorUnitario()));
			             tFQuantidade.setText(1 + "");
			             tFQuantidade.grabFocus();
		             }


		            } catch (SQLException ex) {
		                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
		            } catch (ClassNotFoundException ex) {
		                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
		            }
				}
			}
		});
		
		/****************************************************************************************************************************/
		
		tFNome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tFNome.setColumns(10);
		
		lblNome = new JLabel("Nome ou codigo do produto");
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		/****************************************************************************************************************************/
		
		/****************************************************************************************************************************/
		
		
		JLabel lblValorUnitario = new JLabel("Valor Unit\u00E1rio");
		lblValorUnitario.setForeground(Color.WHITE);
		lblValorUnitario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		tFValorUnitario = new JTextField();
		tFValorUnitario.setBackground(new Color(255, 255, 204));
		tFValorUnitario.setEditable(false);
		tFValorUnitario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tFValorUnitario.setColumns(10);
		
		/****************************************************************************************************************************/
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setForeground(Color.WHITE);
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));	
		
		tFQuantidade = new JTextField();
		
		tFQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					itemVenda = new ItemVenda();
					itemVenda.setProduto(p);
					itemVenda.setValor(p.getValorUnitario());
					itemVenda.setQuantidade(Integer.parseInt(tFQuantidade.getText()));
					
					int qtd = Integer.parseInt(tFQuantidade.getText());
					int qtdEstoque = 0;
					try {
						fil = filialControle.filialPeloLocal(localFilial);
						qtdEstoque = estoqueControle.verificarEstoque(fil, itemVenda);
						
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					
					if(qtdEstoque <= 0){
						JOptionPane.showMessageDialog(null, "Produto Esgotado!");
					}else{
						
						if(qtdEstoque == 1){
							JOptionPane.showMessageDialog(null, "Ultimo produto em estoque!");
						}
						
						for(int i = 0; i < qtd; i++){
							defaultListModel.addElement(itemVenda.getProduto().getDescricao());
							venda.getItemDaVenda().add(itemVenda);
						}
						
						String valor = gerarValorTotal();
						
						tFValorTotal.setText("R$ " + valor);
					}
					limpar();
					
				}
			}
		});
		

		
		
		
		//***************************************************************************************
		
		tFQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tFQuantidade.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 204));
		
		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setForeground(Color.WHITE);
		lblValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		tFValorTotal = new JTextField();
		tFValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tFValorTotal.setEditable(false);
		tFValorTotal.setColumns(10);
		tFValorTotal.setBackground(new Color(255, 255, 204));
		
		//************************************************************************
		
		JButton btnFinalizarVenda = new JButton("Finalizar Venda");
		btnFinalizarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				//Salvar no banco
				try {
					func = funcionarioControle.funcionarioPeloNome(nomeFuncionario);
					fil = filialControle.filialPeloLocal(localFilial);
					venda.setValor(tFValorTotal.getText().substring(3, tFValorTotal.getText().length()));
					venda.setIdFilial(fil.getId());
					estoqueControle.atualizarEstoque(fil, venda);
					vendaControle.salvarVenda(venda, func);
					
					//imprimir
					
					imprimirCupom(venda);
					
					novaVenda();
					
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				
				
				
			}
		});
		btnFinalizarVenda.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		//************************************************************************
		
		JButton btnExcluirItemDe = new JButton("Excluir Item de Venda");
		btnExcluirItemDe.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int index = list.getSelectedIndex();				
				excluirItemVenda((list.getModel().getElementAt(index)).toString());
				defaultListModel.remove(index);
			}
		});
		btnExcluirItemDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(frmSistemaPdv.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblValorTotal, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExcluirItemDe, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblValorUnitario)
										.addComponent(tFValorUnitario, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(13)
											.addComponent(lblQuantidade, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tFQuantidade, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))))
								.addComponent(btnFinalizarVenda, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
								.addComponent(tFValorTotal, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
						.addComponent(tFNome, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
					.addContainerGap())
				.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tFNome, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblValorUnitario, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblQuantidade, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(tFValorUnitario, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(tFQuantidade, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblValorTotal, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tFValorTotal, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnExcluirItemDe, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnFinalizarVenda)))
					.addContainerGap())
		);
		lblFuncionario = new JLabel("Funcion\u00E1rio: " + nomeFuncionario);
		lblFuncionario.setForeground(Color.WHITE);
		lblFuncionario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Principal.class.getResource("/pdv/logo_sistema.png")));
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logout();
			}
		});
		GroupLayout gl_panelHeader = new GroupLayout(panelHeader);
		gl_panelHeader.setHorizontalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFuncionario, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelHeader.createSequentialGroup()
							.addComponent(btnSair)
							.addGap(75)
							.addComponent(label)))
					.addContainerGap(174, Short.MAX_VALUE))
		);
		gl_panelHeader.setVerticalGroup(
			gl_panelHeader.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 147, Short.MAX_VALUE)
						.addComponent(btnSair))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFuncionario)
					.addGap(6))
		);
		panelHeader.setLayout(gl_panelHeader);
		

		
		panel.add(list);
		frmSistemaPdv.getContentPane().setLayout(groupLayout);
	}
	
	
	
	
	private Funcionario func;
	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	public Filial getFil() {
		return fil;
	}

	public void setFil(Filial fil) {
		this.fil = fil;
	}
}


