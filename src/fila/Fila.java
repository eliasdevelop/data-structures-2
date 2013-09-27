package fila;

import colecao.ColecaoNaoComparavel;

public abstract class Fila<E> extends ColecaoNaoComparavel<E> {
	
	public abstract boolean insira(E obj);
	
	public abstract E remova();

}
