package hash;

import utilitarios.MyIterator;
import listasEncadeadas.NoSimpEnc;
import colecao.ColecaoComparavel;

public class HashEnc1<E extends Comparable<E>> extends ColecaoComparavel<E> {

	private NoSimpEnc<E>[] tabela;
	private float fatorDeCarga = 1.75f;

	private class HashIterator implements MyIterator<E> {

		int indCorrente = 0;
		NoSimpEnc<E> noAtual;
		NoSimpEnc<E> noAnt;

		@Override
		public E getFirst() {
			for (int i = indCorrente; i < tabela.length; i++) {
				if (tabela[i] != null) {
					indCorrente = i;
					noAtual = tabela[i];
					noAnt = noAtual;
					return noAtual.getObj();
				}
			}
			return null;
		}

		@Override
		public E getNext() {
			if (noAtual.getProx() == null) {
				indCorrente++;
				return getFirst();
			} else {
				noAnt = noAtual;
				noAtual = noAnt.getProx();
				return noAtual.getObj();
			}
		}

		@Override
		public void remove() {
			if(noAnt == noAtual){
				tabela[indCorrente] = noAnt.getProx();
				numItens--;
			}else{
				noAnt.setProx(noAtual.getProx());
				numItens--;
			}
		}
	}

	protected int funcHash(Object obj) {
		return Math.abs(obj.hashCode()) % tabela.length;
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

	public HashEnc1(int tamTabela) {
		if (!isPrime(tamTabela))
			tamTabela = nextPrime(tamTabela);
		tabela = new NoSimpEnc[tamTabela];
	}

	private void redimensione() {
		int novoTamanho = tabela.length * 2;
		if (!isPrime(novoTamanho))
			novoTamanho = nextPrime(novoTamanho);
		HashEnc1<E> novaTabela = new HashEnc1<E>(novoTamanho);
		MyIterator<E> it = iterator();
		E obj = it.getFirst();
		while (obj != null) {
			novaTabela.add(obj);
			obj = it.getNext();
		}
		tabela = novaTabela.tabela;
		numItens = novaTabela.numItens;
	}

	@Override
	public boolean add(E obj) {
		if (numItens >= fatorDeCarga * tabela.length)
			redimensione();
		int k = funcHash(obj);
		if (tabela[k] != null) {
			NoSimpEnc<E> prox = tabela[k];
			NoSimpEnc<E> ant = prox;
			while (prox != null) {
				if (prox.getObj().compareTo(obj) == 0) {
					return false;
				} else {
					ant = prox;
					prox = prox.getProx();
				}
			}
			NoSimpEnc<E> objeto = new NoSimpEnc<E>(obj);
			ant.setProx(objeto);
			numItens++;
			return true;
		} else {
			NoSimpEnc<E> objeto = new NoSimpEnc<E>(obj);
			tabela[k] = objeto;
			numItens++;
			return true;
		}

	}

	@Override
	public E retrieve(E obj) {
		int k = funcHash(obj);
		if (tabela[k] != null) {
			NoSimpEnc<E> prox = tabela[k];
			NoSimpEnc<E> ant = prox;
			while (prox != null) {
				if (prox.getObj().compareTo(obj) == 0) {
					return prox.getObj();
				} else {
					ant = prox;
					prox = prox.getProx();
				}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public boolean remove(E obj) {
		int k = funcHash(obj);
		if (tabela[k] != null) {
			NoSimpEnc<E> prox = tabela[k];
			NoSimpEnc<E> ant = prox;
			while (prox != null) {
				if (prox.getObj().compareTo(obj) == 0) {
					if (ant == prox) {
						tabela[k] = ant.getProx();
					} else {
						ant.setProx(prox.getProx());
					}
					numItens--;
					return true;
				} else {
					ant = prox;
					prox = ant.getProx();
				}
			}
			return false;
		} else {
			return false;
		}
	}

	public void imprimaTabela() {
		System.out.println("Indice  CodigosHash");
		System.out.println("------  ------------------------------");
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] == null) {
				System.out.printf("%6d  %-30s\n", i, "");
			} else {
				String hashs = "";
				NoSimpEnc<E> prox = tabela[i];
				NoSimpEnc<E> ant = prox;
				while (prox != null) {
					hashs = hashs + " [" + prox.getObj().hashCode() + "]";
					ant = prox;
					prox = prox.getProx();
				}
				System.out.printf("%6d  %30s\n", i, hashs);
			}
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = new NoSimpEnc<E>(null);
		}
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new HashIterator();
	}

}
