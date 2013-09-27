package arvore;

import utilitarios.MyIterator;
import fila.FilaEnc;

/*                  10
 *                   
 *           20           30
 * 
 *       40      50            60
 * 
 *                    70
 */

public class DemoArvoreBinaria {

	static NoArvoreBin<Integer> raiz;
	static int numItens;
	
	static class ArvoreInterator implements MyIterator<Integer>{

		private FilaEnc<NoArvoreBin<Integer>> fila = new FilaEnc<NoArvoreBin<Integer>>();
		private NoArvoreBin<Integer> pAnt;
		
		private void simetrica(NoArvoreBin<Integer> no){
			if(no != null){
				simetrica(no.getEsq());
				fila.insira(no);
				simetrica(no.getDir());
			}
		}
		
		@Override
		public Integer getFirst() {
			simetrica(raiz);
			if(fila.isEmpty()){
				return null;
			}
			pAnt = fila.remova();
			return pAnt.getObj();
		}

		@Override
		public Integer getNext() {
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
	
	static void gereBalanceada(Integer[] dados, int inicio, int fim){
		if(inicio <= fim){
			int meio = (inicio + fim) / 2;
			add(dados[meio]);
			gereBalanceada(dados, inicio, meio - 1);
			gereBalanceada(dados, meio + 1, fim);
		}
	}
	
	static void balanceie(){
		Integer[] dados = new Integer[numItens];
		int i = -1;
		MyIterator<Integer> it = new ArvoreInterator();
		Integer n = it.getFirst();
		while(n != null){
			i++;
			dados[i] = n;
			n = it.getNext();
		}
		raiz = null;
		int num = numItens;
		numItens=0;
		gereBalanceada(dados, 0, num - 1);
	}
	
	static NoArvoreBin<Integer> insert(Integer obj, NoArvoreBin<Integer> pai,
	      int direcao) {

		NoArvoreBin<Integer> novoNo = new NoArvoreBin<Integer>(obj, pai);
		if (pai == null) {
			raiz = novoNo;
			return novoNo;
		}

		if (pai.getFilho(direcao) != null)
			return null;

		pai.setFilho(direcao, novoNo);
		return novoNo;
	}
	
	static boolean remove(Integer n){
		NoArvoreBin<Integer> p = raiz;
		while(p != null){
			if(n.compareTo(p.getObj()) == 0){
				delete(p);
				return true;
			}
			if(n.compareTo(p.getObj()) < 0)
				p = p.getEsq();
			else
				p = p.getDir();
		}
		return false;
	}
	
	static boolean add(Integer n){
		int direcao = 0;
		NoArvoreBin<Integer> p = raiz;
		NoArvoreBin<Integer> pai = null;
		if(raiz != null){
			while(p != null){
				if(n.compareTo(p.getObj()) == 0)
					return false;
				if(n.compareTo(p.getObj()) < 0){
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
		insert(n,pai,direcao);
		numItens++;
		return true;
	}

	static boolean delete(NoArvoreBin<Integer> no) {
		NoArvoreBin<Integer> filho;
		// Verifica se o no tem dois filhos
		if ((no.getDir() != null) && (no.getEsq() != null)) {
			NoArvoreBin<Integer> pTemp = null;
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
			NoArvoreBin<Integer> pai = no.getPai();
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

	// *************************************************************
	// Impressão da arvore na forma normal
	// *************************************************************
	static class DescNo<E> {
		int nivel;
		int ident;
		NoArvoreBin<E> no;
	}

	static void desenhe(int larguraTela) {
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
		NoArvoreBin<Integer> pTemp;
		StringBuffer linha1 = new StringBuffer(200);

		DescNo<Integer> descNo;
		FilaEnc<DescNo<Integer>> fila = new FilaEnc<DescNo<Integer>>();

		descNo = new DescNo<Integer>();

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
			}
			else {
				System.out.println(linha1 + "\n\n");
				linha1.setLength(0);
				linha1.append(brancos1.substring(0, ident)
				      + pTemp.getObj().toString());
				nivelAnt = nivel;
			}
			nivel = nivel + 1;
			offset = (int) (largTela / Math.round(Math.pow(2, nivel + 1)));
			if (pTemp.getEsq() != null) {
				descNo = new DescNo<Integer>();
				descNo.ident = ident - offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getEsq();
				fila.insira(descNo);
			}
			if (pTemp.getDir() != null) {
				descNo = new DescNo<Integer>();
				descNo.ident = ident + offset;
				descNo.nivel = nivel;
				descNo.no = pTemp.getDir();
				fila.insira(descNo);
			}
		}
		System.out.println(linha1);
	}

	public static void main(String[] args) {
		add(50);
		add(20);
		add(70);
		add(25);
		add(33);
		add(65);
		desenhe(60);
		System.out.println();
		balanceie();
		desenhe(60);
	}
}
