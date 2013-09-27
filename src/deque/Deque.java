package deque;

import utilitarios.MyIterator;
import utilitarios.MyIteratorBack;
import listasEncadeadas.ListaDupEnc;
import colecao.ColecaoNaoComparavel;

public class Deque<E> extends ColecaoNaoComparavel<E> {
	
	private ListaDupEnc<E> deque = new ListaDupEnc<E>();

	public void insertBegin(E obj) {
		deque.insertAtBegin(obj);
	}

	public void insertEnd(E obj) {
		deque.insertAtEnd(obj);
	}

	public E removeEnd() {
		return deque.removeFromEnd();
	}

	public E removeBegin() {
		return deque.removeFromBegin();
	}

	@Override
	public void clear() {
		deque.clear();
	}

	@Override
	public MyIterator<E> iterator() {
		return deque.iterator();
	}

	public MyIteratorBack<E> iteratorBack() {
		return deque.iteratorBack();
	}

}
