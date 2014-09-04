/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import objetos.Funcionario;
import banco.Conexao;

public class FuncionarioControle {
    private Conexao conexao;
    
    public boolean carregarFuncionario(String user, String pass) throws SQLException, ClassNotFoundException{
    	
    	conexao = new Conexao();
        
        Statement stmt = conexao.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM funcionario");
        Funcionario f = new Funcionario();
        
        while(rs.next()){
            if(rs.getString("login").equals(user) && rs.getString("senha").equals(pass)){
            	f.setId(rs.getInt("idFuncionario"));
            	f.setNome(rs.getString("nome"));
            }
        }
        
        if(f.getNome() == null){
        	JOptionPane.showMessageDialog(null, "Senha Incorreta! Verifique seu Login e Senha!");
        	return false;
        }
        
        return true;
        	
    }
    
public Funcionario funcionarioPeloLogin(String user) throws SQLException, ClassNotFoundException{
    	
    	conexao = new Conexao();
        
        Statement stmt = conexao.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM funcionario");
        Funcionario f = new Funcionario();
        
        while(rs.next()){
            if(rs.getString("login").equals(user)){
            	f.setId(rs.getInt("idFuncionario"));
            	f.setNome(rs.getString("nome"));
            }
        }

        
        return f;
        	
    }

public Funcionario funcionarioPeloNome(String nome) throws SQLException, ClassNotFoundException{
	
	conexao = new Conexao();
    
    Statement stmt = conexao.getConnection().createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM funcionario");
    Funcionario f = new Funcionario();
    
    while(rs.next()){
        if(rs.getString("nome").equals(nome)){
        	f.setId(rs.getInt("idFuncionario"));
        	f.setNome(rs.getString("nome"));
        	f.setLogin(rs.getString("login"));
        	f.setSenha(rs.getString("senha"));
        }
    }

    
    return f;
    	
}
    
}
