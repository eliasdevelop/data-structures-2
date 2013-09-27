package listasEncadeadas;

import utilitarios.MyIterator;
import utilitarios.MyIteratorBack;

public class ListaDupEnc<E> {
	
	protected NoDupEnc<E> noCabeca;
	protected int numItens;

	private class ListaDupEncIteratorBack implements MyIteratorBack<E>{

		NoDupEnc<E> p;
		NoDupEnc<E> pAnt;
		NoDupEnc<E> pPos;
		int noCorrente;
		
		@Override
		public E getLast() {
			if (size() == 0) {
				return null;
			} else {
				p = noCabeca.getAnt();
				noCorrente = numItens;
				return p.getObj();
			}
		}

		@Override
		public E getPrior() {
			noCorrente--;
			if (noCorrente == 0)
				return null;
			pPos = p;
			p = pPos.getProx();
			pAnt = pPos.getProx().getProx();
			return p.getObj();
		}

		@Override
		public void remove() {
			p.remove();
			numItens--;
			noCorrente--;			
		}
		
	}
	
	private class ListaDupEncIterator implements MyIterator<E> {

		NoDupEnc<E> p;
		NoDupEnc<E> pAnt;
		NoDupEnc<E> pPos;
		int noCorrente;

		@Override
		public E getFirst() {
			if (size() == 0) {
				return null;
			} else {
				p = noCabeca.getProx();
				noCorrente = 0;
				return p.getObj();
			}
		}

		@Override
		public E getNext() {
			noCorrente++;
			if (noCorrente == size())
				return null;
			pAnt = p;
			p = pAnt.getProx();
			pPos = pAnt.getProx().getProx();
			return p.getObj();
		}

		@Override
		public void remove() {
			p.remove();
			numItens--;
			noCorrente--;
		}
	}

	public ListaDupEnc() {
		noCabeca = new NoDupEnc<E>();
	}

	public NoDupEnc<E> getNoCabeca() {
		return noCabeca;
	}

	public void clear() {
		noCabeca.setAnt(null);
		noCabeca.setProx(null);
		numItens = 0;
	}

	public boolean isEmpty() {
		return (numItens == 0);
	}

	public void insertAtBegin(E obj) {
		NoDupEnc<E> novoNo = new NoDupEnc<E>(obj,null,null);
		if (noCabeca.getProx() == null) {
			noCabeca.setProx(novoNo);
			novoNo.setProx(noCabeca);
			novoNo.setAnt(noCabeca);
		} else {
			novoNo.setProx(noCabeca.getProx());
			novoNo.setAnt(noCabeca);
			noCabeca.setProx(novoNo);			
		}
		numItens++;
	}

	public void insertAtEnd(E obj) {
		NoDupEnc<E> novoNo = new NoDupEnc<E>(obj,null,null);
		if (noCabeca.getProx() == null) {
			noCabeca.setProx(novoNo);
			novoNo.setProx(noCabeca);
			novoNo.setAnt(noCabeca);
		} else {
			novoNo.setProx(noCabeca);
			novoNo.setAnt(noCabeca.getAnt());
			noCabeca.setAnt(novoNo);
		}
		numItens++;

	}

	public void insertAfter(E obj, NoDupEnc<E> p) {
		NoDupEnc<E> novoNo = new NoDupEnc<E>(obj, p, null);
		if (p == noCabeca.getAnt())
			novoNo.setProx(noCabeca);
		    noCabeca.setAnt(novoNo);
		novoNo.setProx(p.getProx());
		p.setProx(novoNo);
		numItens++;
	}

	public void insertBefore(E obj, NoDupEnc<E> p) {
		NoDupEnc<E> novoNo = new NoDupEnc<E>(obj, null, p);
		if (p == noCabeca.getProx())
			novoNo.setAnt(noCabeca);
		    noCabeca.setProx(novoNo);
		novoNo.setAnt(p.getAnt());
		p.setAnt(novoNo);
		numItens++;
	}

	public E removeFromBegin() {
		if (size() == 0) {
			return null;
		} else {
			E valorInicio = noCabeca.getProx().getObj();
			noCabeca.setProx(noCabeca.getProx().getProx());
			if (noCabeca.getProx() == null)
				noCabeca.setAnt(null);
			numItens--;
			return valorInicio;
		}
	}

	public E remove(NoDupEnc<E> p) {
		E removido = null;
		MyIterator<E> buscar = iterator();
		E buscador = buscar.getFirst();
		while(buscador != null){
			if(buscador == p){
				removido = buscador;
				buscar.remove();
			}
			buscador = buscar.getNext();
		}
		return removido;
	}

	public E removeFromEnd() {
		if (size() == 0) {
			return null;
		} else {
			E valorFinal = noCabeca.getAnt().getObj();
			noCabeca.setAnt(noCabeca.getAnt().getAnt());
			if (noCabeca.getAnt() == null)
				noCabeca.setProx(null);
			numItens--;
			return valorFinal;
		}
	}

	public int size() {
		return numItens;
	}

	public MyIterator<E> iterator() {
		return new ListaDupEncIterator();
	}
	
	public MyIteratorBack<E> iteratorBack(){
		return new ListaDupEncIteratorBack();
	}
}
