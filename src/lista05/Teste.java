package lista05;

import arvore.ArvoreRedBlack;

public class Teste {
	public static void main(String[] args) {
		ArvoreRedBlack<Integer> teste = new ArvoreRedBlack<Integer>(false);
		teste.add(30);
		teste.add(40);
		teste.add(50);
		teste.add(80);
		teste.add(20);
		teste.add(10);
		teste.add(90);
		teste.add(100);
		teste.add(70);
		teste.add(60);
		teste.desenhe(80);
		System.out.println("");
		System.out.println("----------------------------------------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------------------------------");
		System.out.println("");
		teste.remove(30);
		teste.remove(20);
		teste.remove(70);
		teste.remove(80);
		teste.desenhe(80);
	}
}
