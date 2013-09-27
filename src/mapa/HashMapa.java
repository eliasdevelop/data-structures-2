package mapa;

import hash.HashEnc;

import java.io.Serializable;

import utilitarios.MyIterator;

public class HashMapa<K extends Comparable<K>, V> extends MapaAbstrata<K, V> implements Serializable {

	private HashEnc<ChaveValor<K, V>> mapa;
	
	public HashMapa() {
		mapa = new HashEnc<ChaveValor<K,V>>(10);
	}

	private ChaveValor<K, V> busca(K chave) {
		MyIterator<ChaveValor<K, V>> it = mapa.iterator();
		ChaveValor<K, V> temp = it.getFirst();

		while (temp != null) {
			if (temp.getChave().compareTo(chave) == 0) {
				return temp;
			}
			temp = it.getNext();
		}
		return null;
	}

	@Override
	public V put(K chave, V valor) {
		if (contains(chave))
			return null;
		mapa.add(new ChaveValor<K, V>(chave, valor));
		numItens++;
		return valor;
	}

	@Override
	public boolean contains(K chave) {
		return mapa.contains(new ChaveValor<K, V>(chave, null));

	}

	@Override
	public V remove(K chave) {
		ChaveValor<K, V> temp = busca(chave);
		if (temp != null) {
			mapa.remove(temp);
			numItens--;
			return temp.getValor();
		}
		return null;
	}

	@Override
	public V getValor(K chave) {
		ChaveValor<K, V> temp = busca(chave);
		if (temp == null)
			return null;
		return temp.getValor();
	}

	@Override
	public ChaveValor<K, V> getChaveValor(K chave) {
		return busca(chave);
	}

	@Override
	public void clear() {
		mapa.clear();
	}

	private class Iterator implements MyIteratorMapa<K, V> {
		MyIterator<ChaveValor<K, V>> it = mapa.iterator();
		ChaveValor<K, V> obj;

		@Override
		public ChaveValor<K, V> getFirst() {
			obj = it.getFirst();
			return obj;
		}

		@Override
		public ChaveValor<K, V> getNext() {
			obj = it.getNext();
			return obj;
		}

		@Override
		public void remove() {
			it.remove();
			numItens--;

		}
	}

	@Override
	public MyIteratorMapa<K, V> iterator() {
		return new Iterator();
	}

}
