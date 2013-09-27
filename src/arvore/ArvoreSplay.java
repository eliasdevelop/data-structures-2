package arvore;

@SuppressWarnings("serial")
public class ArvoreSplay<E extends Comparable<E>> extends ArvoreBusca<E> {

	@Override
	public E retrieve(E obj) {
		NoArvoreBin<E> p = raiz;
		NoArvoreBin<E> pAnt = null;
		while (p != null) {
			if (obj.compareTo(p.getObj()) == 0) {
				difusao(p);
				return p.getObj();
			}
			if (obj.compareTo(p.getObj()) < 0) {
				pAnt = p;
				p = p.getEsq();
			} else {
				pAnt = p;
				p = p.getDir();
			}
		}
		difusao(pAnt);
		return null;
	}

	@Override
	protected NoArvoreBin<E> insert(E obj, NoArvoreBin<E> pai, int direcao) {
		NoArvoreBin<E> novoNo = new NoArvoreBin<E>(obj, pai);
		if (pai == null) {
			raiz = novoNo;
		}else{
			pai.setFilho(direcao, novoNo);
			while(raiz != novoNo)
				difusao(novoNo);
		}
		numItens++;
		return novoNo;
	}
	
	private void difusao(NoArvoreBin<E> no) {
		if (no.getPai() == raiz)
			zig(no);
		else {
			NoArvoreBin<E> pai = no.getPai();
			NoArvoreBin<E> avo = pai.getPai();
			if (no == pai.getDir() && pai == avo.getDir() || no == pai.getEsq()
					&& pai == avo.getEsq())
				zigzig(no);
			else
				zigzag(no);
		}
	}
	
	private void zig(NoArvoreBin<E> no) {
		if (no.getPai().getDir() == no)
			rotacaoParaEsquerda(no.getPai());
		else
			rotacaoParaDireita(no.getPai());
	}

	private void zigzig(NoArvoreBin<E> no) {
		NoArvoreBin<E> pai = no.getPai();
		NoArvoreBin<E> avo = pai.getPai();
		if (no == pai.getDir() && pai == avo.getDir()) {
			rotacaoParaEsquerda(pai);
			rotacaoParaEsquerda(avo);
			return;
		}

		if (no == pai.getEsq() && pai == avo.getEsq()) {
			rotacaoParaDireita(pai);
			rotacaoParaDireita(avo);
			return;
		}
	}

	private void zigzag(NoArvoreBin<E> no) {
		NoArvoreBin<E> pai = no.getPai();
		NoArvoreBin<E> avo = pai.getPai();

		if (no == pai.getDir() && pai == avo.getEsq()) {
			rotacaoParaEsquerda(pai);
			rotacaoParaDireita(avo);
			return;
		}

		if (no == pai.getEsq() && pai == avo.getDir()) {
			rotacaoParaDireita(pai);
			rotacaoParaEsquerda(avo);
			return;
		}

	}
	
	private void rotacaoParaEsquerda(NoArvoreBin<E> no) {
		if (no.getDir() == null)
			return;
		NoArvoreBin<E> filho = no.getDir();
		NoArvoreBin<E> pai = no.getPai();
		no.setDir(filho.getEsq());
		if (no.getDir() != null)
			no.getDir().setPai(no);
		filho.setPai(no.getPai());

		if (pai != null) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(null);
		}
		filho.setEsq(no);
		no.setPai(filho);
	}

	private void rotacaoParaDireita(NoArvoreBin<E> no) {
		if (no.getEsq() == null)
			return;
		NoArvoreBin<E> filho = no.getEsq();
		NoArvoreBin<E> pai = no.getPai();
		no.setEsq(filho.getDir());
		if (no.getEsq() != null)
			no.getEsq().setPai(no);
		filho.setPai(no.getPai());

		if (pai != null) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		} else {
			raiz = filho;
			filho.setPai(null);
		}

		filho.setDir(no);
		no.setPai(filho);
	}

}
