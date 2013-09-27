package lista02;

import java.io.Serializable;
import utilitarios.MyIterator;
import listasEncadeadas.SkipList;
import mapa.ChaveValor;
import mapa.HashMapa;
import mapa.MyIteratorMapa;

public class Escola implements Serializable {
	
	private HashMapa<Aluno, SkipList<Disciplina>> mapAlunos;
	private SkipList<Disciplina> disciplinas;
	

	public Escola() {
		mapAlunos = new HashMapa<Aluno, SkipList<Disciplina>>();
		disciplinas = new SkipList<Disciplina>(10);
	}
	
	public void addAluno(Aluno aluno) throws Exception {
		if (mapAlunos.contains(aluno))
			throw new Exception("Aluno ja matriculado.");
		mapAlunos.put(aluno, new SkipList<Disciplina>(10));
	}

	public void removeAluno(Aluno aluno) throws Exception {
		
		SkipList<Disciplina> disciplinas = mapAlunos.getValor(aluno);

		if (disciplinas != null) {
			if (disciplinas.isEmpty())
				mapAlunos.remove(aluno);
			else
				throw new Exception("Aluno com diciplinas matriculadas.");
		} else
			throw new Exception("Aluno não matriculado.");
	}

	public Aluno[] getAlunos() throws Exception {
		Object[] arrayChaveValor = mapAlunos.toArray();

		if (arrayChaveValor != null) {
			Aluno[] alunos = new Aluno[arrayChaveValor.length];

			for (int cont = 0; cont < alunos.length; cont++) {
				alunos[cont] = ((ChaveValor<Aluno, Disciplina>) arrayChaveValor[cont]).getChave();
			}
			return alunos;
		}
		throw new Exception("Nenhum aluno matriculado.");
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
		throw new Exception("Nehuma disciplina encontrada.");
	}

	public Aluno getAluno(Aluno aluno) throws Exception {
		Object[] arrayChaveValor = mapAlunos.toArray();

		if (arrayChaveValor != null) {
			Aluno temp = null;
			for (int cont = 0; cont < arrayChaveValor.length; cont++) {
				temp = ((ChaveValor<Aluno, Disciplina>) arrayChaveValor[cont]).getChave();
				if (temp.compareTo(aluno) == 0)
					return temp;
			}
		}
		throw new Exception("Aluno não encontrado.");
	}

	private Disciplina acharDisc(SkipList<Disciplina> lista, Disciplina disc) {
		MyIterator<Disciplina> it = lista.iterator();
		Disciplina temp = it.getFirst();

		while (temp != null) {
			if (temp.compareTo(disc) == 0)
				return temp;
			temp = it.getNext();
		}
		return null;
	}

	private Disciplina removerDisc(SkipList<Disciplina> lista,	Disciplina disc) throws Exception {
		MyIterator<Disciplina> it = lista.iterator();
		Disciplina temp = it.getFirst();

		while (temp != null) {
			if (temp.compareTo(disc) == 0) {
				it.remove();
				return temp;
			}
			temp = it.getNext();
		}
		throw new Exception("Discipina não encontrada.");
	}

	private boolean discComAlunos(Disciplina disc) {
		MyIteratorMapa<Aluno, SkipList<Disciplina>> it = mapAlunos.iterator();
		ChaveValor<Aluno, SkipList<Disciplina>> temp = it.getFirst();
		SkipList<Disciplina> disciplinasTemp;

		while (temp != null) {
			disciplinasTemp = temp.getValor();
			if (acharDisc(disciplinasTemp, disc) != null)
				return true;
			temp = it.getNext();
		}
		return false;

	}

	public void addDisciplina(Disciplina disc) throws Exception {
		if (acharDisc(disciplinas, disc) == null)
			disciplinas.add(disc);
		else
			throw new Exception("Codigo de Disciplina ja cadastrado.");
	}

	public void removeDisciplina(Disciplina disc) throws Exception {
		Disciplina achada = acharDisc(disciplinas, disc);
		if (achada == null)
			throw new Exception("Disciplina não encontrada.");
		if (!discComAlunos(achada))
			removerDisc(disciplinas, achada);
		else
			throw new Exception("Disciplina com alunos matriculados");

	}

	public Disciplina getDisciplina(Disciplina disciplina) throws Exception {
		if (disciplinas.size() != 0) {
			MyIterator<Disciplina> it = disciplinas.iterator();
			Disciplina temp = it.getFirst();

			int cont = 0;
			while (temp != null) {
				if (temp.compareTo(disciplina) == 0)
					return temp;
				temp = it.getNext();
				cont++;
			}
			throw new Exception("Disciplina não encontrada.");
		}
		throw new Exception("Nehuma disciplina encontrada.");
	}

	public void matricularAlunoDisc(int matriculaAluno, Disciplina disc)
			throws Exception {
		SkipList<Disciplina> lista = mapAlunos.getValor(new Aluno(
				matriculaAluno));
		if (lista != null) {
			Disciplina achada = acharDisc(disciplinas, disc);
			if (achada != null) {
				if (acharDisc(lista, disc) == null)
					lista.add(achada);
				else
					throw new Exception("Disciplina já foi matriculada.");
			} else
				throw new Exception("Esta disciplina não existe.");
		} else
			throw new Exception("Aluno não encontrado.");

	}

	public Disciplina removeMatriculaDisciplina(int matriculaAluno,
			Disciplina disc) throws Exception {
		SkipList<Disciplina> lista = mapAlunos.getValor(new Aluno(matriculaAluno));

		if (lista != null) {
			Disciplina retorno = removerDisc(lista, disc);
			if (retorno != null)
				return retorno;
			throw new Exception("Disciplina não matriculada.");
		} else
			throw new Exception("Aluno não encontrado.");
	}

	public Disciplina[] listaDisciplinasDoAluno(Aluno aluno) throws Exception {
		SkipList<Disciplina> discAluno = mapAlunos.getValor(aluno);
		if (discAluno.size() != 0) {
			MyIterator<Disciplina> it = discAluno.iterator();

			Disciplina temp = it.getFirst();
			Disciplina[] disciplinas = new Disciplina[discAluno.size()];
			int cont = 0;

			while (temp != null) {
				disciplinas[cont] = temp;
				temp = it.getNext();
			}
			return disciplinas;
		}
		throw new Exception("Nenhuma disciplina matriculada para este aluno.");
	}

	public Aluno[] listaAlunosMatriculadosDisciplina(Disciplina disc)
			throws Exception {
		SkipList<Aluno> alunos = new SkipList<Aluno>(10);

		MyIteratorMapa<Aluno, SkipList<Disciplina>> it = mapAlunos.iterator();
		ChaveValor<Aluno, SkipList<Disciplina>> temp = it.getFirst();
		SkipList<Disciplina> disciplinasTemp;

		while (temp != null) {
			disciplinasTemp = temp.getValor();
			if (acharDisc(disciplinasTemp, disc) != null)
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
		throw new Exception("Nenhum aluno matriculado nesta disciplina.");
	}
	
}
