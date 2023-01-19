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
		String insert = "insert into contatos(nome,fone,email) values (?,?,?);";
		try {
			/**
			 * Estabelecendo a conexão e criando a query para ser executada no banco de
			 * dados
			 **/
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(insert);

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
	
	public void selecionarContato(JavaBeans contato) {
		String read = "select * from contatos where idcon = ?";
		try {
			
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				/** Setar as variaveis JavaBeans **/
				
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
				
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void editarContato(JavaBeans contato) {
		String update = "update contatos set nome=?,fone=?,email=? where idcon = ?;";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deletarContato(JavaBeans contato) {
		String excluir = "delete from contatos where idcon=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(excluir);
			
			pst.setString(1, contato.getIdcon());
			
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
