package mapa;

import java.io.Serializable;

public abstract class MapaAbstrata<K extends Comparable<K>, V> implements
      Mapa<K, V>, Serializable {

	protected int numItens;

	@Override
	public int size() {
		return numItens;
	}

	@Override
	public boolean isEmpty() {
		return (numItens == 0);
	}

	@Override
	public Object[] toArray() {
		if (isEmpty())
			return null;
		Object[] objs = new Object[numItens];
		MyIteratorMapa<K, V> it = iterator();
		int i = -1;
		Object chaveValor = it.getFirst();
		while (chaveValor != null) {
			i++;
			objs[i] = chaveValor;
			chaveValor = it.getNext();
		}
		return objs;
	}

}
