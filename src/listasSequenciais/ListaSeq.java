package listasSequenciais;

import utilitarios.MyIterator;
import utilitarios.Vetor;
import colecao.ColecaoComparavel;

public abstract class ListaSeq<E extends Comparable<E>> extends ColecaoComparavel<E> {

	protected Vetor<E> lista;

	public ListaSeq(int tamInicial) {
		lista = new Vetor<E>(tamInicial);
	}

	protected abstract int procuraPosicao(E obj);

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
		int indice = procuraPosicao(obj);
		if (indice == -1)
			return false;
		lista.removeAt(indice);
		numItens--;
		return true;
	}

	@Override
	public E retrieve(E obj) {
		int indice = procuraPosicao(obj);
		if (indice == -1)
			return null;
		return lista.elementAt(indice);
	}

}
