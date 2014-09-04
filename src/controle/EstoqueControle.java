/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import objetos.Estoque;
import objetos.Filial;
import objetos.ItemVenda;
import objetos.Venda;
import banco.Conexao;

public class EstoqueControle {
    private Conexao conexao;
    
    public void atualizarEstoque(Filial filial, Venda venda) throws SQLException, ClassNotFoundException{
    	
    	conexao = new Conexao();
        
        Statement stmt = conexao.getConnection().createStatement();
        Statement stat = conexao.getConnection().createStatement();
       
        for(ItemVenda itemVenda : venda.getItemDaVenda()){
        	
	        ResultSet rs = stmt.executeQuery(""
	        		+ "SELECT * FROM estoque e"
	        		+ " where e.idFilial like " + filial.getId() 
	        		+ " and e.idProduto like " + itemVenda.getProduto().getId());
	        	
	        while(rs.next()){
	        
	        	int id = rs.getInt("idestoque");
	        	int qtd = Integer.parseInt(rs.getString("qtdProduto"));
	        	int novoValor = qtd - 1;
	        	
	        	stat.executeUpdate("UPDATE estoque SET qtdProduto = " + novoValor +" WHERE idestoque like "+ id);
	        }
        }
    }

    
    public int verificarEstoque(Filial filial, ItemVenda itemVenda) throws SQLException, ClassNotFoundException{
    	
    	int qtd = 0;
    	conexao = new Conexao();
        
        Statement stmt = conexao.getConnection().createStatement();
       
	    ResultSet rs = stmt.executeQuery(""
	        		+ "SELECT * FROM estoque e"
	        		+ " where e.idFilial like " + filial.getId() 
	        		+ " and e.idProduto like " + itemVenda.getProduto().getId());
	        	
	        while(rs.next()){
	        	qtd = Integer.parseInt(rs.getString("qtdProduto"));
	        }
	        
	        return qtd;
        
    }
}
