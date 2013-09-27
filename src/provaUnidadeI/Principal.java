package provaUnidadeI;

import java.util.Comparator;
import utilitarios.Keyboard;
import utilitarios.ObjectFile;
import utilitarios.Sort;


public class Principal {
	
	static ObjectFile arqListas = new ObjectFile("provaUnidadeI.dat");
	static Escola escola = new Escola();
	
	public static void main(String[] args) {
		
		lerArquivo();
		int menu = Keyboard.menu("Manutenção de Alunos/Manutenção de Disciplinas/Manutenção de Turmas/Matrícula/Terminar");

		while (menu != 5) {
			Keyboard.clrscr();

			switch (menu) {
			case 1:
				manuAlunos(escola);
				break;

			case 2:
				manuDisc(escola);
				break;

			case 3:
				manuTurma(escola);
				break;
				
			case 4:
				matricula(escola);
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			menu = Keyboard.menu("Manutenção de Alunos/Manutenção de Disciplinas/Manutenção de Turmas/Matrícula/Terminar");
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
		int submenu = Keyboard.menu("Incluir Disciplina/Excluir Disciplina/Listar Disciplinas/Retornar");
		Keyboard.clrscr();
		while (submenu != 4) {
			switch (submenu) {
			case 1:
				try {
					escola.addDisciplina(lerDisciplina());
					System.out.println("Disciplina Adicionada com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				try {
					escola.removeDisciplina(new Disciplina(Keyboard.readInt("Informe o codigo da Disciplina: ")));
					System.out.println("Disciplina removida com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				System.out.println("Lista de Disciplinas:");
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

	public static void manuTurma(Escola escola) {
		int submenu = Keyboard.menu("Incluir Turma/Excluir Turma/Listar Turmas/Retornar");
		Keyboard.clrscr();

		while (submenu != 4) {
			switch (submenu) {
			case 1:
				try {
					addTurma();
					System.out.println("Turma adicionada com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				try {
					Disciplina disciplina = new Disciplina(Keyboard.readInt("Informe o codigo da disciplina: "));
					escola.removeTurma(new TurmaDisc(Keyboard.readString("Informe o codigo da turma: ")), disciplina);
					System.out.println("Turma removida com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				try {
					listarTurmas(escola.getTurmas());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			submenu = Keyboard.menu("Incluir Turma/Excluir Turma/Listar Turmas/Retornar");
		}
	}

	public static void matricula(Escola escola) {
		int submenu = Keyboard.menu("Matricular Aluno/Listar turmas matriculadas pelo aluno/Listar alunos matriculados em uma turma/Retornar");
		Keyboard.clrscr();

		while (submenu != 4) {
			int codAluno = 0, codDisc = 0; 
		    String codTurma;
			switch (submenu) {
			case 1:
				System.out.println("Informe:");
				codAluno = Keyboard.readInt("Matrícula do aluno: ");
				codTurma = Keyboard.readString("Codigo da Turma: ");
				codDisc = Keyboard.readInt("Codigo da Disciplina: ");

				try {
					escola.matricularAlunoTurma(codAluno, new TurmaAluno(codDisc,codTurma));
					System.out.println("Turma matriculada com sucesso.");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 2:
				codAluno = Keyboard.readInt("Informe a matrícula do aluno: ");
				Aluno al;
				try {
					al = escola.getAluno(new Aluno(codAluno));
					listarTurmasDoAluno(al, escola.listaTurmasDoAluno(al));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;

			case 3:
				codTurma = Keyboard.readString("Informe o codigo da turma: ");
				codDisc = Keyboard.readInt("Informe o codigo da disciplina: ");
				TurmaAluno turma = new TurmaAluno(codDisc, codTurma);
				try {
					listarAlunosDaTurma(turma,	escola.listaAlunosMatriculadosTurma(turma));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			submenu = Keyboard
					.menu("Matricular Aluno/Listar turmas matriculadas pelo aluno/Listar alunos matriculados em uma turma/Retornar");
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
	
	public static void addTurma() throws Exception{
		System.out.println("Informe os dados da Turma:");
		String codTurma = Keyboard.readString("Codigo: ");
		codTurma = Keyboard.readString("");
		int codDisc = Keyboard.readInt("Codigo da Disciplina: ");
		Disciplina[] discs = escola.getDisciplinas();
		boolean existe = false;
		for(Disciplina disc : discs){
			if(disc.compareTo(new Disciplina(codDisc)) == 0){
				existe = true;
			}
		}
		if(!existe){
			throw new Exception("Disciplina inexistente!");
		}else{
			escola.addTurma(codTurma, new Disciplina(codDisc));
		}
	}
	
	public static void listarAlunos(Aluno[] alunos) {
		System.out.println("Matricula     Nome");
		System.out.println("---------     ----------------------------------");
		for (Aluno al : alunos) {
			System.out.printf("%-9d     %-34s\n", al.getMatricula(), al.getNome());
		}
	}

	public static void listarTurmas(TurmaAluno[] turmas) {
		System.out.println("CodTurma       NomeDisc");
		System.out.println("------------   ----------");
		for (TurmaAluno turma : turmas) {
			System.out.printf("%-12s   %-10s\n", turma.getCodTurma(), turma.getNomeDisc());
		}
	}
	
	public static void listarDisciplinas(Disciplina[] disciplinas) {
		System.out.println("Codigo        Nome                              ");
		System.out.println("---------     ----------------------------------");
		for (Disciplina disc : disciplinas) {
			System.out.printf("%-9d     %-34s\n", disc.getCod(), disc.getNome());
		}
	}

	public static void listarTurmasDoAluno(Aluno aluno, TurmaAluno[] turmas) {
		System.out.println("Num Mat: " + aluno.getMatricula()	+ "     Nome do(a) aluno(a): " + aluno.getNome());
		System.out.println("CodTurma    Nome da disciplina");
		System.out.println("-------     ---------------------------");

		Sort.quickSort(turmas, new Comparator() {
			@Override
			public int compare(Object a0, Object a1) {
				return ((TurmaAluno) a0).getNomeDisc().compareToIgnoreCase(
						((TurmaAluno) a1).getNomeDisc());
			}
		});

		for (TurmaAluno turma : turmas) {
			System.out.printf("%-7s     %-27s\n", turma.getCodTurma(), turma.getNomeDisc());
		}
	}

	public static void listarAlunosDaTurma(TurmaAluno turma, Aluno[] alunos) {
		System.out.println("Cod Turma: " + turma.getCodTurma() + "     Codigo da disciplina: " + turma.getCodDisc());
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
