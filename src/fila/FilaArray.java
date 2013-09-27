package fila;

import utilitarios.*;

public class FilaArray<E> extends Fila<E> {
	
	E[] F;
	private int fim;
	private int inicio;
	private int NMaximo;
	
	
	public FilaArray(int tamanhoMaximo){
		F  = (E[]) new Object[tamanhoMaximo];
		this.NMaximo = (tamanhoMaximo);
	}
	
	private class FilaArrayIterator implements MyIterator<E>{
		
		private int indiceCorrente = inicio;

		@Override
		public E getFirst() {
			return F[inicio];
		}

		@Override
		public E getNext() {
			indiceCorrente++;
			 if (indiceCorrente == NMaximo)
				indiceCorrente = 0;
			if (indiceCorrente == fim)
				return null;
			return F[indiceCorrente];
		}

		@Override
		public void remove() {
			if (indiceCorrente == inicio){
				FilaArray.this.remova();
			} else if (indiceCorrente == (NMaximo-1)){
				fim = indiceCorrente;
				F[indiceCorrente] = null;
			} else if (indiceCorrente < fim){
				for (int i = indiceCorrente; i < fim; i++) {
					F[i] = F[i+1];
				}
				fim--;
			} else {
				for (int i = indiceCorrente; i > inicio; i--) {
					F[i] = F[i-1];
				}
				inicio++;
			}
			numItens--;
		}
	}
	
	@Override
	public void clear() {
		inicio = fim = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new FilaArrayIterator();
	}

	@Override
	public boolean insira(E obj) {
		if (numItens==NMaximo)
			return false;
		F[fim] = obj;
		fim = fim + 1;
		if (fim == (NMaximo))
			fim = 0;
		numItens++;
		return true;
	}

	@Override
	public E remova() {
		E obj = F[inicio];
		F[inicio] = null;
		inicio++;
		if (inicio == (NMaximo))
			inicio = 0;
		numItens--;
		return obj;
	}

}
