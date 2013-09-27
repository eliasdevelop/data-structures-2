package mapa;

public interface MyIteratorMapa<K extends Comparable<K>, V> {
	// Retorna o primeiro elemento do mapa
	// Se n‹o existir o primeiro elemento, retorna null
	ChaveValor<K, V> getFirst();

	// Retorna o proximo elemento do mapa
	// Se n‹o existir o pr—ximo elemento, retorna null
	ChaveValor<K, V> getNext();

	// Remove do mapa o œltimo elemento retornado
	void remove();
}
