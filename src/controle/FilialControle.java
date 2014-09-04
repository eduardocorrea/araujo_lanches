/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objetos.Filial;
import banco.Conexao;

public class FilialControle {
    private Conexao conexao;
    
    public String[] carregarFilial() throws SQLException, ClassNotFoundException{
    	
    	conexao = new Conexao();
    	ArrayList<Filial> resultado = new ArrayList<Filial>();
    	int contador = 0;
        
        Statement stmt = conexao.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM filial");
        
        while(rs.next()){ 
        	
        	Filial f = new Filial();
        	f.setLocal(rs.getString("local"));
        	resultado.add(f);
        
        }
        
    	String[] filiais = new String[resultado.size()];
    	
        
        for(Filial  f : resultado){           	
        	filiais[contador] = f.getLocal();
        	contador++;
        }
        
        return filiais;
    }
    
public Filial filialPeloLocal(String local) throws SQLException, ClassNotFoundException{
    	
    	conexao = new Conexao();
        
        Statement stmt = conexao.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM filial");
        Filial fil = new Filial();
        
        while(rs.next()){
            if(rs.getString("local").equals(local)){
            	fil.setId(rs.getInt("idFilial"));
            	fil.setLocal(rs.getString("local"));
            	fil.setEndereco(rs.getString("endereco"));
            	fil.setTelefone(rs.getString("telefone"));
            	fil.setFax(rs.getString("fax"));
            }
        }

        
        return fil;
        	
    }
    
}
