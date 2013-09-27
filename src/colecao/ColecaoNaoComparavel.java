package colecao;
import java.util.Comparator;

import utilitarios.MyIterator;
import utilitarios.Sort;

@SuppressWarnings("serial")
public abstract class ColecaoNaoComparavel<E> implements Colecao<E>{
   
	protected int numItens;

	@Override
	public boolean isEmpty() {
		return (numItens == 0);
	}
	
	@Override
	public int size() {
		return numItens;
	}

	@Override
	public Object[] toArray() {
		if (isEmpty())
			return null;
		Object[] objs = new Object[numItens];
		MyIterator<E> it = iterator();
		int i = -1;
		Object obj = it.getFirst();
		while (obj != null) {
			i++;
			objs[i] = obj;
			obj = it.getNext();
		}
		return objs;
	}
	
	@SuppressWarnings("unchecked")
   @Override
	public Object[] sort(Comparator<E> comparador){
		if (numItens == 0)
			return null;
		Object[] obj = toArray();
		Sort.quickSort(obj, comparador);
		return obj;
	}
}
