package listasEncadeadas;

import java.util.Random;

public class DemoSkipList {

	static int nivelMaximo = 10;
	static int nivelAtual;
	static SkipNode<Integer> noCabeca;

	public static class SkipNode<E extends Comparable<E>> {
		public E obj;
		public SkipNode<E>[] prox;

		public SkipNode(int nivel, E obj) {
			prox = new SkipNode[nivel + 1];
			this.obj = obj;
		}

	}

	static int calculaNivel() {
		int nivel = 0;
		while (nivel < nivelMaximo && Math.random() < 0.5 && nivel <= nivelAtual) {
			nivel++;
		}
		return nivel;
	}

	@SuppressWarnings("unchecked")
	static boolean add(Integer obj) {
		SkipNode<Integer> p = noCabeca;
		SkipNode<Integer>[] pAnt = new SkipNode[nivelMaximo + 1];

		for (int i = nivelAtual; i >= 0; i--) {
			while (p.prox[i] != null && p.prox[i].obj.compareTo(obj) < 0) {
				p = p.prox[i];
			}
			pAnt[i] = p;
		}
		p = p.prox[0];

		int nivel;

		if (p == null || !(p.obj.compareTo(obj) == 0)) {
			nivel = calculaNivel();
			p = new SkipNode<Integer>(nivel, obj);

			if (nivel > nivelAtual) {
				pAnt[nivel] = noCabeca;
				nivelAtual = nivel;
			}

			for (int i = 0; i <= nivel; i++) {
				p.prox[i] = pAnt[i].prox[i];
				pAnt[i].prox[i] = p;
			}
			return true;
		}
		return false;
	}

	static String display(int nivel) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		SkipNode<Integer> p = noCabeca.prox[nivel];
		while (p != null) {
			sb.append(p.obj.toString());
			p = p.prox[nivel];
			if (p != null)
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}

	static void imprima() {
		StringBuilder sb = new StringBuilder();
		for (int i = nivelAtual; i >= 0; i--) {
			sb.append(i + " - " + display(i) + "\n");
		}
		System.out.println(sb);
	}

	public static void main(String[] args) {
		noCabeca = new SkipNode<Integer>(nivelMaximo, null);
		Random r = new Random();
		Integer dado;
		int i = 1;
		do {
			dado = r.nextInt(10000);
			if (add(dado)) {
				i++;
			}
		} while (i <= 10);
		imprima();
	}
}
