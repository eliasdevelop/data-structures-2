package listasEncadeadas;

import java.io.Serializable;

/* Referência: Data Structures and Algorithms in Java
 * Peter Drake
 */
public class NoSimpEnc<E> implements Serializable{
	private E obj;
	private NoSimpEnc<E> prox;

	//Cria um nó com o campo prox igual a null
	public NoSimpEnc(E obj) {
		this.obj = obj;
		this.prox = null;
	}

	//Cria um nó com uma referência ao próximo nó
	public NoSimpEnc(E obj, NoSimpEnc<E> prox) {
		this.obj = obj;
		this.prox = prox;
	}

	//Retorna o objeto armazenado no nó
	public E getObj() {
		return obj;
	}

	//Retorna a referência ao próximo nó
	public NoSimpEnc<E> getProx() {
		return prox;
	}

	//Altera a referência do objeto armazenado no nó
	public void setObj(E obj) {
		this.obj = obj;
	}

	//Altera a referência ao próximo nó
	public void setProx(NoSimpEnc<E> prox) {
		this.prox = prox;
	}

}
