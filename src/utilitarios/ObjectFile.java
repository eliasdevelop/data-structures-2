package utilitarios;

//Classe desenvolvida para ler e gravar objetos em arquivos binários
//Programado por Raimundo Machado Costa em 21/08/2008

import java.io.*;

public class ObjectFile {
	private final String			nomeArq;
	private ObjectOutputStream	outputStream;
	private ObjectInputStream	inputStream;

	public ObjectFile(String nomeArq) {
		this.nomeArq = nomeArq;
		outputStream = null;
		inputStream = null;
	}

	// Abre o arquivo para leitura
	public boolean reset() {
		try {
			inputStream = new ObjectInputStream(new FileInputStream(nomeArq));
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}
		catch (IOException e) {
			return false;
		}
	}

	// Abre para gravação, criando um novo arquivo
	public void rewrite() {
		if (outputStream != null)
			closeFile();
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(nomeArq));
		}
		catch (IOException e) {
			System.out.println("Erro ao abrir o arquivo");
			System.exit(0);
		}
	}

	// Fecha o arquivo
	public void closeFile() {
		try {
			if (outputStream != null)
				outputStream.close();
			if (inputStream != null)
				inputStream.close();
		}
		catch (IOException e) {
			System.out.println("Erro ao fechar o arquivo");
		}
		outputStream = null;
		inputStream = null;
	}

	public void flush() throws IOException {
		outputStream.flush();
	}

	// Grava um objeto no arquivo
	public void write(Serializable obj) {
		try {
			if (outputStream == null)
				System.out.println("Erro: O arquivo não foi aberto");
			else
				outputStream.writeObject(obj);
		}
		catch (IOException e) {
			System.out.println("Erro ao gravar o arquivo");
		}
	}

	// Lê um objeto do arquivo
	public Serializable read() {

		if (inputStream == null) {
			return null;
		}
		else {
			try {
				return (Serializable) inputStream.readObject();
			}
			catch (IOException e) {
				return null;
			}
			catch (ClassNotFoundException e) {
				return null;
			}
			catch (Exception e) {
				return null;
			}
		}
	}

	public boolean delete() {
		try {
			closeFile();
			File file = new File(nomeArq);
			return file.delete();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}

	}

}
