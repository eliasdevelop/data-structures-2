package listasEncadeadas;

/* Referência: Data Structures and Algorithms in Java
 * Peter Drake
 */
public class NoDupEnc<E> {
	private E obj;
	private NoDupEnc<E> prox;
	private NoDupEnc<E> ant;

	public NoDupEnc() {
		this.prox = this;
		this.ant = this;
	}

	public NoDupEnc(E obj, NoDupEnc<E> ant, NoDupEnc<E> prox) {
		this.obj = obj;
	   this.ant = ant;
	   ant.prox = this;
	   this.prox = prox;
	   prox.ant = this;
	}

	public E getObj() {
		return obj;
	}

	public NoDupEnc<E> getProx() {
		return prox;
	}

	public NoDupEnc<E> getAnt() {
		return ant;
	}

	public void setObj(E obj) {
		this.obj = obj;
	}

	public void setProx(NoDupEnc<E> prox) {
		this.prox = prox;
	}

	public void setAnt(NoDupEnc<E> ant) {
		this.ant = ant;
	}

	public void remove() {
	   this.ant.prox = this.prox;
	   this.prox.ant = this.ant;
	}

}
