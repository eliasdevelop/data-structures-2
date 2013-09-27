package arvore;

import utilitarios.MyIterator;
import colecao.ColecaoComparavel;
import fila.FilaEnc;


public class ArvoreRedBlack<E extends Comparable<E>> extends ColecaoComparavel<E>  {
	/** The root node of this tree. */
	private NoRedBlack<E> raiz;
	private boolean permiteDadosRepetidos;

	/** All "null" node references actually point to this node. */
	private NoRedBlack<E> sentinel;

	private class ArvoreIterator implements MyIterator<E> {

		private FilaEnc<NoRedBlack<E>> fila = new FilaEnc<NoRedBlack<E>>();
		private NoRedBlack<E> noAnt = new NoRedBlack<E>();		
		
		private void simetrica(NoRedBlack<E> no){
			if(no != sentinel){
				simetrica(no.getEsq());
				fila.insira(no);
				simetrica(no.getDir());
			}
		}
		
		@Override
		public E getFirst() {
			fila.clear();
			simetrica(raiz);
			if (fila.isEmpty())
				return null;
			noAnt = fila.remova();
			return noAnt.getObj();
		}

		@Override
		public E getNext() {
			if (fila.isEmpty())
				return null;
			noAnt = fila.remova();
			return noAnt.getObj();
		}

		@Override
		public void remove() {
			delete(noAnt);
		}
	}

	/** The tree is initially empty. */
	public ArvoreRedBlack(boolean permiteDadosRepetidos) {
		sentinel = new NoRedBlack<E>();
		raiz = sentinel;
		this.permiteDadosRepetidos = permiteDadosRepetidos;
	}

	private void rotacaoParaEsquerda(NoRedBlack<E> no) {
		if (no.getDir() == sentinel)
			return;
		NoRedBlack<E> filho = no.getDir();
		NoRedBlack<E> pai = no.getPai();
		no.setDir(filho.getEsq());
		if (no.getDir() != sentinel)
			no.getDir().setPai(no);
		filho.setPai(no.getPai());
		
		if (pai != sentinel){
		if (pai.getEsq() == no)
			pai.setEsq(filho);
		else
			pai.setDir(filho);
		}
		else{
			raiz = filho;
			filho.setPai(sentinel);
		}
		filho.setEsq(no);
		no.setPai(filho);
	}

	private void rotacaoParaDireita(NoRedBlack<E> no) {
		if (no.getEsq() == sentinel)
			return;
		NoRedBlack<E> filho = no.getEsq();
		NoRedBlack<E> pai = no.getPai();
		no.setEsq(filho.getDir());
		if (no.getEsq() != sentinel)
			no.getEsq().setPai(no);
		filho.setPai(no.getPai());
		
		if (pai != sentinel) {
			if (pai.getEsq() == no)
				pai.setEsq(filho);
			else
				pai.setDir(filho);
		}
		else {
			raiz = filho;
			filho.setPai(sentinel);
		}

		filho.setDir(no);
		no.setPai(filho);
	}
	
	//Caso5: O pai do no e' vermelho e o tio e' preto. 
	//Alem disso, o no e o pai sao filhos esquerdos(ou direitos)
	private void insertCase5(NoRedBlack<E> no) {
		NoRedBlack<E> avo = no.getAvo();
		no.getPai().setBlack();
		avo.setRed();
		if (no.isFilhoEsquerdo())
			rotacaoParaDireita(avo);
		else
			rotacaoParaEsquerda(avo);
	}

	//Caso4: O no e' filho direito(ou esquerdo) do pai, o pai e' filho 
	//esquerdo(ou direito) do avo e o tio e' preto
	private void insertCase4(NoRedBlack<E> no) {
		NoRedBlack<E> avo = no.getAvo();
		if (no.isFilhoDireito() && no.getPai().isFilhoEsquerdo()) {
			rotacaoParaEsquerda(no.getPai());
			no = no.getEsq();
		}
		else
			if ((no.isFilhoEsquerdo()) && no.getPai().isFilhoDireito()) {
				rotacaoParaDireita(no.getPai());
				no = no.getDir();
			}
		insertCase5(no);
	}
	
	//Caso3: O tio e o pai do no sao vermelhos e o avo e' preto
	private void insertCase3(NoRedBlack<E> no) {
		NoRedBlack<E> tio = no.getTio();
		NoRedBlack<E> avo = no.getAvo();
		if (tio != sentinel && tio.isRed()) {
			no.getPai().setBlack();
			tio.setBlack();
			avo.setRed();
			insertCase1(avo);
		}
		else
			insertCase4(no);
	}

