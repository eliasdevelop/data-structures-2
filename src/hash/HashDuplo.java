package hash;

import utilitarios.MyIterator;
import colecao.ColecaoComparavel;

public class HashDuplo<E extends Comparable<E>> extends ColecaoComparavel<E> {

	private E[] tabela;
	private String deletado = "";

	public HashDuplo(int tamTabela) {
		if (!isPrime(tamTabela))
			tamTabela = nextPrime(tamTabela);
		tabela = (E[]) new Comparable[tamTabela];
	}

	private class HashDuploIterator implements MyIterator<E> {

		int indCorrente;

		@Override
		public E getFirst() {
			for (int i = 0; i < tabela.length; i++) {
				if (tabela[i] == null || tabela[i] == deletado) {
					indCorrente = i;
				} else {
					indCorrente = i;
					return tabela[i];
				}
			}
			return null;
		}

		@Override
		public E getNext() {
			for (int i = (indCorrente + 1); i < tabela.length; i++) {
				if (tabela[i] == null || tabela[i] == deletado) {
					indCorrente = i;
				} else {
					indCorrente = i;
					return tabela[i];
				}
			}
			return null;
		}

		@Override
		public void remove() {
			tabela[indCorrente] = (E) deletado;
			numItens--;
		}

	}

	private int funcHash(E obj) {
		return obj.hashCode() % tabela.length;
	}

	private int hash2(E obj) {
		int result = 1 + obj.hashCode() % (tabela.length - 1);
		return result;
	}

	private boolean isPrime(int x) {
		int divisor;
		if (x < 4)
			return true;

		if ((x % 2) == 0)
			return false;

		divisor = 3;

		while (!((divisor * divisor > x) || (x % divisor == 0)))
			divisor = divisor + 2;

		if (divisor * divisor > x)
			return true;
		else
			return false;
	}

	// Retorna o próximo número primo maior do que x
	private int nextPrime(int x) {
		if (x < 2)
			return 3;

		if (x % 2 == 0)
			x++;
		else
			x = x + 2;

		while (!isPrime(x))
			x = x + 2;

		return x;

	}

	@Override
	public void clear() {
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = null;			
		}
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new HashDuploIterator();
	}

	@Override
	public boolean remove(E obj) {
		int k = funcHash(obj);
		int inicio = k;
		int incremento = hash2(obj);
		while ((tabela[k] != null) && (tabela[k] != deletado)) {
			if (tabela[k].compareTo(obj) == 0) {
				tabela[k] = (E) deletado;
				numItens--;
				return true;
			}
			k = (k + incremento) % tabela.length;
		}

		while (tabela[k] != null) {
			if (k == inicio)
				break;
			if ((tabela[k] != deletado) && (tabela[k].compareTo(obj) == 0)) {
				tabela[k] = (E) deletado;
				numItens--;
				return true;
			}
			k = (k + incremento) % tabela.length;
		}
		return false;		
	}

	@Override
	public E retrieve(E obj) {
		int k = funcHash(obj);
		int inicio = k;
		int incremento = hash2(obj);
		while ((tabela[k] != null) && (tabela[k] != deletado)) {
			if (tabela[k].compareTo(obj) == 0) {
				return tabela[k];
			}
			k = (k + incremento) % tabela.length;
		}

		while (tabela[k] != null) {
			if (k == inicio)
				break;
			if ((tabela[k] != deletado) && (tabela[k].compareTo(obj) == 0)) {
				return tabela[k];
			}
			k = (k + incremento) % tabela.length;
		}
		return null;
	}

	@Override
	public boolean add(E obj) {
		if ((float) numItens / tabela.length >= 0.7f)
			redimensione();
		int k = funcHash(obj);
		int inicio = k;
		int incremento = hash2(obj);
		while ((tabela[k] != null) && (tabela[k] != deletado)) {
			if (tabela[k].compareTo(obj) == 0) {
				return false;
			}
			k = (k + incremento) % tabela.length;
		}

		int j = k;

		while (tabela[k] != null) {
			if (k == inicio)
				break;
			if ((tabela[k] != deletado) && (tabela[k].compareTo(obj) == 0)) {
				return false;
			}
			k = (k + incremento) % tabela.length;
		}

		tabela[j] = obj;
		numItens++;
		return true;
	}

	private void redimensione() {
		E[] novaTabela = tabela;
		int numItensAux = numItens;
		int tamTabela = tabela.length;
		tamTabela = tamTabela + (tabela.length / 2);
		tabela = (E[]) new Comparable[tamTabela];
		for (int i = 0; i < novaTabela.length; i++) {
			if ((novaTabela[i] != deletado) && (novaTabela[i] != null)) {
				add(novaTabela[i]);
			}
		}
		numItens = numItensAux;
	}

	public void imprimirTabela() {
		System.out.println("Indice  CodigoHash");
		System.out.println("------  ----------");
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == null){
				System.out.printf("%6d  %10s\n", i, "");
			} else if (tabela[i] == deletado) {
				System.out.printf("%6d  %10s\n", i, "Deletado");
			} else {
				System.out.printf("%6d  %10d\n", i, tabela[i].hashCode());
			}
		}
	}

}
