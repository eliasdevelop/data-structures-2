package provaUnidadeII;

public class Pessoa implements Comparable<Pessoa>{
	
	private int codigo;
	private String nome;
	private float salario;
	private int idade;
	
	public Pessoa(int codigo, String nome, float salario, int idade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.salario = salario;
		this.idade = idade;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	@Override
	public int compareTo(Pessoa p1) {
		if(p1.getCodigo() == getCodigo()){
			return 0;
		}else if(p1.getCodigo() > getCodigo()){
			return 1;
		}else{
			return -1;
		}
	}
	
		
	
}
