package colecao;

@SuppressWarnings("serial")
public abstract class ColecaoComparavel<E extends Comparable<E>>  extends ColecaoNaoComparavel<E> {

	public boolean contains(E obj){
		return (retrieve(obj) != null);
	}
	public abstract boolean remove(E obj);
	public abstract E retrieve(E obj);
	public abstract boolean add(E obj);
}
