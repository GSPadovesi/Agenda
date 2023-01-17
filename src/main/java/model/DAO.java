package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DAO {
	/** Modulo de Conexão **/
	
	// Parametros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbappweb?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "";
	
	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** Método para testar a conexão **/
	
	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void inserirContato(JavaBeans contato) {
		String sqlInsert = "insert into contatos(nome,fone,email) values (?,?,?);";
		try {
			/** Estabelecendo a conexão e criando **/
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(sqlInsert);
			
			/** Atribuindo o valor dos atributos da class JavaBeans ao banco de dados **/
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			/** Executando atualização e fechando conexão com o banco **/
			pst.executeUpdate();
			con.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
