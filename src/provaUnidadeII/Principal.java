package provaUnidadeII;

import mapa.ChaveValor;
import utilitarios.Keyboard;
import utilitarios.MyIterator;
import heap.MaxHeap;
import arvore.ArvoreRedBlack;

public class Principal {

	static ArvoreRedBlack<ChaveValor<Integer, Pessoa>> pessoasNoRepeat = new ArvoreRedBlack<ChaveValor<Integer, Pessoa>>(false);
	static ArvoreRedBlack<ChaveValor<Float, Pessoa>> pessoasRepeat = new ArvoreRedBlack<ChaveValor<Float, Pessoa>>(true);
	static MaxHeap<ChaveValor<Integer, Pessoa>> filaPrioridade = new MaxHeap<ChaveValor<Integer, Pessoa>>(); //idade prioridade MaxHeap
	
	
	public static void main(String[] args) {
		
		int menu = Keyboard.menu("Incluir Pessoas/Listar Pessoas/Remover Pessoa/Excluir Pessoas por salario/Terminar");

		while (menu != 5) {
			Keyboard.clrscr();

			switch (menu) {
			case 1:
				incluirPessoas();
				break;

			case 2:
				listarPessoas();
				break;

			case 3:
				removerPessoa();
				break;
				
			case 4:
				excluirPorSalario();
				break;
			}
			Keyboard.waitEnter();
			Keyboard.clrscr();
			menu = Keyboard.menu("Incluir Pessoas/Listar Pessoas/Remover Pessoa/Excluir Pessoas por salario/Terminar");
		}
		System.out.println("Programa encerrado");
	}

	private static void excluirPorSalario() {
		Float salario = Keyboard.readFloat("Salario: ");
		int cont = 0;		
		MyIterator<ChaveValor<Float, Pessoa>> it = pessoasRepeat.iterator();
		ChaveValor<Float, Pessoa> p1 = it.getFirst();
		while(p1 != null){
			if(p1.getChave().floatValue() == salario){
				cont++;
				pessoasNoRepeat.remove(new ChaveValor<Integer,Pessoa>(p1.getValor().getCodigo(), p1.getValor()));
				pessoasRepeat.remove(p1);	
				MyIterator<ChaveValor<Integer, Pessoa>> it2 = filaPrioridade.iterator();
				ChaveValor<Integer, Pessoa> chave = it2.getFirst();
				while (chave != null) {
					if(chave.getValor() == p1.getValor()) {
						it2.remove();
					}
					chave = it2.getNext();
				}
				pessoasRepeat.remove(p1);				
			}
			p1 = it.getNext();
		}
				
		if(cont == 0){
			System.out.println("Salario inexistente");
		}else{
			System.out.println(cont + " removidos com sucesso");
		}
	}

	private static void removerPessoa() {
		ChaveValor<Integer, Pessoa> removido = filaPrioridade.remove();
		
		if(removido != null){
		Pessoa pessoa = removido.getValor();
		
		ChaveValor<Float, Pessoa> chave = new ChaveValor<Float, Pessoa>(pessoa.getSalario(), pessoa);
		pessoasRepeat.remove(chave);
		
		ChaveValor<Integer, Pessoa> chave2 = new ChaveValor<Integer, Pessoa>(pessoa.getCodigo(), pessoa);
		pessoasNoRepeat.remove(chave2);
		
		System.out.println("Removido com sucesso!");
		}
	}

	private static void listarPessoas() {
		int menu = Keyboard.menu("Por Codigo/Por Salario/Por Idade/Voltar");

		while (menu != 4) {
			Keyboard.clrscr();

			switch (menu) {
			case 1:
				listarCodigo();
				break;

			case 2:
				listarSalario();
				break;

			case 3:
				listarIdade();
				break;
			}	
			Keyboard.waitEnter();
			Keyboard.clrscr();
			menu = Keyboard.menu("Por Codigo/Por Salario/Por Idade/Voltar");			
		}
	}

	private static void listarIdade() {
		System.out.println("Codigo  Nome                  Salario  Idade");
		System.out.println("------  --------------------  -------  -----");
		MyIterator<ChaveValor<Integer, Pessoa>> it = filaPrioridade.iterator();
		ChaveValor<Integer, Pessoa> p1 = it.getFirst();
		while(p1 != null){
			System.out.printf("%-6d  %-20s  %-7.2f  %-5d\n", p1.getValor().getCodigo(), p1.getValor().getNome(), p1.getValor().getSalario(), p1.getValor().getIdade());
			p1 = it.getNext();
		}			
	}

	private static void listarSalario() {
		System.out.println("Codigo  Nome                  Salario  Idade");
		System.out.println("------  --------------------  -------  -----");
		MyIterator<ChaveValor<Float, Pessoa>> it = pessoasRepeat.iterator();
		ChaveValor<Float, Pessoa> p1 = it.getFirst();
		while(p1 != null){
			System.out.printf("%-6d  %-20s  %-7.2f  %-5d\n", p1.getValor().getCodigo(), p1.getValor().getNome(), p1.getValor().getSalario(), p1.getValor().getIdade());
			p1 = it.getNext();
		}			
	}

	private static void listarCodigo() {
		System.out.println("Codigo  Nome                  Salario  Idade");
		System.out.println("------  --------------------  -------  -----");
		MyIterator<ChaveValor<Integer, Pessoa>> it = pessoasNoRepeat.iterator();
		ChaveValor<Integer, Pessoa> p1 = it.getFirst();
		while(p1 != null){
			System.out.printf("%-6d  %-20s  %-7.2f  %-5d\n", p1.getValor().getCodigo(), p1.getValor().getNome(), p1.getValor().getSalario(), p1.getValor().getIdade());
			p1 = it.getNext();
		}		
	}


	private static void incluirPessoas() {
		int codigo = Keyboard.readInt("Codigo: ");
		String nome = Keyboard.readString("Nome: ");
		Float salario = Keyboard.readFloat("Salario: ");
		int idade = Keyboard.readInt("Idade:" );
		Pessoa pessoa = new Pessoa(codigo, nome, salario, idade);
		ChaveValor<Integer, Pessoa> pessoaCodigo = new ChaveValor<Integer, Pessoa>(codigo, pessoa);
		ChaveValor<Float, Pessoa> pessoaSalario = new ChaveValor<Float, Pessoa>(salario, pessoa);
		ChaveValor<Integer, Pessoa> pessoaIdade = new ChaveValor<Integer, Pessoa>(idade, pessoa);
		if(pessoasNoRepeat.add(pessoaCodigo)){
			pessoasRepeat.add(pessoaSalario);
			filaPrioridade.add(pessoaIdade);		
			System.out.println("Pessoa Adicionada com sucesso!");
		}else{
			System.out.println("Codigo existente!");
		}
		
		
	}

}


