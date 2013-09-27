package arvore;

import java.io.Serializable;

import lista04.Turma;

import utilitarios.MyIterator;
import colecao.ColecaoComparavel;
import fila.FilaEnc;

public class ArvoreAVL<E extends Comparable<E>> extends ColecaoComparavel<E> implements Serializable {

	private NoAVL<E> raiz;
	
	private class ArvoreAVLInterator implements MyIterator<E>{

		private FilaEnc<NoAVL<E>> fila = new FilaEnc<NoAVL<E>>();
		private NoAVL<E> pAnt;
		
		private void simetrica(NoAVL<E> no){
			if(no != null && !no.isDeletado()){
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
			pAnt.setDeletado(true);
			numItens--;
		}
		
	}

	private int height(NoAVL<E> no) {
		return no == null ? -1 : no.getAltura();
	}

	public int balanco(NoAVL<E> no) {
		return height(no.getEsq()) - height(no.getDir());
	}

	private NoAVL<E> rotacaoParaDireita(NoAVL<E> no) {
		NoAVL<E> p = no.getEsq();
		no.setEsq(p.getDir());
		p.setDir(no);
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		p.setAltura(Math.max(height(p.getEsq()), no.getAltura()) + 1);
		return p;
	}

	private NoAVL<E> rotacaoParaEsquerda(NoAVL<E> no) {
		NoAVL<E> p = no.getDir();
		no.setDir(p.getEsq());
		p.setEsq(no);
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		p.setAltura(Math.max(height(p.getDir()), no.getAltura()) + 1);
		return p;
	}

	private NoAVL<E> rotacaoDuplaEsquerdaDireita(NoAVL<E> no) {
		no.setEsq(rotacaoParaEsquerda(no.getEsq()));
		return rotacaoParaDireita(no);
	}

	private NoAVL<E> rotacaoDuplaDireitaEsquerda(NoAVL<E> no) {
		no.setDir(rotacaoParaDireita(no.getDir()));
		return rotacaoParaEsquerda(no);
	}

	private NoAVL<E> insira(E obj, NoAVL<E> no) {

		if (no == null) {
			no = new NoAVL<E>(obj);
		}
		else
			if (no.isDeletado() && (obj.compareTo(no.getObj()) == 0)) {
				no.setDeletado(false);
				no.setObj(obj);
				return no;
			}
			else {
				if (obj.compareTo(no.getObj()) < 0) {
					no.setEsq(insira(obj, no.getEsq()));
					if (height(no.getEsq()) - height(no.getDir()) == 2)
						if (obj.compareTo(no.getEsq().getObj()) < 0)
							no = rotacaoParaDireita(no);
						else
							no = rotacaoDuplaEsquerdaDireita(no);
				}
				else
					if (obj.compareTo(no.getObj()) >= 0) {
						no.setDir(insira(obj, no.getDir()));
						if (height(no.getDir()) - height(no.getEsq()) == 2)
							if (obj.compareTo(no.getDir().getObj()) > 0)
								no = rotacaoParaEsquerda(no);
							else
								no = rotacaoDuplaDireitaEsquerda(no);
					}
			}
		no.setAltura(Math.max(height(no.getEsq()), height(no.getDir())) + 1);
		return no;
	}

	@Override
	public boolean add(E obj) {
		if (contains(obj))
			return false;
		NoAVL<E> no = insira(obj, raiz);
		raiz = no;
		numItens++;
		return true;
	}

	@Override
	public void clear() {
		raiz = null;
		numItens = 0;
	}

	@Override
	public MyIterator<E> iterator() {		
		return new ArvoreAVLInterator();
	}

	@Override
	public boolean remove(E obj) {
		NoAVL<E> p = raiz;
		while(p != null){
			if(obj.compareTo(p.getObj()) == 0){
			    if(p.isDeletado()){
			    	return false;
			    }else{
			    	p.setDeletado(true);
			    	numItens--;
			    	return true;
			    }
			}
			if(obj.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return false;
	}
	
	public boolean removeMarkDel(){
		FilaEnc<E> fila = new FilaEnc<E>();
		if(fila.isEmpty()){
			return false;
		}else{
			MyIterator<E> it = iterator();
			E temp = it.getFirst();
			while (temp != null) {
				fila.insira(temp);
			}
			clear();
			while (!fila.isEmpty()) {
				add(fila.remova());
			}			
			return true;	
		}
	}
	
	@Override
	public E retrieve(E obj) {
		NoAVL<E> p = raiz;
		while(p != null){
			if(obj.compareTo(p.getObj()) == 0){
				if(p.isDeletado()){
					return null;
				}else{
					return p.getObj();
				}				
			}
			if(obj.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return null;
	}

	// *************************************************************
	// Impresao da arvore
	// *************************************************************
	private class DescNo<E> {
		int nivel;
		int ident;
		NoAVL<E> no;
	}

	public void desenhe(int larguraTela) {
		int balanco;
		char deletado;
		if (raiz == null)
			return;
		String brancos = "                                                        "
		      + "                                                        "
		      + "                                                        ";
		int largTela = larguraTela;
		int ident = largTela / 2;
		int nivelAnt = 0;
		int nivel = 0;
		int offset;
		NoAVL<E> pTemp;
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
			balanco = height((NoAVL<E>) pTemp.getEsq())
			      - height((NoAVL<E>) pTemp.getDir());
			if (pTemp.isDeletado())
				deletado = 'D';
			else
				deletado = 'V';
			if (nivel == nivelAnt) {
				linha = linha + brancos.substring(0, ident - linha.length())
				      + pTemp.getObj().toString() + "(" + balanco + ")" + deletado;
			}
			else {
				System.out.println(linha + "\n\n");
				linha = brancos.substring(0, ident) + pTemp.getObj().toString()
				      + "(" + balanco + ")" + deletado;
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
		System.out.println(linha);
	}

}
