package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entidade que representa um período
 */
public class Periodo{

	private int numero;

	private Map<String, Cadeira> cadeiras;

	public Periodo(int numeroDoPeriodo) {
		this.numero = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
	}

	public void addCadeira(Cadeira cadeira){
		cadeiras.put(cadeira.getNome(), cadeira);
	}

	public void removerCadeira(Cadeira cadeira) {
		cadeiras.remove(cadeira.getNome());
	}

	public int getDificuldadeTotal() {
		int difi = 0;
		for (Cadeira c : getCadeiras().values()) {
			difi += c.getDificuldade();
		}
		return difi;
	}

	/**
	 * Calcula o total de Créditos do Periodo
	 * 
	 * Responsabilidade Atribuída seguindo o padrão Information Expert
	 */
	public int getCreditos() {
		int sum = 0;
		for (Cadeira c : getCadeiras().values()) {
			sum += c.getCreditos();
		}
		return sum;
	}

	public Map<String, Cadeira> getCadeiras() {
		return cadeiras;
	}
	
	public List<Cadeira> getListaCadeiras(){
		List<Cadeira> cadeiras = new ArrayList<Cadeira>();
		for(Cadeira c: getCadeiras().values()){
			cadeiras.add(c);
		}
		return cadeiras;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero){
		this.numero = numero;
	}

	public Cadeira getCadeira(String cadeira) {
		return cadeiras.get(cadeira);
	}
}