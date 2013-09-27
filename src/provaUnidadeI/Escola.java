package provaUnidadeI;

import java.io.Serializable;

import arvore.ArvoreAVL;
import utilitarios.MyIterator;
import mapa.ChaveValor;
import mapa.MyIteratorMapa;
import mapa.TreeMapa;

public class Escola implements Serializable {

	private TreeMapa<Aluno, ArvoreAVL<TurmaAluno>> mapAlunos;
	private TreeMapa<Disciplina, ArvoreAVL<TurmaDisc>> mapDisc;

	public Escola() {
		mapAlunos = new TreeMapa<Aluno, ArvoreAVL<TurmaAluno>>();
		mapDisc = new TreeMapa<Disciplina, ArvoreAVL<TurmaDisc>>();
	}

	public void addAluno(Aluno aluno) throws Exception {
		if (mapAlunos.contains(aluno))	throw new Exception("Aluno ja matriculado.");
		mapAlunos.put(aluno, new ArvoreAVL<TurmaAluno>());
	}

	public void removeAluno(Aluno aluno) throws Exception {

		ArvoreAVL<TurmaAluno> turmas = mapAlunos.getValor(aluno);

		if (turmas != null) {
			if (turmas.isEmpty())
				mapAlunos.remove(aluno);
			else
				throw new Exception("Aluno com Turmas matriculadas.");
		} else
			throw new Exception("Aluno não matriculado.");
	}

	public Aluno[] getAlunos() throws Exception {
		Object[] arrayChaveValor = mapAlunos.toArray();

		if (arrayChaveValor != null) {
			Aluno[] alunos = new Aluno[arrayChaveValor.length];

			for (int cont = 0; cont < alunos.length; cont++) {
				alunos[cont] = ((ChaveValor<Aluno, TurmaAluno>) arrayChaveValor[cont]).getChave();
			}
			return alunos;
		}
		throw new Exception("Nenhum aluno matriculado.");
	}

	public Aluno getAluno(Aluno aluno) throws Exception {
		Object[] arrayChaveValor = mapAlunos.toArray();

		if (arrayChaveValor != null) {
			Aluno temp = null;
			for (int cont = 0; cont < arrayChaveValor.length; cont++) {
				temp = ((ChaveValor<Aluno, TurmaAluno>) arrayChaveValor[cont])
						.getChave();
				if (temp.compareTo(aluno) == 0)
					return temp;
			}
		}
		throw new Exception("Aluno não encontrado.");
	}

	public void addDisciplina(Disciplina disc) throws Exception {
		if (!mapDisc.contains(disc))
			mapDisc.put(disc, new ArvoreAVL<TurmaDisc>());
		else
			throw new Exception("Codigo de Disciplina ja cadastrado.");
	}

	public void removeDisciplina(Disciplina disc) throws Exception {
		ArvoreAVL<TurmaDisc> turmas = mapDisc.getValor(disc);

		if (turmas != null) {
			if (turmas.isEmpty())
				mapDisc.remove(disc);
			else
				throw new Exception("Disciplina com Turmas matriculadas.");
		} else
			throw new Exception("Disciplina não matriculado.");

	}

	public Disciplina[] getDisciplinas() throws Exception {
		Object[] arrayChaveValor = mapDisc.toArray();

		if (arrayChaveValor != null) {
			Disciplina[] disciplinas = new Disciplina[arrayChaveValor.length];

			for (int cont = 0; cont < disciplinas.length; cont++) {
				disciplinas[cont] = ((ChaveValor<Disciplina, TurmaDisc>) arrayChaveValor[cont]).getChave();
			}
			return disciplinas;
		}
		throw new Exception("Nenhuma turma cadastrada.");
	}
	
	public void addTurma(String codTurma, Disciplina disc) throws Exception {
		ArvoreAVL<TurmaDisc> lista = mapDisc.getValor(disc);
		
		TurmaDisc achada = lista.retrieve(new TurmaDisc(codTurma));
		if (achada == null) {
			TurmaDisc newTurma = new TurmaDisc(codTurma);
			lista.add(newTurma);
		} else{
			throw new Exception("Turma já existe.");
		}
	}
	
	private TurmaDisc removerTurma(ArvoreAVL<TurmaDisc> lista, TurmaDisc turma)	throws Exception {
		if (lista.retrieve(turma) == null) {
			throw new Exception("Turma não encontrada.");
		} else {
			TurmaDisc turmaRemovida = lista.retrieve(turma);
			lista.remove(turma);
			return turmaRemovida;
		}
	}

	private boolean turmaComAlunos(TurmaAluno turma) {
		MyIteratorMapa<Aluno, ArvoreAVL<TurmaAluno>> it = mapAlunos.iterator();
		ChaveValor<Aluno, ArvoreAVL<TurmaAluno>> temp = it.getFirst();
		ArvoreAVL<TurmaAluno> turmasTemp;

		while (temp != null) {
			turmasTemp = temp.getValor();
			if (turmasTemp.retrieve(turma) != null)
				return true;
			temp = it.getNext();
		}
		return false;

	}

	public void removeTurma(TurmaDisc turma, Disciplina disc) throws Exception {
		
		ArvoreAVL<TurmaDisc> turmas = mapDisc.getValor(disc);
		if (turmas.retrieve(turma) == null)
			throw new Exception("Turma não encontrada.");
		TurmaAluno turmaAluno = new TurmaAluno(disc.getCod(), turma.getCodTurma(), disc.getNome());
		if (!turmaComAlunos(turmaAluno)){
			removerTurma(turmas, turma);
		}else{
			throw new Exception("Turma com alunos matriculados");
		}
	}

