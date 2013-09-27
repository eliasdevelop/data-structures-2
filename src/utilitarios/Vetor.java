package utilitarios;

import java.io.Serializable;

public class Vetor<E> implements Serializable {
	private final int CAPACIDADE_DEFAULT = 100;
	protected E[] lista;
	protected int incremento, capacidadeInicial;
	protected int numItens;

	private class VetorIterador implements MyIterator<E> {

		private int indiceCorrente;

		@Override
		public E getFirst() {
			if (size() == 0)
				return null;
			indiceCorrente = 0;
			return lista[0];
		}

		@Override
		public E getNext() {
			indiceCorrente++;
			if (indiceCorrente >= size())
				return null;
			return lista[indiceCorrente];
		}

		public void remove() {
			removeAt(indiceCorrente);
			indiceCorrente--;
			
		}

	}

	@SuppressWarnings("unchecked")
	public Vetor() {
		lista = (E[]) new Object[CAPACIDADE_DEFAULT];
		incremento = 10;
		capacidadeInicial = CAPACIDADE_DEFAULT;
	}

	@SuppressWarnings("unchecked")
	public Vetor(int capacidadeInicial) {
		lista = (E[]) new Object[capacidadeInicial];
		this.incremento = 10;
		this.capacidadeInicial = capacidadeInicial;
	}

	@SuppressWarnings("unchecked")
	public Vetor(int capacidadeInicial, int incremento) {
		lista = (E[]) new Object[capacidadeInicial];
		this.incremento = incremento;
		this.capacidadeInicial = capacidadeInicial;
	}

	@SuppressWarnings("unchecked")
	protected void redimensione() {
		if (size() == lista.length) {
			E[] novoVetor = (E[]) new Object[size() + incremento];
			System.arraycopy(lista, 0, novoVetor, 0, size());
			lista = novoVetor;
		}
	}
	
	
	public void clear() {
		E[] vetorLimpo = (E[]) new Object[capacidadeInicial];
		lista = vetorLimpo;
	}

	public E elementAt(int indice) {
		if (indice < 0 || indice > lista.length - 1)
			return null;

		return lista[indice];
	}

	public void insertAtEnd(E obj) {
		redimensione();
		lista[numItens++] = obj;
	}

	public void insertAtBegin(E obj) {
		insertAt(0, obj);
	}

	public boolean insertAt(int indice, E obj) {
		if (indice > size() || indice < 0) {
			return false;
		}
		redimensione();
		for (int i = size(); i > indice; i--) {
			lista[i] = lista[i - 1];
		}
		lista[indice] = obj;
		numItens++;
		return true;
	}

	public E removeFromBegin() {
		E removido = removeAt(0);
		return removido;
	}

	public E removeFromEnd() {
		E removido = lista[size() - 1];
		lista[size() - 1] = null;
		numItens--;
		return removido;
	}

	public E removeAt(int indice) {
		if (indice > size() || indice < 0) {
			return null;
		} else {
			E removido = lista[indice];
			for (int i = indice; i < size(); i++) {
				lista[i] = lista[i + 1];
			}
			lista[size()] = null;
			numItens--;
			return removido;
		}
	}

	public boolean replace(int indice, E obj) {
		if (indice > size() || indice < 0) {
			return false;
		}
		lista[indice] = obj;
		return true;
	}

	public boolean isEmpty() {
		return (numItens == 0);
	}

	public int size() {
		return numItens;
	}

	public MyIterator<E> iterator() {
		return new VetorIterador();
	}

}
