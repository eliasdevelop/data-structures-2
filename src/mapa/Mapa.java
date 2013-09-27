package mapa;

public interface Mapa<K extends Comparable<K>, V> {
	// Armazena no mapa o par (chave, valor). Se a chave já existir no mapa
	// o valor associado à chave deverá ser substituído pelo novo valor.
	// O método o valor antigo associado à chave.
	V put(K chave, V valor);

	// Informa se o mapa contém ou não a chave especificada.
	boolean contains(K chave);

	// Remove do mapa a entrada contendo a chave especificada, retornado o
	// valor associado a ela.
	V remove(K chave);

	// Retorna o valor associado à chave especificada.
	V getValor(K chave);

	// Retornar o par (chave, valor), para a chave especificada
	ChaveValor<K, V> getChaveValor(K chave);

	// Informa o número de entradas armazenadas no mapa.
	int size();

	// Informa se o mapa está vazio ou não.
	boolean isEmpty();

	// Esvazia o mapa.
	void clear();

	// Retorna um iterator do tipo MyIteratorMapa.
	MyIteratorMapa<K, V> iterator();

	// Retorna um array contendo todas as entradas do mapa. O tipo dos
	// elementos do array deverá ser ChaveValor.
	Object[] toArray();

}
