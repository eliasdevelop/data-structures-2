package lista06;

import utilitarios.MyIterator;
import heap.MaxHeap;
import heap.MinHeap;

public class TesteHeap {
	
	public static void main(String[] args) {
		
		System.out.println("Min Heap");
		MinHeap<Integer> heap = new MinHeap<Integer>();
		heap.add(9);
		heap.add(15);
		heap.add(6);
		heap.add(7);
		heap.add(1);

		System.out.println("Iterator");
		MyIterator<Integer> ite = heap.iterator();
		Integer temporario = ite.getFirst();
		while (temporario != null) {
			System.out.println(temporario);
			temporario = ite.getNext();
		}

		System.out.println("Remoção");
		Integer numero = heap.remove();
		while (numero != null) {
			System.out.println(numero);
			numero = heap.remove();
		}
		System.out.println("_______________________________________________________________________");
		System.out.println("Max Heap");
		MaxHeap<Integer> h = new MaxHeap<Integer>();
		h.add(9);
		h.add(15);
		h.add(6);
		h.add(7);
		h.add(1);

		System.out.println("Iterator");
		MyIterator<Integer> it = h.iterator();
		Integer temp = it.getFirst();
		while (temp != null) {
			System.out.println(temp);
			temp = it.getNext();
		}

		System.out.println("Remoção");
		Integer a = h.remove();
		while (a != null) {
			System.out.println(a);
			a = h.remove();
		}
	}
}


