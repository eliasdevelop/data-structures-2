package listasEncadeadas;

import java.io.Serializable;

import utilitarios.MyIterator;

public class ListaSimpEnc<E> implements Serializable {

	protected NoSimpEnc<E> inicio;
	protected NoSimpEnc<E> fim;
	protected int numItens;

	private class ListaSimpEncIterator implements MyIterator<E> {

		NoSimpEnc<E> p;
		NoSimpEnc<E> pAnt;
		int noCorrente;

		@Override
		public E getFirst() {
			if (size() == 0) {
				return null;
			} else {
				p = inicio;
				noCorrente = 0;
				return p.getObj();
			}
		}

		@Override
		public E getNext() {
			noCorrente++;
			if (noCorrente == size())
				return null;
			pAnt = p;
			p = pAnt.getProx();
			return p.getObj();
		}

		@Override
		public void remove() {
			if (pAnt == null) {
				inicio = p.getProx();
				numItens--;
				noCorrente--;
				return;
			} else if (p == fim) {
				fim = pAnt;
				numItens--;
				noCorrente--;
				return;
			} else {
				removeAfter(pAnt);
				noCorrente--;
			}

		}
	}

	// Retorna a refer�ncia ao in�cio da lista
	public NoSimpEnc<E> getInicio() {
		return inicio;
	}

	// Retorna a refer�ncia ao final da lista
	public NoSimpEnc<E> getFim() {
		return fim;
	}

	// Esvazia a lista
	public void clear() {
		inicio = fim = null;
		numItens = 0;
	}

	// Informa se a lista est� vazia ou n�o
	public boolean isEmpty() {
		return (numItens == 0);
	}

	// Insere um n� no in�cio da lista
	public void insertAtBegin(E obj) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj);
		if (inicio == null) {
			inicio = fim = novoNo;
		} else {
			novoNo.setProx(inicio);
			inicio = novoNo;
		}
		numItens++;
	}

	// Insere um n� no final da lista
	public void insertAtEnd(E obj) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj);
		if (inicio == null) {
			inicio = fim = novoNo;
		} else {
			fim.setProx(novoNo);
			fim = novoNo;
		}
		numItens++;
	}

	// Insere um n� ap�s o n� apontado por p
	public void insertAfter(E obj, NoSimpEnc<E> p) {
		NoSimpEnc<E> novoNo = new NoSimpEnc<E>(obj, p.getProx());
		if (p == fim)
			fim = novoNo;
		p.setProx(novoNo);
		numItens++;
	}

	// Remover o primeiro n� da lista,
	// retornando a refer�ncia ao objeto que se
	// encontra armazenado nele.
	// Se a lista estiver vazia retorna null
	public E removeFromBegin() {
		if (size() == 0) {
			return null;
		} else {
			E valorInicio = inicio.getObj();
			inicio = inicio.getProx();
			if (inicio == null)
				fim = null;
			numItens--;
			return valorInicio;
		}
	}

	// Remover o n� que segue o n� apontado por p,
	// retornando a refer�ncia ao objeto que se encontra
	// armazenado nele.
	// Se a lista estiver vazia ou se n�o existir o
	// pr�ximo n�, retorna null
	public E removeAfter(NoSimpEnc<E> p) {
		if (size() == 0 || p.getProx() == null) {
			return null;
		} else {
			E valorRemovido = p.getProx().getObj();
			if (p.getProx() == fim) {
				fim = p;
			} else {
				p.setProx(p.getProx().getProx());
				numItens--;
			}
			return valorRemovido;
		}
	}

	// Retorna o tamanho da lista
	public int size() {
		return numItens;
	}

	public MyIterator<E> iterator() {
		return new ListaSimpEncIterator();
	}
}
