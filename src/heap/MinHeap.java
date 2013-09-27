package heap;

import utilitarios.MyIterator;

@SuppressWarnings("serial")
public class MinHeap<E extends Comparable<E>> extends Heap<E>{

	public MinHeap() {
		super();
	}
	public MinHeap(int capInicial) {
		super(capInicial);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void borbulheParaBaixo(int indice) {
		
		int menorFilho;
		int filhoDireito, filhoEsquerdo;
		Comparable infoTemp;
		
		infoTemp = heapArray[indice];
		
		while ((2*indice + 2) <= size()){
			filhoEsquerdo = 2* indice + 1;
			filhoDireito = filhoEsquerdo + 1;
			if(filhoDireito <= (size() - 1) && 
					(heapArray[filhoDireito]).compareTo(heapArray[filhoEsquerdo]) < 0)
				menorFilho = filhoDireito;
			else
				menorFilho = filhoEsquerdo;
			
			if(infoTemp.compareTo(heapArray[menorFilho]) < 0)
				break;
			
			heapArray[indice] = heapArray[menorFilho];
			indice = menorFilho;		
		}
		heapArray[indice] = infoTemp;		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void borbulheParaCima(int indice) {
		int indicePai;
		Comparable infoTemp;
		indicePai = (indice - 1) / 2;
		infoTemp = heapArray[indice];
		while((indice > 0) && (heapArray[indicePai].compareTo(infoTemp) > 0)) {
			heapArray[indice] = heapArray[indicePai];
			indice = indicePai;
			indicePai = (indice - 1) / 2;
		}
		heapArray[indice] = infoTemp;
	}
}
