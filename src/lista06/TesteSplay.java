package lista06;

import arvore.ArvoreSplay;

public class TesteSplay {
	public static void main(String[] args) {
		ArvoreSplay<Integer> teste = new ArvoreSplay<Integer>();
		System.out.println("");
		System.out.println("");
		System.out.println("Inicio");
		System.out.println("");
		System.out.println("");
		teste.add(30);
		teste.add(40);
		teste.add(5);
		teste.add(10);
		teste.desenhe(80);
		System.out.println("");
		System.out.println("");
		System.out.println("Procurar 50 não encontrado");
		System.out.println("");
		System.out.println("");
		teste.retrieve(50);
		teste.desenhe(80);
		System.out.println("");
		System.out.println("");
		System.out.println("Adicionado alguns numeros");
		System.out.println("");
		System.out.println("");
		teste.add(100);
		teste.add(45);
		teste.add(55);
		teste.add(13);
		teste.desenhe(80);
		
	}
}
