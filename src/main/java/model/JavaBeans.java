package model;

public class JavaBeans {
	private String idcon;
	private String nome;
	private String fone;
	private String email;
	
	public JavaBeans() {
		super();
	}
	
	public JavaBeans(String idcon, String nome, String fone) {
		super();
		this.idcon = idcon;
		this.nome = nome;
		this.fone = fone;
	}
	
	public String getIdcon() {
		return this.idcon;
	}
	
	public void setIdcon(String idcon) {
		this.idcon = idcon;
	}
	
	public String nome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getFone() {
		return this.fone;
	}
	
	public void setFone(String fone) {
		this.fone = fone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
