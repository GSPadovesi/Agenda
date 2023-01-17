package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<JavaBeans> listarContatos() {
		/** Criando objeto para acessar a class JavaBeans **/
		ArrayList<JavaBeans> contato = new ArrayList<>();

		String read = "select * from contatos order by idcon";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);

			/**
			 * Criando objeto para executar a query "read" - ResultSet serve para armazenar
			 * o retorno do banco de dados temporariamente em um objeto
			 **/

			ResultSet rs = pst.executeQuery();

			/**
			 * o while será executado somente enquanto houver contatos - next() é utilizado
			 * para recuperar os contato do banco
			 **/
			while (rs.next()) {

				/** Stributos de apoio que recebem os dados do banco **/
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				/** Populando o ArrayList **/
				contato.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contato;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}

	public void inserirContato(JavaBeans contato) {
		String sqlInsert = "insert into contatos(nome,fone,email) values (?,?,?);";
		try {
			/**
			 * Estabelecendo a conexão e criando a query para ser executada no banco de
			 * dados
			 **/
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(sqlInsert);

			/** Atribuindo o valor dos atributos da class JavaBeans ao banco de dados **/
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			/** Executando atualização e fechando conexão com o banco **/
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
