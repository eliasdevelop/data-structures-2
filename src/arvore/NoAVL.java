package arvore;

import java.io.Serializable;

public class NoAVL<E> implements Serializable{
	
	/** Contém o objeto associado a esse nó. */
	private E obj;
 
	/** Referência à subárvore esquerda. */
	private NoAVL<E> esq;

	/** referência a subárvore direita. */
	private NoAVL<E> dir;
	
	private int altura;
	private boolean deletado;

	public boolean isDeletado() {
   	return deletado;
   }

	public void setDeletado(boolean deletado) {
   	this.deletado = deletado;
   }

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	/** Cria um nó com filhos nulos (folha) */
	public NoAVL(E obj) {
		this.obj = obj;
		this.esq = null;
		this.dir = null;
		this.deletado = false;
		this.altura = 0;
	}

	/** Cria um nó com as respectivas subárvores esquerda e direita. */
	public NoAVL(E obj, NoAVL<E> esq, NoAVL<E> dir) {
		this.obj = obj;
		this.esq = esq;
		this.dir = dir;
		this.deletado = false;
		this.altura = 0;
	}

	/** Retorna o objeto associado a esse nó. */
	public E getObj() {
		return obj;
	}

	/** Retorna a referência da subárvore esquerda. */
	public NoAVL<E> getEsq() {
		return esq;
	}

	/** Retorna a referência da subárvore direita. */
	public NoAVL<E> getDir() {
		return dir;
	}

	/** Retorna true se o nó é uma folha. */
	public boolean ehFolha() {
		return (esq == null) && (dir == null);
	}

	/** Substitui o objeto armazenado no nó por obj. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Substitui a referência da subárvore esquerda por esq. */
	public void setEsq(NoAVL<E> esq) {
		this.esq = esq;
	}

	/** Substitui a referência da subárvore direita por dir. */
	public void setDir(NoAVL<E> dir) {
		this.dir = dir;
	}
	
	/** Retorna o filho do nó de acordo com o valor de direcao. Se direcao < 0
	 * retorna o filho esquerdo caso contrário retorna o filho direito.
	 */
	public NoAVL<E> getFilho(int direcao) {
		if (direcao < 0)
			return esq;
		else
			return dir;
	}

	/** Atribui o filho do nó de acordo com o valor de direcao. Se direcao < 0
	 * atribui o filho esquerdo, caso contrário, atribui o filho direito.
	 */
	public void setFilho(int direcao, NoAVL<E> filho) {
		if (direcao < 0)
			esq = filho;
		else
			dir = filho;
	}

}
