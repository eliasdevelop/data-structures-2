package listasEncadeadas;

import java.io.Serializable;

import utilitarios.MyIterator;
import colecao.ColecaoComparavel;

public class SkipList<E extends Comparable<E>> extends ColecaoComparavel<E> implements Serializable {
	
	static int nivelMaximo;
	static int nivelAtual;
	protected SkipNode<E> noCabeca;
	
	@SuppressWarnings("unused")
	private class SkipListIterator implements MyIterator<E>{
			
		SkipNode<E> p;
		SkipNode<E> pAnt;
		
		@Override
		public E getFirst() {
			if (noCabeca.prox[0] == null) {
				return null;
			} else {
				p = noCabeca.prox[0];
				return p.obj;
			}
		}

		@Override
		public E getNext() {
			if (p.prox[0] == null)
				return null;
			pAnt = p;
			p = pAnt.prox[0];
			return p.obj;
		}

		@Override
		public void remove() {
			SkipList.this.remove(p.obj);			
		}		
	}
	
	private class SkipNode<E> implements Serializable {
		public E obj;
		public SkipNode<E>[] prox;

		public SkipNode(int nivel, E obj) {
			prox = new SkipNode[nivel + 1];
			this.obj = obj;
		}
	}

	public SkipList(int nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
		noCabeca = new SkipNode<E>(nivelMaximo, null);
		nivelAtual = 0;
	}
	
	static int calculaNivel() {
		int nivel = 0;
		while (nivel < nivelMaximo && Math.random() < 0.5 && nivel <= nivelAtual) {
			nivel++;
		}
		return nivel;
	}

	@Override
	public void clear() {
		noCabeca = new SkipNode<E>(nivelMaximo, null);
		nivelAtual = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new SkipListIterator();
	}

	@Override
	public boolean remove(E obj) {
		SkipNode<E> p = noCabeca;
		SkipNode<E>[] pAnt = new SkipNode[nivelMaximo + 1];
		int nivel = -1;
		
		for (int i = nivelAtual; i >= 0; i--) {
			while (p.prox[i] != null && p.prox[i].obj.compareTo(obj) < 0) {
				p = p.prox[i];
			}
			if(p.prox[i].obj.compareTo(obj) == 0){
				nivel++;
			}
			pAnt[i] = p;
		}
		p = p.prox[0];	

		if (p != null || (p.obj.compareTo(obj) == 0)) {			
			for (int i = 0; i <= nivel; i++) {
				pAnt[i].prox[i] = p.prox[i];
			}
			numItens--;
			return true;
		}
		return false;		
	}

	@Override
	public E retrieve(E obj) {
		SkipNode<E> p = noCabeca;

		for (int i = nivelAtual; i >= 0; i--) {
			while (p.prox[i] != null && p.prox[i].obj.compareTo(obj) < 0) {				
				p = p.prox[i];
			}
		}
		p = p.prox[0];
		
		if (p != null && (p.obj.compareTo(obj) == 0)) {
			return p.obj;
		}
		return null;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(E obj) {
		SkipNode<E> p = noCabeca;
		SkipNode<E>[] pAnt = new SkipNode[nivelMaximo + 1];

		for (int i = nivelAtual; i >= 0; i--) {
			while (p.prox[i] != null && p.prox[i].obj.compareTo(obj) < 0) {
				p = p.prox[i];
			}
			pAnt[i] = p;
		}
		p = p.prox[0];

		int nivel;

		if (p == null || !(p.obj.compareTo(obj) == 0)) {
			nivel = calculaNivel();
			p = new SkipNode<E>(nivel, obj);

			if (nivel > nivelAtual) {
				pAnt[nivel] = noCabeca;
				nivelAtual = nivel;
			}

			for (int i = 0; i <= nivel; i++) {
				p.prox[i] = pAnt[i].prox[i];
				pAnt[i].prox[i] = p;
			}
			numItens++;
			return true;
		}
		return false;		
	}

}
