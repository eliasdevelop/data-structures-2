package listasSequenciais;

public class ListaSeqOrd<E extends Comparable<E>> extends ListaSeq<E> {

	public ListaSeqOrd(int tamInicial) {
		super(tamInicial);
	}

	@Override
	protected int procuraPosicao(E obj) {
		int inicio, fim, meio, c;
		inicio = 0;
		fim = size() - 1;

		while (inicio <= fim) {
			meio = (inicio + fim) / 2;
			c = obj.compareTo(lista.elementAt(meio));
			if (c == 0)
				return meio;
			if (c > 0)
				inicio = meio + 1;
			else
				fim = meio - 1;
		}
		return -1;
	}
	
	private int busque(E obj) {
		int inicio, fim, meio, c;
		inicio = 0;
		fim = size() - 1;

		while (inicio <= fim) {
			meio = (inicio + fim) / 2;
			c = obj.compareTo(lista.elementAt(meio));
			if (c > 0)
				inicio = meio + 1;
			else
				fim = meio - 1;
		}
		return inicio;
	}	

	@Override
	public boolean add(E obj) {
		lista.insertAt(busque(obj), obj);
		numItens++;
		return true;
	}

}
