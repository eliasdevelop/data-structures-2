package listasLinearesEncadeadas;

import listasEncadeadas.ListaSimpEnc;
import utilitarios.MyIterator;
import colecao.ColecaoComparavel;

public abstract class ListaEnc<E extends Comparable<E>> extends
		ColecaoComparavel<E> {

	protected ListaSimpEnc<E> lista;

	public ListaEnc() {
		lista = new ListaSimpEnc<E>();
	}

	@Override
	public void clear() {
		lista.clear();
	}

	@Override
	public MyIterator<E> iterator() {
		return lista.iterator();
	}

	@Override
	public boolean remove(E obj) {
		MyIterator<E> it = iterator();
		E busca = it.getFirst();
		while (busca != null) {
			if (busca.compareTo(obj) == 0) {
				it.remove();
				numItens--;
				return true;
			} else {
				busca = it.getNext();
			}
		}
		return false;
	}

	@Override
	public E retrieve(E obj) {
		MyIterator<E> it = iterator();
		E busca = it.getFirst();
		while (busca != null) {
			if (busca.compareTo(obj) == 0){
				return busca;
			}	
			busca = it.getNext();
		}
		return null;
	}

	@Override
	public abstract boolean add(E obj);

}
