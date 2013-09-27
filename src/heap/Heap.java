package heap;

import utilitarios.MyIterator;
import colecao.ColecaoComparavel;

@SuppressWarnings("serial")
public abstract class Heap <E extends Comparable<E>> extends ColecaoComparavel<E> {

	@SuppressWarnings("rawtypes")
	protected Comparable[] heapArray;
	
	public Heap() {
		heapArray = new Comparable[10];
	}
	
	public Heap(int capInicial) {
		heapArray = new Comparable[capInicial];
	}
		
	@Override
	public void clear() {
		heapArray = new Comparable[heapArray.length];
		numItens = 0;
	}
	
	
	@Override
	public boolean add(E obj) {
		redimensione();		
		heapArray[numItens] = obj;
		borbulheParaCima(numItens);
		numItens++;
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public E remove() {
		if(size() > 0){
		Comparable removido = heapArray[0];
		
		numItens--;
		heapArray[0] = heapArray[numItens];
		heapArray[numItens] = null;		
		
		borbulheParaBaixo(0);
		return (E) removido;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected void redimensione() {
		if (size() == heapArray.length) {
			E[] novoVetor = (E[]) new Object[size() + 10];
			System.arraycopy(heapArray, 0, novoVetor, 0, size());
			heapArray = (E[]) novoVetor;
		}
	}
	protected abstract void borbulheParaBaixo(int indice);
	protected abstract void borbulheParaCima(int indice);
	
	private class HeapIterator implements MyIterator<E> {
		private Heap<E> heap;
		@SuppressWarnings("rawtypes")
		private Comparable removido;

		@SuppressWarnings("unchecked")
		@Override
		public E getFirst() {
			if (Heap.this instanceof MaxHeap)
				heap = new MaxHeap<E>(numItens);
			else
				heap = new MinHeap<E>(numItens);

			System.arraycopy(heapArray, 0, heap.heapArray, 0, numItens);
			heap.numItens = numItens;

			removido = heap.remove();
			return (E) removido;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E getNext() {
			removido = heap.remove();
			return (E) removido;
		}

		@SuppressWarnings({ "unchecked"})
		@Override
		public void remove() {
			int pos = 0;
			boolean achou = false;
			for (pos = 0; pos < heapArray.length; pos++) {
				if (removido.compareTo(heapArray[pos]) == 0) {
					achou = true;
					break;
				}
			}

			if (achou) {
				numItens--;
				heapArray[pos] = heapArray[numItens];
				heapArray[numItens] = null;

				borbulheParaBaixo(pos);
			}
		}
	}

	@Override
	public MyIterator<E> iterator() {
		return new HeapIterator();
	}
	
	
	//SEM USO
	@Override
	public boolean remove(E obj) {
		System.out.println("Não suportado");
		return false;
    }
    @Override
	public E retrieve(E obj) {
    	System.out.println("Não suportado");
    	return null;
	}
    @Override
	public boolean contains(E obj) {
		System.out.println("Não suportado");
		return false;
	}

}
