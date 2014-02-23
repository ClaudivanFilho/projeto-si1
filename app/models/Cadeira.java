package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.google.common.base.Objects;
/**
 * Entidade que representa uma Cadeira.
 */
@Entity
public class Cadeira extends Model implements Comparable<Cadeira>{

	private static final long serialVersionUID = 1L;

	// TODO PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	// a classe
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Constraints.Required
	@Column(unique = true, nullable=false)
	private String nome;
	
	private int creditos;

	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "cadeira_requisito", 
    joinColumns = {@JoinColumn (name = "fk_cadeira")}, inverseJoinColumns = {@JoinColumn(name = "fk_requisito")})
	private List<Cadeira> preRequisitos;
	
	private int dificuldade; // dificuldade de 1 - 10
	
	@ManyToOne(cascade=CascadeType.ALL)
	@Column(name="fk_periodo")
	private Periodo periodo;

	public Cadeira() {
		setPreRequisitos(new ArrayList<Cadeira>());
	}

	public Cadeira(String nome, int dificuldade) {
		this.setNome(nome);	
		this.creditos = 4;
		this.dificuldade = dificuldade;
		setPreRequisitos(new ArrayList<Cadeira>());
	}

	public Cadeira(String nome, int dificuldade, int creditos) {
		this(nome, dificuldade);
		this.creditos = creditos;
	}
	
	/**
	 * Retorna verdadeiro caso a cadeira {@code c} seja pre-requisito, Seguindo
	 * o padrão Information Expert, quem deve saber se uma cadeira é
	 * pre-requisito é a mesma.
	 */
	public boolean isPreRequisito(Cadeira c) {
		return this.getPreRequisitos().contains(c);
	}

	// TODO PADRÃO DE PROJETO: INFORMATION EXPERT - a classe cadeira é a
	// responsável por guardar e adicionar pre-requisitos
	public void addPreRequisito(Cadeira... c) {
		Cadeira[] lista = c;
		for (Cadeira cadeira : lista) {
			getPreRequisitos().add(cadeira);
		}
	}
	
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public int getCreditos() {
		return this.creditos;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public List<Cadeira> getPreRequisitos() {
		return preRequisitos;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreRequisitos(List<Cadeira> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public int getNumeroPeriodo(){
		return periodo.getNumero();
	}
	
	public static Finder<Long,Cadeira> find = new Finder<Long,Cadeira>(
		    Long.class, Cadeira.class
	); 
	
	public static void create(Cadeira c) {
		c.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	public static void atualizar(Long id) {
		Cadeira p = find.ref(id);
		p.update();
	}
	
	@Override
	public int compareTo(Cadeira c) {
		return getNome().compareTo(c.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getNome(), creditos,
				getPreRequisitos());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadeira other = (Cadeira) obj;
		return Objects.equal(this.getCreditos(), other.getCreditos())
				&& Objects.equal(this.getNome(), other.getNome())
				&& Objects.equal(this.getPreRequisitos(),
						other.getPreRequisitos());
	}

}