	//Caso2: O pai do no' e' preto
	private void insertCase2(NoRedBlack<E> no) {
		if (no.getPai().isBlack())
			return;
		else
			insertCase3(no);
	}

	//Caso1: A raiz e' vermelha
	private void insertCase1(NoRedBlack<E> no) {
		if (no == raiz)
			no.setBlack();
		else
			insertCase2(no);
	}

	
	//Caso 1: O no corrente e' a raiz
	private void deleteCase1(NoRedBlack<E> no){
		if (no.getPai() == sentinel)
			return;
		else
			deleteCase2(no);
	}
	
	//Caso 2: O irmao do no corrente e' vermelho
	private void deleteCase2(NoRedBlack<E> no){
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (s.isRed()){
			no.getPai().setRed();
			s.setBlack();
			if (no.isFilhoEsquerdo())
				rotacaoParaEsquerda(no.getPai());
			else
				rotacaoParaDireita(no.getPai());
		}
		deleteCase3(no);
	}

	//Caso 3: O irmao do no corrente e' preto, 
	//o pai e' preto e os filhos do irmao sao pretos
	private void deleteCase3(NoRedBlack<E> no){
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.getPai().isBlack() && s.isBlack() &&
				s.getEsq().isBlack() && s.getDir().isBlack()){
			s.setRed();
			deleteCase1(no.getPai());
		}
		else
			deleteCase4(no);
	}
	
	//Caso 4: O irmao do no corrente e' preto, 
	//o pai e' vermelho e os filhos do irmao sao pretos
	private void deleteCase4(NoRedBlack<E> no){
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.getPai().isRed() && s.isBlack() &&
				s.getEsq().isBlack() && s.getDir().isBlack()){
			s.setRed();
			no.getPai().setBlack();
		}
		else
			deleteCase5(no);
	}
	
	//Caso 5: O no e' filho esquerdo do pai, 
	//o irmao e' preto, o filho esquerdo do irmao e' vermelho 
	//e o filho direito do irmao e' preto
	private void deleteCase5(NoRedBlack<E> no){
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		if (no.isFilhoEsquerdo() && s.isBlack() &&
				s.getEsq().isRed() && s.getDir().isBlack()){
			s.setRed();
			s.getEsq().setBlack();
			rotacaoParaDireita(s);
		}
		else
			if (no.isFilhoDireito() && s.isBlack() &&
					s.getDir().isRed() && s.getEsq().isBlack()){
				s.setRed();
				s.getDir().setBlack();
				rotacaoParaEsquerda(s);
			}
		deleteCase6(no);
	}
	
	//Caso 6: O no e' filho esquerdo do pai, 
	//o irmao e' preto, o filho esquerdo do irmao e' vermelho
	private void deleteCase6(NoRedBlack<E> no){
		NoRedBlack<E> s = no.getIrmao();
		if (s == sentinel)
			return;
		s.setColor(no.getPai().getColor());
		no.getPai().setBlack();
		if (no.isFilhoEsquerdo()){
			s.getDir().setBlack();
			rotacaoParaEsquerda(no.getPai());
		}
		else{
			s.getEsq().setBlack();
			rotacaoParaDireita(no.getPai());
		}
			
	}
	
	@Override
   public void clear() {
	   raiz = null;
   }

	@Override
   public MyIterator<E> iterator() {
		return new ArvoreIterator();
   }

	@Override
   public boolean remove(E obj) {
	   // implementar
		NoRedBlack<E> p = raiz;
		while(p != sentinel){
			if (obj.compareTo(p.getObj()) == 0){
				deleteCase1(p);
				delete(p);
				numItens--;
				return true;
			}
			if(obj.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return false;
	}

	public boolean delete(NoRedBlack<E> no) {
		NoRedBlack<E> filho;
		// Verifica se o no tem dois filhos
		if ((no.getDir() != sentinel) && (no.getEsq() != sentinel)) {
			NoRedBlack<E> pTemp = sentinel;
			// Procura na subarvore esquerda o
			// no que se encontra mais a direita
			pTemp = no.getEsq();
			while (pTemp.getDir() != sentinel) {
				pTemp = pTemp.getDir();
			}
			// Copia o objeto do no que se encontra mais
			// a direita no no que desejamos remover
			no.setObj(pTemp.getObj());
			// Faz com que o no a ser removido seja o no
			// que se encontra mais a direita da subarvore
			// esquerda
			no = pTemp;
		}
		// Verifica se esta removendo a raiz
		if (no == raiz) {
			if (raiz.getEsq() != sentinel)
				raiz = raiz.getEsq();
			else
				raiz = raiz.getDir();
			if (raiz != sentinel)
				raiz.setPai(sentinel);
			return true;
		}
		else {
			int direcao = 1;
			NoRedBlack<E> pai = no.getPai();
			if (no == pai.getEsq())
				direcao = -1;
			filho = pai.getFilho(direcao);
			if (filho == sentinel)
				return true;
			if (filho.getEsq() != sentinel) {
				pai.setFilho(direcao, filho.getEsq());
				filho.getEsq().setPai(pai);
			}
			else {
				pai.setFilho(direcao, filho.getDir());
				if (filho.getDir() != sentinel)
					filho.getDir().setPai(pai);
			}
			return true;
		}
	}

	@Override
   public E retrieve(E obj) {
		int c;
		NoRedBlack<E> p = raiz;
		while (p != sentinel){
			c = obj.compareTo(p.getObj());
			if (c == 0){
				return p.getObj();
			}else{
				if(c > 0){
					p = p.getDir();
				}else {
					p = p.getEsq();
				}
			}
		}
		return null;
   }

	@Override
   public boolean add(E obj) {
		NoRedBlack<E> p = raiz;
		NoRedBlack<E> pai = sentinel;
		int direcao = 0;
		if(raiz  != sentinel){
			while(p != sentinel){
				if(obj.compareTo(p.getObj()) == 0){
					if(permiteDadosRepetidos){
						if (p.getFilho(1) == sentinel){
							pai = p;
							direcao = 1;
							break;
						}	
					}else{
						return false;
					}
				}
				if(obj.compareTo(p.getObj()) < 0){
					if (p.getEsq() == sentinel){
						pai = p;
						direcao = -1;
						break;
					}
					p = p.getEsq();
				} else {
					if (p.getDir() == sentinel){
						pai = p;
						direcao = 1;
						break;
					}
					p = p.getDir();
				}
			}
		}
		insertCase1(insert(obj, pai, direcao));
		numItens++;
	   return true;
   }
	
	public NoRedBlack<E> insert(E obj, NoRedBlack<E> pai,
		      int direcao) {

			NoRedBlack<E> novoNo = new NoRedBlack<E>(obj, sentinel);
			if (pai == sentinel) {
				raiz = novoNo;
				return novoNo;
			}

			if (pai.getFilho(direcao) != sentinel)
				return null;

			pai.setFilho(direcao, novoNo);
			novoNo.setPai(pai);
			return novoNo;
	}
	// *************************************************************
	// Impressao da arvore
	// *************************************************************
	static class DescNo<E> {
		int nivel;
		int ident;
		NoRedBlack<E> no;
	}

	public void desenhe(int larguraTela) {
		if (raiz == sentinel)
			return;
		String brancos = "                                                        "
		      + "                                                        "
		      + "                                                        ";
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoRedBlack<E> pTemp;
		String linha = "";

		DescNo<E> descNo;
		FilaEnc<DescNo<E>> fila = new FilaEnc<DescNo<E>>();

		descNo = new DescNo<E>();

		descNo.nivel = 0;
		descNo.ident = ident;
		descNo.no = raiz;
		fila.insira(descNo);

		while (!fila.isEmpty()) {
			descNo = fila.remova();
			ident = descNo.ident;
			pTemp = descNo.no;
			nivel = descNo.nivel;

			if (nivel == nivelAnt) {
				linha = linha + brancos.substring(0, ident - linha.length())
				      + pTemp.getObj().hashCode();
			}
			else {
				System.out.println(linha + "\n\n");
				linha = brancos.substring(0, ident) + pTemp.getObj().hashCode();
				nivelAnt = nivel;
			}
			linha = linha + (pTemp.getColor()?"-Red":"-Black");
			nivel = nivel + 1;
			offset = (int) (largTela / Math.round(Math.pow(2, nivel + 1)));
			if (pTemp.getEsq() != sentinel) {
				descNo = new DescNo<E>();
				descNo.ident = ident - offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getEsq();
				fila.insira(descNo);
			}
			if (pTemp.getDir() != sentinel) {
				descNo = new DescNo<E>();
				descNo.ident = ident + offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getDir();
				fila.insira(descNo);
			}
		}
		System.out.println(linha);
	}
	
}
