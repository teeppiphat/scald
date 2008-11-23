package framework.util;

import org.fenggui.render.ITexture;
import org.fenggui.render.Pixmap;
import org.fenggui.table.ITableModel;

public class TableModel implements ITableModel{

	Object[][] matrix = null;
	boolean[] targets = null;
	
	public TableModel()
	{		

	}
	
	public void initMatrix(int row,int col){
		//allocate the space
		matrix = new Object[row][col];
		//null all spaces
		for(int i = 0; i < matrix.length; i++){
			matrix[i] = null;
		}
		
	}
	
	public int insertPos(){
		//look for the position
		for(int i = 0; i < matrix.length; i++){
			if(matrix[i] == null){				
				return i;
			}
		}
		//position free wasn´t found
		return -1;
	}
	
	public void addRowCharacter(ITexture image, String name){
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[2];
		
		//add the item to the table		
		matrix[pos][0] = new Pixmap(image);	
		matrix[pos][1] = new String(name);
		
	}
	
	public void addRowMagic(ITexture image, String name, int quantity){
		
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[3];
		
		//add the item to the table		
		matrix[pos][0] = new Pixmap(image);	
		matrix[pos][1] = new String(name);
		matrix[pos][2] = new String("" + quantity);
		
	}
	
	public void addRowSell(ITexture image, String name, int quantity, int value){
		
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[4];
		
		//add the item to the table		
		matrix[pos][0] = new Pixmap(image);	
		matrix[pos][1] = new String(name);
		matrix[pos][2] = new String("x" + quantity);
		matrix[pos][3] = new String("" + value);
		
	}
		
	public void addRow(ITexture image, String name, int quantity){
				
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[3];
		
		//add the item to the table		
		matrix[pos][0] = new Pixmap(image);	
		matrix[pos][1] = new String(name);
		matrix[pos][2] = new String("x" + quantity);
		
	}
	
	public void addRow(String name){
		
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[1];
		
		//add the item to the table
		matrix[pos][0] = new String(name);
		
	}
	
	public void addRowTarget(String name, boolean group){
		//add the item to the table
		
		//must find the row to put it
		int pos = insertPos();
		
		//allocate the position
		matrix[pos] = new Object[1];
		targets = new boolean[this.getRowCount()];
		
		//add the item to the table
		matrix[pos][0] = new String(name);
		targets[pos] = group;
		//matrix[pos][1] = new Boolean(group);
		
	}
	
	public String getColumnName(int columnIndex)
	{
		return "COLUMN" + columnIndex;
	}

	public int getColumnCount()
	{
		return matrix[0].length;
	}

	public Object getValue(int row, int column)
	{
		return matrix[row][column];
	}

	public int getRowCount()
	{
		return matrix.length;
	}

	public void clear()
	{
		// TODO implement
	}

	public Object getValue(int row)
	{		
		return matrix[row];
	}

	public boolean[] getTargets() {
		return targets;
	}
	
	public boolean getTargetValue(int row){
		return targets[row];
	}
	
}
