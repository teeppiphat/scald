package framework.util.persistent;

import java.io.IOException;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import framework.rpgsystem.evolution.Feature;
import framework.rpgsystem.evolution.Character;

public class PersitentManager {
	
	private Prevayler sistema;	
	
	public PersitentManager(String root){
		PrevaylerFactory factory = new PrevaylerFactory();    
		factory.configurePrevalentSystem(DataCollection.getInstance());    
		factory.configurePrevalenceDirectory(root);
		try {
			sistema = factory.create();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 		
	}
	
	public void update() throws IOException {
		sistema.takeSnapshot();
	}

	public void addFeature(Feature f) {
		sistema.execute(new AddFeature(f));	
	}

	public void removeFeature(Feature f) {
		sistema.execute(new RemoveFeature(f));		
	}
	
	public void addCharacter(Character c){
		sistema.execute(new AddCharacter(c));
	}
	
	public void removeCharacter(Character c){
		sistema.execute(new RemoveCharacter(c));
	}
	
	//public void editarPaciente(int i, String string, Date date, Historico h) {
	//	try {
//			sistema.execute(new EditarPaciente(i,string,date,h));
		//} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	//}	
}
/*
 public class EditarPaciente implements TransactionWithQuery {

	private static final long serialVersionUID = 1L;
	
	private int index;
	private String nome;
	private Date data;

	private Historico historico;

	public EditarPaciente(int i, String string, Date date, Historico h) {
		this.index = i;
		this.nome = string;
		this.data = date;
		this.historico = h;
	}

	public Object executeAndQuery(Object arg0, Date arg1) throws Exception {
		ListaDePacientes lista = (ListaDePacientes)arg0;
		Paciente p = lista.pegarPaciente(index);
		p.setNome(nome);
		p.setNascimento(data);
		p.setHistorico(historico);
		return null;
	}
}
 */
