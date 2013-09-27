package lista01;

import java.util.Comparator;

import listasSequenciais.ListaSeqOrd;

import utilitarios.Keyboard;
import utilitarios.ObjectFile;
import utilitarios.Sort;


public class Principal {
	
	static ObjectFile arqListas = new ObjectFile("lista01.dat");
	static Escola escola = new Escola();
	
	public static void main(String[] args) {
		
		lerArquivo();
		int menu = Keyboard.menu("Manutenção de Alunos/Manutenção de Disciplinas/Matrícula/Terminar");

		while (menu != 4) {
			Keyboard.clrscr();

			switch (menu) {
			case 1:
				manuAlunos(escola);
				break;

			case 2:
				manuDisc(escola);
				break;

			case 3:
				matricula(escola);
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			menu = Keyboard
					.menu("Manutenção de Alunos/Manutenção de Disciplinas/Matrícula/Terminar");
		}
		System.out.println("Salvando dados...");
		gravarArquivo();
		System.out.println("Programa encerrado");
	}

	public static void manuAlunos(Escola escola) {
		int submenu = Keyboard
				.menu("Incluir Aluno/Excluir Aluno/Listar Alunos/Retornar");
		Keyboard.clrscr();
		while (submenu != 4) {
			switch (submenu) {
			case 1:
				try {
					escola.addAluno(lerAluno());
					System.out.println("Aluno adicionado com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				try {
					escola.removeAluno(new Aluno(Keyboard
							.readInt("Informe a matricula do aluno: ")));
					System.out.println("Aluno removido com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				System.out.println("Lista de alunos:");
				try {
					listarAlunos(escola.getAlunos());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			submenu = Keyboard
					.menu("Incluir Aluno/Excluir Aluno/Listar Alunos/Retornar");
		}
	}

	public static void manuDisc(Escola escola) {
		int submenu = Keyboard
				.menu("Incluir Disciplina/Excluir Disciplina/Listar Disciplinas/Retornar");
		Keyboard.clrscr();

		while (submenu != 4) {
			switch (submenu) {
			case 1:
				try {
					escola.addDisciplina(lerDisciplina());
					System.out.println("Disciplina adicionada com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				try {
					escola.removeDisciplina(new Disciplina(Keyboard
							.readInt("Informe o codigo da disciplina: ")));
					System.out.println("Disciplina removida com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				try {
					listarDisciplinas(escola.getDisciplinas());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			submenu = Keyboard
					.menu("Incluir Disciplina/Excluir Disciplina/Listar Disciplinas/Retornar");
		}
	}

	public static void matricula(Escola escola) {
		int submenu = Keyboard
				.menu("Matricular Aluno/Listar disciplinas matriculadas pelo aluno/Listar alunos matriculados em uma disciplina/Retornar");
		Keyboard.clrscr();

		while (submenu != 4) {
			int codAluno = 0, codDisc = 0;
			switch (submenu) {
			case 1:
				System.out.println("Informe:");
				codAluno = Keyboard.readInt("Matrícula do aluno: ");
				codDisc = Keyboard.readInt("Codigo da disciplina: ");
				try {
					escola.matricularAlunoDisc(codAluno,
							new Disciplina(codDisc));
					System.out.println("Disciplina matriculada com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				codAluno = Keyboard.readInt("Informe a matrícula do aluno: ");
				Aluno al;
				try {
					al = escola.getAluno(new Aluno(codAluno));
					listarDisciplinasDoAluno(al,
							escola.listaDisciplinasDoAluno(al));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				codDisc = Keyboard.readInt("Informe o codigo da disciplina: ");
				Disciplina disc;
				try {
					disc = escola.getDisciplina(new Disciplina(codDisc));
					listarAlunosDaDisciplina(disc,
							escola.listaAlunosMatriculadosDisciplina(disc));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			submenu = Keyboard
					.menu("Matricular Aluno/Listar disciplinas matriculadas pelo aluno/Listar alunos matriculados em uma disciplina/Retornar");
		}
	}

	public static Aluno lerAluno() {
		System.out.println("Informe os dados do aluno:");
		String nome = Keyboard.readString("Nome: ");
		nome = Keyboard.readString("");
		int matricula = Keyboard.readInt("Matrícula: ");
		return new Aluno(matricula, nome);
	}

	public static Disciplina lerDisciplina() {
		System.out.println("Informe os dados da Disciplina:");
		String nome = Keyboard.readString("Nome: ");
		nome = Keyboard.readString("");
		int cod = Keyboard.readInt("Codigo: ");
		return new Disciplina(cod, nome);
	}

	public static void listarAlunos(Aluno[] alunos) {
		System.out.println("Matricula     Nome");
		System.out.println("---------     ----------------------------------");
		for (Aluno al : alunos) {
			System.out.printf("%-9d     %-34s\n", al.getMatricula(),
					al.getNome());
		}
	}

	public static void listarDisciplinas(Disciplina[] disciplinas) {
		System.out.println("Codigo        Nome");
		System.out.println("---------     ----------------------------------");
		for (Disciplina disc : disciplinas) {
			System.out
					.printf("%-9d     %-34s\n", disc.getCod(), disc.getNome());
		}
	}

	public static void listarDisciplinasDoAluno(Aluno aluno,
			Disciplina[] disciplinas) {
		System.out.println("Num Mat: " + aluno.getMatricula()
				+ "     Nome do(a) aluno(a): " + aluno.getNome());
		System.out.println("CodDisc     Nome da disciplina");
		System.out.println("-------     ---------------------------");

		Sort.quickSort(disciplinas, new Comparator() {
			@Override
			public int compare(Object a0, Object a1) {
				return ((Disciplina) a0).getNome().compareToIgnoreCase(
						((Disciplina) a1).getNome());
			}
		});

		for (Disciplina disc : disciplinas) {
			System.out
					.printf("%-7d     %-27s\n", disc.getCod(), disc.getNome());
		}
	}

	public static void listarAlunosDaDisciplina(Disciplina disciplina,
			Aluno[] alunos) {
		System.out.println("Cod Disc: " + disciplina.getCod()
				+ "     Nome da disciplina: " + disciplina.getNome());
		System.out.println("NMat     Nome do(a) Aluno(a)");
		System.out.println("----     ---------------------------");

		Sort.quickSort(alunos, new Comparator() {
			@Override
			public int compare(Object a0, Object a1) {
				return ((Aluno) a0).getNome().compareToIgnoreCase(
						((Aluno) a1).getNome());
			}
		});

		for (Aluno al : alunos) {
			System.out.printf("%-4d     %-27s\n", al.getMatricula(),
					al.getNome());
		}
	}	
	
	static void gravarArquivo() {
		arqListas.rewrite();
		arqListas.write(escola);
		arqListas.closeFile();
	}

	static void lerArquivo() {
		if (arqListas.reset()) {
			escola = (Escola) arqListas.read();
			arqListas.closeFile();
		} else {
			escola = new Escola();			
		}
	}
}
