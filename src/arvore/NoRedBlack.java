package arvore;


//ReferEncia: Data Structures and Algorithms in Java - Peter Drake
public class NoRedBlack<E> {
	/** Black node color. */
	public static final boolean BLACK = false;

	/** Red node color. */
	public static final boolean RED = true;

	/** Color of this node, BLACK or RED. */
	private boolean color;

	/** Item associated with this node. */
	private E obj;

	/** Left child of this node. */
	private NoRedBlack<E> esq;

	/** Parent of this node. */
	private NoRedBlack<E> pai;

	/** Right child of this node. */
	private NoRedBlack<E> dir;

	/** Used for constructing a sentinel. */
	protected NoRedBlack() {
		color = BLACK;
		// All other fields are irrelevant
	}

	/**
	 * The new node is red and both of its children are sentinel. The node's
	 * parent is NOT set by this constructor.
	 */
	public NoRedBlack(E obj, NoRedBlack<E> sentinel) {
		color = RED;
		this.obj = obj;
		pai = sentinel;
		esq = sentinel;
		dir = sentinel;
	}

	/**
	 * Return this node's left (if direction is negative) or right (otherwise)
	 * child.
	 */
	public NoRedBlack<E> getFilho(int direcao) {
		if (direcao < 0) {
			return esq;
		}
		return dir;
	}

	/** Return the color of this node. */
	public boolean getColor() {
		return color;
	}

	/** Return the item associated with this node. */
	public E getObj() {
		return obj;
	}

	/** Return this node's left child. */
	public NoRedBlack<E> getEsq() {
		return esq;
	}

	/** Return this node's parent. */
	public NoRedBlack<E> getPai() {
		return pai;
	}

	/** Return this node's right child. */
	public NoRedBlack<E> getDir() {
		return dir;
	}

	/** Return true if this node is black. */
	public boolean isBlack() {
		return color == BLACK;
	}

	/** Return true if this node is red. */
	public boolean isRed() {
		return color == RED;
	}

	/**
	 * Set this node's left (if direction is negative) or right (otherwise)
	 * child.
	 */
	public void setFilho(int direcao, NoRedBlack<E> filho) {
		if (direcao < 0) {
			esq = filho;
		}
		else {
			dir = filho;
		}
	}

	/** Make this node black. */
	public void setBlack() {
		color = BLACK;
	}

	/** Set the color of this node. */
	public void setColor(boolean color) {
		this.color = color;
	}

	/** Set the item associated with this node. */
	public void setObj(E obj) {
		this.obj = obj;
	}

	/** Set the parent of this node. */
	public void setPai(NoRedBlack<E> pai) {
		this.pai = pai;
	}

	/** Make this node red. */
	public void setRed() {
		color = RED;
	}
	
	public void setDir(NoRedBlack<E> dir){
		this.dir = dir;
	}
	
	public void setEsq(NoRedBlack<E> esq){
		this.esq = esq;
	}
	
	
	public boolean isFilhoDireito(){
		return this == getPai().dir;
	}
	
	public boolean isFilhoEsquerdo(){
		return this == getPai().esq;
	}
	
	//pai do pai é o avo
	public NoRedBlack<E> getAvo(){
		return getPai().getPai();
	}
	
	//se vc é o esquerdo, o irmao é o direito
	//e vice versa
	public NoRedBlack<E> getIrmao(){
		if(isFilhoEsquerdo())
			return getPai().dir;
		return getPai().esq;
	}
	
	//tio é o irmao do pai
	public NoRedBlack<E> getTio(){
		return getPai().getIrmao();
	}

	
	@Override
	public boolean equals(Object obj) {
		return this.getObj().equals(obj);
	}
	
}
