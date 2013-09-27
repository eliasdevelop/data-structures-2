package lista03;

import java.io.Serializable;

import arvore.ArvoreBusca;

public class Turma implements Comparable<Turma>, Serializable {

	private String nome;
	private int cod;
	private Disciplina disciplina;
	
	public Turma(String nome, int cod, Disciplina disciplina) {
		super();
		this.nome = nome;
		this.cod = cod;
		this.disciplina = disciplina;
	}
	
	public Turma(int cod) {
		super();
		this.cod = cod;
	}
	
	public int getCod() {
		return cod;
	}

	public String getNome() {
		return nome;
	}
	
	public Disciplina getDisciplina(){
		return disciplina;
	}
		
	@Override
	public int hashCode() {
		return cod;
	}

	@Override
	public int compareTo(Turma turma) {
		Integer cod = this.cod;
		return cod.compareTo(turma.cod);
	}

	
}
