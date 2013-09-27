package provaUnidadeI;

import java.io.Serializable;

public class TurmaAluno implements Comparable<TurmaAluno>, Serializable{
	
	private int codDisc;
	private String codTurma;
	private String nomeDisc;
	
	public TurmaAluno(int codDisc, String codTurma, String nomeDisc) {
		super();
		this.codDisc = codDisc;
		this.codTurma = codTurma;
		this.nomeDisc = nomeDisc;
	}
	
	public TurmaAluno(int codDisc, String codTurma) {
		super();
		this.codDisc = codDisc;
		this.codTurma = codTurma;
	}

	public int getCodDisc() {
		return codDisc;
	}
	
	public void setNomeDisc(String nomeDisc) {
		this.nomeDisc = nomeDisc;
	}

	public String getCodTurma() {
		return codTurma;
	}
	
	public String getNomeDisc() {
		return nomeDisc;
	}

	@Override
	public int compareTo(TurmaAluno o1) {
		if(codDisc == o1.codDisc && codTurma.equalsIgnoreCase(codTurma)){
			return 0;
		}
		return 1;
	}
}
