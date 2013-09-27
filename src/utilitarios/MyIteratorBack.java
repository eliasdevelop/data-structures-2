package utilitarios;

public interface MyIteratorBack<E> {
	//Retorna o último elemento da coleção.
	//Se não existir o primeiro elemento, retorna null
	E getLast();
	
	//Retorna elemento anterior da coleção.
	//Se não existir, retorna null
	E getPrior();
	
	//Remove da coleção o último elemento retornado
	void remove();
	
}
