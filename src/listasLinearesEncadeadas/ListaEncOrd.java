package listasLinearesEncadeadas;

import java.io.Serializable;

import listasEncadeadas.NoSimpEnc;
import utilitarios.MyIterator;

public class ListaEncOrd<E extends Comparable<E>> extends ListaEnc<E> implements Serializable{

		@Override
		public boolean add(E obj) {
			if (lista.isEmpty()){
				lista.insertAtEnd((E)obj);
			} else {
				MyIterator<E> it = lista.iterator();
				E p = it.getFirst();
				E pAnt = null;
				while (p != null){
					if (pAnt != null){
						if ((obj.compareTo(p) < 0) & (obj.compareTo(pAnt) > 0)){
							NoSimpEnc<E> noAnt = new NoSimpEnc<E>((E)pAnt);
							NoSimpEnc<E> no = new NoSimpEnc<E>((E)p);
							noAnt.setProx(no);
							lista.insertAfter((E)obj, noAnt);
							return true;
						}
					} else if (obj.compareTo(p) < 0){
						lista.insertAtBegin((E)obj);
						return true;
					} else {
						lista.insertAtEnd((E)obj);
						return true;
					}
					pAnt = p;
					p = it.getNext();
				}
			}
			return false;
		}
}
