package fila;

import listasEncadeadas.ListaSimpEnc;
import utilitarios.MyIterator;

public class FilaEnc<E> extends Fila<E> {

	private ListaSimpEnc<E> fila = new ListaSimpEnc<E>();

	@Override
	public void clear() {
		fila.clear();
	}

	@Override
	public MyIterator<E> iterator() {
		return fila.iterator();
	}

	@Override
	public boolean insira(E obj) {
		fila.insertAtEnd(obj);
		numItens++;
		return true;
	}

	@Override
	public E remova() {
		if (fila.size() == 0)
			return null;
		numItens--;
		return fila.removeFromBegin();
	}

}
