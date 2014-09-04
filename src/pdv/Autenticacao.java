package pdv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import controle.FilialControle;
import controle.FuncionarioControle;

public class Autenticacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4219126740119550418L;
	private JPanel contentPane;
	private JTextField tFLogin;
	private FuncionarioControle funcionarioControle;
	private FilialControle filialControle;
	private JTextField tFSenha;
	private DefaultComboBoxModel<List> boxModel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Autenticacao frame = new Autenticacao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Autenticacao() throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		funcionarioControle = new FuncionarioControle();
		
		JLabel lblAutenticao = new JLabel("Autentica\u00E7\u00E3o");
		lblAutenticao.setForeground(Color.WHITE);
		lblAutenticao.setFont(new Font("Tahoma", Font.PLAIN, 23));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Autenticacao.class.getResource("/pdv/logo_sistema.png")));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		
		tFLogin = new JTextField();
		tFLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		tFLogin.setColumns(10);
		
		JButton btnAutenticar = new JButton("Acessar");
		btnAutenticar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = tFLogin.getText();
				String senha = tFSenha.getText();
				
				try {
					
					if(funcionarioControle.carregarFuncionario(login, senha)){
						Principal.main(login, boxModel.getSelectedItem().toString());
						dispose();
					}else{
						System.out.println("Senha errada");
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnAutenticar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		
		filialControle = new FilialControle();
		String[] filiais;
		filiais = filialControle.carregarFilial();
		JComboBox cBFiliais = new JComboBox(filiais);
		boxModel = (DefaultComboBoxModel<List>) cBFiliais.getModel();			
				
		
		
		cBFiliais.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		tFSenha = new JPasswordField();
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(276, Short.MAX_VALUE)
					.addComponent(label)
					.addGap(265))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(439, Short.MAX_VALUE)
					.addComponent(lblAutenticao)
					.addGap(428))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(266)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(tFSenha, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
						.addComponent(cBFiliais, Alignment.LEADING, 0, 476, Short.MAX_VALUE)
						.addComponent(btnAutenticar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(428))
						.addComponent(tFLogin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblSenha, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
							.addGap(396)))
					.addGap(256))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(50)
					.addComponent(lblAutenticao)
					.addGap(11)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(lblLogin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tFLogin, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tFSenha, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(cBFiliais, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnAutenticar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(81, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
