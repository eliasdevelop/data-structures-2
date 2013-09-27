package pilha;

import listasEncadeadas.ListaSimpEnc;
import utilitarios.MyIterator;

public class PilhaEnc<E> extends Pilha<E> {

	private ListaSimpEnc<E> lista = new ListaSimpEnc<E>();

	@Override
	public void clear() {
		lista.clear();
	}

	@Override
	public MyIterator<E> iterator() {
		return lista.iterator();
	}

	@Override
	public void empilhe(E obj) {
		lista.insertAtBegin(obj);
	}

	@Override
	public E desempilhe() {
		if (lista.isEmpty())
			return null;
		return lista.removeFromBegin();
	}

	@Override
	public E getTopo() {
		return lista.getInicio().getObj();
	}

}
