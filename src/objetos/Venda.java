/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objetos;

import java.util.ArrayList;

/**
 *
 * @author eduardo
 */
public class Venda {
    private String data;
    private ArrayList<ItemVenda> itemDaVenda;
    private String valor;
    private Integer idFilial;
    
    
    public Venda(){   
        this.itemDaVenda = new ArrayList<>();
    }
    
    public float getValorTotal(){
        float retorno = 0;
        
        for (ItemVenda itemDaVenda1 : this.itemDaVenda) {
            float valor = itemDaVenda1.getQuantidade() * itemDaVenda1.getValor();
            retorno += valor;
        }
        return retorno;
    }

    
    //*********************************************
    
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }


    /**
     * @return the itemDaVenda
     */
    public ArrayList<ItemVenda> getItemDaVenda() {
        return itemDaVenda;
    }

    /**
     * @param itemDaVenda the itemDaVenda to set
     */
    public void setItemDaVenda(ArrayList<ItemVenda> itemDaVenda) {
        this.itemDaVenda = itemDaVenda;
    }

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Integer getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Integer idFilial) {
		this.idFilial = idFilial;
	}
    
    
}
