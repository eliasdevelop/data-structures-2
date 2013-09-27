package listasSequenciais;

public class ListaSeqNaoOrd<E extends Comparable<E>> extends ListaSeq<E> {

	public ListaSeqNaoOrd(int tamInicial) {
		super(tamInicial);
	}

	@Override
	protected int procuraPosicao(E obj) {
		for (int i = 0; i < size(); i++) {
			if (obj.compareTo(lista.elementAt(i)) == 0)
				return i;
		}
		return -1;
	}

	@Override
	public boolean add(E obj) {
		lista.insertAtEnd(obj);
		numItens++;
		return true;
	}

}