	public TurmaAluno[] getTurmas() throws Exception {
		
		MyIteratorMapa<Disciplina, ArvoreAVL<TurmaDisc>> it = mapDisc.iterator();
		ChaveValor<Disciplina, ArvoreAVL<TurmaDisc>> temp = it.getFirst();
		ArvoreAVL<TurmaDisc> turmasTemp;
		int cont = 0;	
		while (temp != null) {
				turmasTemp = temp.getValor();
				cont = cont + turmasTemp.size();
				temp = it.getNext();
		}
		if(cont == 0){
			throw new Exception("Não existem turmas");
		}
		TurmaAluno[] turmas = new TurmaAluno[cont];
		
		it = mapDisc.iterator();
		temp = it.getFirst();
		int i = 0;
		while (temp != null) {
			turmasTemp = temp.getValor();
			int codDisc = temp.getChave().getCod();
			String nomeDisc = temp.getChave().getNome();
			MyIterator<TurmaDisc> itz = temp.getValor().iterator();
			TurmaDisc turma = itz.getFirst();
			while(turma != null){
				turmas[i] = new TurmaAluno(codDisc, turma.getCodTurma(), nomeDisc);
				i++;
				turma = itz.getNext();
			}
			temp = it.getNext();
	    }
		return turmas;
	}

	public void matricularAlunoTurma(int matriculaAluno, TurmaAluno turma) throws Exception {

		ArvoreAVL<TurmaAluno> lista = mapAlunos.getValor(new Aluno(matriculaAluno));
		if (lista != null) {
			ChaveValor<Disciplina, ArvoreAVL<TurmaDisc>> turmas = mapDisc.getChaveValor(new Disciplina(turma.getCodDisc()));
			if(turmas.getValor().isEmpty()){
				throw new Exception("Esta turma não existe.");
			}else{
				TurmaDisc achada = turmas.getValor().retrieve(new TurmaDisc(turma.getCodTurma()));
				if(achada != null){
					if (lista.retrieve(turma) == null) {
						turma.setNomeDisc(turmas.getChave().getNome());
						lista.add(turma);
					} else {
						throw new Exception("Turma já foi matriculada.");
					}
				}else{
					throw new Exception("Esta turma não existe.");
				}
			}
		} else
			throw new Exception("Aluno não encontrado.");

	}

	private TurmaAluno desmatricular(ArvoreAVL<TurmaAluno> lista, TurmaAluno turma)	throws Exception {
		if (lista.retrieve(turma) == null) {
			throw new Exception("Turma não encontrada.");
		} else {
			TurmaAluno turmaRemovida = lista.retrieve(turma);
			lista.remove(turma);
			return turmaRemovida;
		}
	}
	
	public TurmaAluno removeMatriculaTurma(int matriculaAluno, TurmaAluno turma) throws Exception {
		ArvoreAVL<TurmaAluno> lista = mapAlunos.getValor(new Aluno(matriculaAluno));
		if (lista != null) {
			TurmaAluno retorno = desmatricular(lista, turma);
			if (retorno != null) {
				return retorno;
			} else {
				throw new Exception("Turma não matriculada.");
			}
		} else
			throw new Exception("Aluno não encontrado.");
	}

	public TurmaAluno[] listaTurmasDoAluno(Aluno aluno) throws Exception {
		ArvoreAVL<TurmaAluno> turmaAluno = mapAlunos.getValor(aluno);
		if (turmaAluno.size() != 0) {
			MyIterator<TurmaAluno> it = turmaAluno.iterator();

			TurmaAluno temp = it.getFirst();
			TurmaAluno[] turmas = new TurmaAluno[turmaAluno.size()];
			int cont = 0;

			while (temp != null) {
				turmas[cont] = temp;
				temp = it.getNext();
			}
			return turmas;
		}
		throw new Exception("Nenhuma turma matriculada para este aluno.");
	}

	public Aluno[] listaAlunosMatriculadosTurma(TurmaAluno turma) throws Exception {
		ArvoreAVL<Aluno> alunos = new ArvoreAVL<Aluno>();

		MyIteratorMapa<Aluno, ArvoreAVL<TurmaAluno>> it = mapAlunos.iterator();
		ChaveValor<Aluno, ArvoreAVL<TurmaAluno>> temp = it.getFirst();
		ArvoreAVL<TurmaAluno> turmasTemp;

		while (temp != null) {
			turmasTemp = temp.getValor();
			if (turmasTemp.retrieve(turma) != null)
				alunos.add(temp.getChave());
			temp = it.getNext();
		}
		if (!alunos.isEmpty()) {
			Aluno[] als = new Aluno[alunos.size()];
			MyIterator<Aluno> iterator = alunos.iterator();
			Aluno al = iterator.getFirst();
			int contador = 0;
			while (al != null) {
				als[contador] = al;
				al = iterator.getNext();
			}
			return als;
		}
		throw new Exception("Nenhum aluno matriculado nesta turma.");
	}

	public boolean disciplinaEmTurma(Disciplina disc) {
		ArvoreAVL<TurmaDisc> turmasTemp = mapDisc.getValor(disc);
		if(turmasTemp != null){
			if (turmasTemp.size() > 0)
				return true;
		}
		return false;
	}
}
