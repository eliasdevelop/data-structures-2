package lista04;

import java.io.Serializable;

import arvore.ArvoreAVL;
import utilitarios.MyIterator;
import mapa.ChaveValor;
import mapa.HashMapa;
import mapa.MyIteratorMapa;

public class Escola implements Serializable {
	
	private HashMapa<Aluno, ArvoreAVL<Turma>> mapAlunos;
	private ArvoreAVL<Turma> turmas;
	public ArvoreAVL<Disciplina> disciplinas;
	

	public Escola() {
		mapAlunos = new HashMapa<Aluno, ArvoreAVL<Turma>>();
		turmas = new ArvoreAVL<Turma>();
		disciplinas = new ArvoreAVL<Disciplina>();
	}
	
	public void addAluno(Aluno aluno) throws Exception {
		if (mapAlunos.contains(aluno))
			throw new Exception("Aluno ja matriculado.");
		mapAlunos.put(aluno, new ArvoreAVL<Turma>());
	}
	
	public void removeAluno(Aluno aluno) throws Exception {
		
		ArvoreAVL<Turma> turmas = mapAlunos.getValor(aluno);

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
				alunos[cont] = ((ChaveValor<Aluno, Turma>) arrayChaveValor[cont])
						.getChave();
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
				temp = ((ChaveValor<Aluno, Turma>) arrayChaveValor[cont])
						.getChave();
				if (temp.compareTo(aluno) == 0)
					return temp;
			}
		}
		throw new Exception("Aluno não encontrado.");
	}

	private Turma removerTurma(ArvoreAVL<Turma> lista ,Turma turma) throws Exception {		
		if(lista.retrieve(turma) == null){
			throw new Exception("Turma não encontrada.");
		}else{
			Turma turmaRemovida = lista.retrieve(turma);
			lista.remove(turma);
			return turmaRemovida;
		}		
	}

	private boolean turmaComAlunos(Turma turma) {
		MyIteratorMapa<Aluno, ArvoreAVL<Turma>> it = mapAlunos.iterator();
		ChaveValor<Aluno, ArvoreAVL<Turma>> temp = it.getFirst();
		ArvoreAVL<Turma> turmasTemp;

		while (temp != null) {
			turmasTemp = temp.getValor();
			if (turmasTemp.retrieve(turma) != null)
				return true;
			temp = it.getNext();
		}
		return false;

	}

	public void addTurma(Turma turma) throws Exception {
		if (turmas.retrieve(turma) == null)
			turmas.add(turma);
		else
			throw new Exception("Codigo de Turma ja cadastrado.");
	}

	public void removeTurma(Turma turma) throws Exception {
		if (turmas.retrieve(turma) == null)
			throw new Exception("Turma não encontrada.");
		if (!turmaComAlunos(turma))
			removerTurma(turmas, turma);
		else
			throw new Exception("Turma com alunos matriculados");

	}

	public Turma[] getTurmas() throws Exception {
		if (turmas.size() != 0) {
			Turma[] turmasArray = new Turma[turmas.size()];
			MyIterator<Turma> it = turmas.iterator();
			Turma temp = it.getFirst();

			int cont = 0;
			while (temp != null) {
				turmasArray[cont] = temp;
				temp = it.getNext();
				cont++;
			}
			return turmasArray;
		}
		throw new Exception("Nenhuma turma encontrada.");
	}

	public Turma getTurma(Turma turma) throws Exception {
		if (turmas.size() != 0) {			
			if(turmas.retrieve(turma) == null){
				throw new Exception("Disciplina não encontrada.");
			}
			else{
				return turmas.retrieve(turma);
			}
		}else{
			throw new Exception("Nehuma disciplina encontrada.");
		}
	}

	public void matricularAlunoTurma(int matriculaAluno, Turma turma)throws Exception {
		
		ArvoreAVL<Turma> lista = mapAlunos.getValor(new Aluno(matriculaAluno));
		if (lista != null) {
			Turma achada = turmas.retrieve(turma);
			if (achada != null) {
				if (lista.retrieve(turma) == null)
					lista.add(achada);
				else
					throw new Exception("Turma já foi matriculada.");
			} else
				throw new Exception("Esta turma não existe.");
		} else
			throw new Exception("Aluno não encontrado.");

	}

	public Turma removeMatriculaTurma(int matriculaAluno,	Turma turma) throws Exception {
		ArvoreAVL<Turma> lista = mapAlunos.getValor(new Aluno(matriculaAluno));
		if (lista != null) {
			Turma retorno = removerTurma(lista, turma);
			if (retorno != null)
				return retorno;
			throw new Exception("Turma não matriculada.");
		} else
			throw new Exception("Aluno não encontrado.");
	}

	public Turma[] listaTurmasDoAluno(Aluno aluno) throws Exception {
		ArvoreAVL<Turma> turmaAluno = mapAlunos.getValor(aluno);
		if (turmaAluno.size() != 0) {
			MyIterator<Turma> it = turmaAluno.iterator();

			Turma temp = it.getFirst();
			Turma[] turmas = new Turma[turmaAluno.size()];
			int cont = 0;

			while (temp != null) {
				turmas[cont] = temp;
				temp = it.getNext();
			}
			return turmas;
		}
		throw new Exception("Nenhuma turma matriculada para este aluno.");
	}

	public Aluno[] listaAlunosMatriculadosTurma(Turma turma) throws Exception {
		ArvoreAVL<Aluno> alunos = new ArvoreAVL<Aluno>();

		MyIteratorMapa<Aluno, ArvoreAVL<Turma>> it = mapAlunos.iterator();
		ChaveValor<Aluno, ArvoreAVL<Turma>> temp = it.getFirst();
		ArvoreAVL<Turma> turmasTemp;

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
	
	public void addDisciplina(Disciplina disc) throws Exception {
		if (disciplinas.retrieve(disc) == null)
			disciplinas.add(disc);
		else
			throw new Exception("Codigo de Disciplina ja cadastrado.");
	}

	public void removeDisciplina(Disciplina disc) throws Exception {
		if (disciplinas.retrieve(disc) == null)
			throw new Exception("Disciplina não encontrada.");
		if (!disciplinaEmTurma(disc))
			removerDisciplina(disciplinas, disc);
		else
			throw new Exception("Disciplina cadastrada em uma turma");

	}
	
	public Disciplina[] getDisciplinas() throws Exception {
		if (disciplinas.size() != 0) {
			Disciplina[] disciplinasArray = new Disciplina[disciplinas.size()];
			MyIterator<Disciplina> it = disciplinas.iterator();
			Disciplina temp = it.getFirst();

			int cont = 0;
			while (temp != null) {
				disciplinasArray[cont] = temp;
				temp = it.getNext();
				cont++;
			}
			return disciplinasArray;
		}
		throw new Exception("Nenhuma disciplina encontrada.");
	}
	
	private Disciplina removerDisciplina(ArvoreAVL<Disciplina> lista , Disciplina disc) throws Exception {		
		if(lista.retrieve(disc) == null){
			throw new Exception("Discipina não encontrada.");
		}else{
			Disciplina discRemovida = lista.retrieve(disc);
			lista.remove(disc);
			return discRemovida;
		}		
	}
	
	public boolean disciplinaEmTurma(Disciplina disc) {
		MyIterator<Turma> it = turmas.iterator();
		Turma temp = it.getFirst();
		
		while (temp != null) {
			if (temp.getDisciplina().compareTo(disc) == 0)
				return true;
			temp = it.getNext();
		}
		return false;

	}

}
