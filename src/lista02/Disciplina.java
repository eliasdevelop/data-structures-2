package lista02;

import java.io.Serializable;

public class Disciplina implements Comparable<Disciplina>, Serializable {
	private int cod;
	private String nome;

	public int getCod() {
		return cod;
	}

	public String getNome() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		return cod;
	}

	@Override
	public int compareTo(Disciplina disc) {
		Integer cod = this.cod;
		return cod.compareTo(disc.cod);
	}

	public Disciplina(int cod) {
		this.cod = cod;
	}
	
	public Disciplina(int cod, String nome) {
		this(cod);
		this.nome = nome;
	}
}
