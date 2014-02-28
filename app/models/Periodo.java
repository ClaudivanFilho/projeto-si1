package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

/**
 * Entidade que representa um período
 */
@Entity
public class Periodo extends Model{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	private int numero;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "periodo_cadeira", 
    joinColumns = {@JoinColumn (name = "fk_periodo")}, inverseJoinColumns = {@JoinColumn(name = "fk_cadeira")})
	private Map<String, Cadeira> cadeiras;

	public Periodo(int numeroDoPeriodo) {
		this.numero = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
	}
	
	public Long getId(){
		return id;
	}	

	public static Finder<Long,Periodo> find = new Finder<Long,Periodo>(
		    Long.class, Periodo.class
	);
	
	public static void create(Periodo p) {
		p.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	public static void atualizar(Long id) {
		Periodo p = find.ref(id);
		p.update();
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
	
	public void setCadeiras(Map<String, Cadeira> cadeiras){
		this.cadeiras = cadeiras;
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