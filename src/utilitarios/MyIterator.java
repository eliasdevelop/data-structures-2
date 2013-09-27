package utilitarios;


public interface MyIterator<E>{
	//Retorna o primeiro elemento da coleção.
	//Se não existir o primeiro elemento, retorna null.
	E getFirst();
	
	//Retorna o próximo elemento da coleção.
	//Se não existir o próximo elemento, retorna null.
	E getNext();
	
	//Remove da coleção o último elemento retornado
	void remove();
	
}
