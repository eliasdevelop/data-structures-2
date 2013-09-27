package provaUnidadeI;

import java.io.Serializable;

public class TurmaDisc implements Comparable<TurmaDisc>, Serializable {

	private String codTurma;
	
	public TurmaDisc(String codTurma) {
		super();
		this.codTurma = codTurma;
	}
	
	public String getCodTurma(){
		return codTurma;
	}

	@Override
	public int compareTo(TurmaDisc o) {
		if (o.codTurma.equalsIgnoreCase(codTurma))
			return 0;
		return 1;
	}

}
