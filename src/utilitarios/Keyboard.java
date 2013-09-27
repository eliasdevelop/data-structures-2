package utilitarios;

/** Programado por Raimundo Machado Costa */

import java.util.Scanner;

public class Keyboard{

	public static int readInt(String texto) {
		boolean ok;
		int valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextInt();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static float readFloat(String texto) {
		boolean ok;
		float valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextFloat();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static double readDouble(String texto) {
		boolean ok;
		double valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextDouble();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static long readLong(String texto) {
		boolean ok;
		long valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextLong();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static String readString(String texto) {
		boolean ok;
		String valor = null;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextLine();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static byte readByte(String texto) {
		boolean ok;
		byte valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextByte();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static char readChar(String texto) {
		boolean ok;
		char valor = ' ';

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.next().charAt(0);
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	public static int readShort(String texto) {
		boolean ok;
		short valor = 0;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextShort();
			} catch (Exception exception) {
				System.out.println("Valor invalido");
				ok = false;
			}

		} while (!ok);
		return valor;
	}
	
	public static String readData(String texto){
		boolean ok;
		String valor = null;

		do {
			Scanner input = new Scanner(System.in);
			System.out.print(texto);
			try {
				ok = true;
				valor = input.nextLine();
				MyDate data1 = new MyDate(valor);
			} catch (Exception exception) {
				System.out.println("Data invalida");
				ok = false;
			}

		} while (!ok);
		return valor;
	}

	// -----------------------------------------------------------------
	// Criação de um menu. Cada item do menu deverá estar separado
	// um do outro através de uma barra (/)
	// -----------------------------------------------------------------
	public static int menu(String opcoes) {
		int p;
		String opcao;
		int nOpcoes = 0;
		System.out.println("Opcoes:\n");
		do {
			p = opcoes.indexOf('/');
			if (p == -1) {
				nOpcoes++;
				System.out.println(nOpcoes + " => " + opcoes);
				continue;
			}
			opcao = opcoes.substring(0, p);
			nOpcoes++;
			System.out.println(nOpcoes + " => " + opcao);
			opcoes = opcoes.substring(p + 1);

		} while (p > 0);

		p = readInt("\nEntrar com a opcao: ");

		while ((p < 1) || (p > nOpcoes)) {
			System.out.println("Opcao inválida");
			p = readInt("Entrar com a opcao: ");
		}

		return p;
	}

	public static void waitEnter() {
		byte[] bytes = new byte[160];
		System.out.print("\nPressione Enter para continuar");
		try {
			System.in.read(bytes);
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public static void clrscr() {
		for (int i = 1; i < 100; i++) {
			System.out.println();
		}
	}

}
