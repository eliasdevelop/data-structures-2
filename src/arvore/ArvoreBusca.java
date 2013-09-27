package arvore;

import java.io.Serializable;
import utilitarios.MyIterator;
import colecao.ColecaoComparavel;
import fila.FilaEnc;

public class ArvoreBusca<E extends Comparable<E>> extends ColecaoComparavel<E> implements Serializable{

	protected NoArvoreBin<E> raiz;
	
	private class ArvoreBuscaInterator implements MyIterator<E>{

		private FilaEnc<NoArvoreBin<E>> fila = new FilaEnc<NoArvoreBin<E>>();
		private NoArvoreBin<E> pAnt;
		
		private void simetrica(NoArvoreBin<E> no){
			if(no != null){
				simetrica(no.getEsq());
				fila.insira(no);
				simetrica(no.getDir());
			}
		}
		
		@Override
		public E getFirst() {
			simetrica(raiz);
			if(fila.isEmpty()){
				return null;
			}
			pAnt = fila.remova();
			return pAnt.getObj();
		}

		@Override
		public E getNext() {
			if(fila.isEmpty()){
				return null;
			}
			pAnt = fila.remova();
			return pAnt.getObj();
		}

		@Override
		public void remove() {
			delete(pAnt);			
		}
		
	}
	
	@Override
	public void clear() {
		raiz = null;
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {
		return new ArvoreBuscaInterator();
	}

	@Override
	public boolean remove(E obj) {
		NoArvoreBin<E> p = raiz;
		while(p != null){
			if(obj.compareTo(p.getObj()) == 0){
				delete(p);
				return true;
			}
			if(obj.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return false;
	}
	
	private boolean delete(NoArvoreBin<E> no) {
		NoArvoreBin<E> filho;
		// Verifica se o no tem dois filhos
		if ((no.getDir() != null) && (no.getEsq() != null)) {
			NoArvoreBin<E> pTemp = null;
			// Procura na subarvore esquerda o
			// no que se encontra mais a direita
			pTemp = no.getEsq();
			while (pTemp.getDir() != null) {
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
			if (raiz.getEsq() != null)
				raiz = raiz.getEsq();
			else
				raiz = raiz.getDir();
			if (raiz != null)
				raiz.setPai(null);
			numItens--;
			return true;
		}
		else {
			int direcao = 1;
			NoArvoreBin<E> pai = no.getPai();
			if (no == pai.getEsq())
				direcao = -1;
			filho = pai.getFilho(direcao);
			if (filho == null)
				return true;
			if (filho.getEsq() != null) {
				pai.setFilho(direcao, filho.getEsq());
				filho.getEsq().setPai(pai);
			}
			else {
				pai.setFilho(direcao, filho.getDir());
				if (filho.getDir() != null)
					filho.getDir().setPai(pai);
			}
			numItens--;
			return true;
		}
	}

	@Override
	public E retrieve(E obj) {
		NoArvoreBin<E> p = raiz;
		while(p != null){
			if(obj.compareTo(p.getObj()) == 0){
				return p.getObj();
			}
			if(obj.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return null;
	}

	@Override
	public boolean add(E obj) {
		int direcao = 0;
		NoArvoreBin<E> p = raiz;
		NoArvoreBin<E> pai = null;
		if(raiz != null){
			while(p != null){
				if(obj.compareTo(p.getObj()) == 0)
					return false;
				if(obj.compareTo(p.getObj()) < 0){
					if(p.getEsq() == null){
						pai = p;
						direcao = -1;
						break;
					}
					p = p.getEsq();
				}else{
					if(p.getDir() == null){
						pai = p;
						direcao = 1;
						break;
					}
					p = p.getDir();
				}
			}
		}
		insert(obj,pai,direcao);
		numItens++;
		return true;
	}
	
	protected NoArvoreBin<E> insert(E obj, NoArvoreBin<E> pai, int direcao) {

			NoArvoreBin<E> novoNo = new NoArvoreBin<E>(obj, pai);
			if (pai == null) {
				raiz = novoNo;
				return novoNo;
			}

			if (pai.getFilho(direcao) != null)
				return null;

			pai.setFilho(direcao, novoNo);
			return novoNo;
	}

	// *************************************************************
	// Impressao da arvore na forma normal
	// *************************************************************
	static class DescNo<E> {
		int nivel;
		int ident;
		NoArvoreBin<E> no;
	}

	public void desenhe(int larguraTela) {
		if (raiz == null)
			return;
		StringBuffer brancos1 = new StringBuffer();
		String brancos = "                                                        ";
		brancos1.append(brancos);
		brancos1.append(brancos);
		brancos1.append(brancos);
		brancos1.append(brancos);
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoArvoreBin<E> pTemp;
		StringBuffer linha1 = new StringBuffer(200);
		String linha = "";

		DescNo<E> descNo;
		FilaEnc<DescNo<E>> fila = new FilaEnc<DescNo<E>>();

		descNo = new DescNo<E>();

		descNo.nivel = 0;
		descNo.ident = ident;
		descNo.no = raiz;
		fila.insira(descNo);
		String pai;
		while (!fila.isEmpty()) {
			descNo = fila.remova();
			ident = descNo.ident;
			pTemp = descNo.no;
			nivel = descNo.nivel;
			if (pTemp.getPai() == null)
				pai = "  ";
			else
				pai = pTemp.getPai().getObj().toString();
			if (nivel == nivelAnt) {
				linha1.append(brancos1.substring(0, ident - linha1.length())
				      + pTemp.getObj().toString());
				linha = linha + brancos1.substring(0, ident - linha.length())
				      + pTemp.getObj().toString() + "(h=" + pTemp.getAltura() + ")";
			}
			else {
				System.out.println(linha + "\n\n");
				linha1.setLength(0);
				linha1.append(brancos1.substring(0, ident)
				      + pTemp.getObj().toString());
				linha = brancos1.substring(0, ident) + pTemp.getObj().toString()
				      + "(h=" + pTemp.getAltura() + ")";
				nivelAnt = nivel;
			}
			nivel = nivel + 1;
			offset = (int) (largTela / Math.round(Math.pow(2, nivel + 1)));
			if (pTemp.getEsq() != null) {
				descNo = new DescNo<E>();
				descNo.ident = ident - offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getEsq();
				fila.insira(descNo);
			}
			if (pTemp.getDir() != null) {
				descNo = new DescNo<E>();
				descNo.ident = ident + offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getDir();
				fila.insira(descNo);
			}
		}
		System.out.println(linha1);
	}
}
