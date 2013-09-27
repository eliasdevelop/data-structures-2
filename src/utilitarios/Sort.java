package utilitarios;

import java.util.Comparator;

public class Sort {

	public static void selecao(Object[] a, Comparator c) {
		int iMenor;
		Object temp;

		for (int i = 0; i <= a.length - 2; i++) {

			iMenor = i;
			for (int k = i + 1; k <= a.length - 1; k++) {
				if (c.compare(a[k], a[iMenor]) < 0)
					iMenor = k;
			}

			if (i != iMenor) {
				temp = a[i];
				a[i] = a[iMenor];
				a[iMenor] = temp;
			}

		}
	}

	public static void bolha(Object[] a, Comparator c) {
		int fim;
		boolean troquei;
		Object temp;
		fim = a.length - 1;
		do {
			troquei = false;

			for (int i = 0; i <= fim - 1; i++) {
				if (c.compare(a[i], a[i + 1]) > 0) {
					temp = a[i];
					a[i] = a[i + 1];
					a[i + 1] = temp;
					troquei = true;
				}
			}
			fim--;
		} while (troquei);

	}

	public static void insercao(Object[] vetor, Comparator c) {
		int k;
		Object temp;

		for (int i = 0; i <= vetor.length - 1; i++) {
			k = i;
			temp = vetor[i];

			while ((k > 0) && (c.compare(temp, vetor[k - 1]) < 0)) {
				vetor[k] = vetor[k - 1];
				k--;
			}
			vetor[k] = temp;
		}

	}
	
	public static void quickSort(Object[] a, Comparator c){		
		qSort(a, 0, a.length - 1, c);		
	}

	private static void qSort(Object[] a, int primeiro, int ultimo,	Comparator c) {
		int indicePivo;

		if (primeiro < ultimo) {
			indicePivo = particao(a, primeiro, ultimo, c);
			qSort(a, primeiro, indicePivo - 1, c);
			qSort(a, indicePivo + 1, ultimo, c);
		}

	}
	

	private static int particao(Object[] a, int primeiro, int ultimo,
			Comparator c) {
		int i, j, indicePivo;
		Object temp, pivo;

		pivo = a[primeiro];
		i = primeiro;
		j = ultimo;
		do {
			while ((c.compare(a[i], pivo) == 0 || c.compare(a[i], pivo) < 0)
					&& (i < ultimo))
				i++;
			while ((c.compare(a[j], pivo) > 0) && (j > primeiro))
				j--;
			if (i < j) {
				temp = a[i];
				a[i] = a[j];
				a[j] = temp;
			}
		} while (i < j);
		indicePivo = j;
		temp = a[primeiro];
		a[primeiro] = a[indicePivo];
		a[indicePivo] = temp;
		return indicePivo;
	}
	
}
