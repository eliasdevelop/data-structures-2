package pilha;

import utilitarios.MyIterator;
import utilitarios.Vetor;

public class PilhaArray<E> extends Pilha<E> {

	private Vetor<E> pilha;	
	
	public PilhaArray(){
		pilha = new Vetor<E>();
	}
	
	public PilhaArray(int tamInicial){
		pilha = new Vetor<E>(tamInicial);
	}
	
	@Override
	public void clear() {
		pilha.clear();
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return pilha.iterator();
	}

	@Override
	public void empilhe(E obj) {
		pilha.insertAtEnd(obj);
		numItens++;
		
	}

	@Override
	public E desempilhe() {
		if(isEmpty())
			return null;
		numItens--;
		return pilha.removeFromEnd();
	}

	@Override
	public E getTopo() {
		if(isEmpty())
			return null;
		return pilha.elementAt(numItens - 1);
	}

}
