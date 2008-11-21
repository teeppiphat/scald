package framework.rpgsystem.item;

import java.io.Serializable;

/**
 * It´s used to determine the quantity of an item that
 * can be in the Bag and the quantity of that item
 * that the Bag has
 * 
 * @author Diego Antônio Tronquini Costi
 *
 */
public class ItemEntry implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents how many of this item that the bag
	 * or the character has
	 */
	private int qtd;
	 
	/**
	 * Represents the maximum of determined item the
	 * bag or the character could have
	 */
	private int maxQtd;
	 
	/**
	 * Represents the item in question
	 */
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getMaxQtd() {
		return maxQtd;
	}

	public void setMaxQtd(int maxQtd) {
		this.maxQtd = maxQtd;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public void increaseQuantity(){
		qtd++;
	}
	
	public void deacreseQuantity(){
		qtd--;
	}
	
	public void increaseQuantityValue(int quantity){
		qtd = qtd + quantity;
	}
	
	public void decreaseQuantityValue(int quantity){
		qtd = qtd - quantity;
	}
	
}
 
