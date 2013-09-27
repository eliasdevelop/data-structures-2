package listasLinearesEncadeadas;

public class ListaEncNaoOrd<E extends Comparable<E>> extends ListaEnc<E> {

	@Override
	public boolean add(E obj) {
		lista.insertAtEnd(obj);
		numItens++;
		return true;
	}


}
