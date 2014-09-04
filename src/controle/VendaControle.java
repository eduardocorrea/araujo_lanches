/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import objetos.Funcionario;
import objetos.Produto;
import objetos.Venda;
import banco.Conexao;

import com.mysql.jdbc.PreparedStatement;

public class VendaControle {
    private Conexao conexao;
    private PreparedStatement pstmt = null; 
    
    public Produto carregarProduto(String nomeProduto) throws SQLException, ClassNotFoundException{
        
    	conexao = new Conexao();
    	
        Statement stmt = conexao.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM produto");
        Produto p = new Produto();
        
        while(rs.next()){
            if(rs.getString("descricao").equals(nomeProduto) || rs.getString("codigo").equals(nomeProduto)){
                p.setId(rs.getInt("idProduto"));
                p.setDescricao(rs.getString("descricao"));
                p.setValorUnitario(rs.getFloat("valorUnitario"));
                p.setCodigo(rs.getString("codigo"));
            }
        }
        return p;
    }
    
    public boolean salvarVenda(Venda venda, Funcionario f) throws SQLException, ClassNotFoundException{
    	conexao = new Conexao();
    	
    	try{
    	Date data = new Date();  
    	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
    	
        pstmt = (PreparedStatement) conexao.getConnection().prepareStatement(""
        		+ "insert into venda (dataVenda, valorTotal, idFuncionario, idFilial) values (?,?,?,?)");
    	
        pstmt.setString(1, formatador.format(data));
        pstmt.setString(2, venda.getValor());
        pstmt.setInt(3, f.getId());
        pstmt.setInt(4, venda.getIdFilial());
        
        pstmt.executeUpdate();  
        pstmt.close();  
        return true;  
    } catch (SQLException e) {  
       e.printStackTrace();  
        return false;  
    }  
    	
    }
    
}
