package utilitarios;

public interface MyIteratorBack<E> {
	//Retorna o �ltimo elemento da cole��o.
	//Se n�o existir o primeiro elemento, retorna null
	E getLast();
	
	//Retorna elemento anterior da cole��o.
	//Se n�o existir, retorna null
	E getPrior();
	
	//Remove da cole��o o �ltimo elemento retornado
	void remove();
	
}
