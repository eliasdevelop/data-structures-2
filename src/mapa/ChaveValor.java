package mapa;

import java.io.Serializable;

public class ChaveValor<K extends Comparable<K>, V> implements
		Comparable<ChaveValor<K, V>>, Serializable {

	private K chave;
	private V valor;

	public ChaveValor(K chave, V valor) {
		super();
		this.chave = chave;
		this.valor = valor;
	}

	public K getChave() {
		return chave;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		return chave.hashCode();
	}

	@Override
	public int compareTo(ChaveValor<K, V> o) {
		return chave.compareTo(o.getChave());
	}

}
