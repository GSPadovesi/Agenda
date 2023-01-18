package controller;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Criando objetos da camada model - (DAO e JavaBeans) **/

	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if(action.equals("/insert")) {
			novoContato(request, response);
		} else if(action.equals("/select")){
			listarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}
		dao.testeConexao();
	}

	/** Listar Contatos **/
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** Criando um objeto que irá receber os dados JavaBeans **/
		ArrayList<JavaBeans> lista = dao.listarContatos();
		
		/** Encaminhar a lista ao documento agenda.jsp **/
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("Agenda.jsp");
		rd.forward(request, response);
		//response.sendRedirect("Agenda.jsp");
	}
	
	/** Novo Contato **/
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		/** Atribuindo o valor do formContato para os atributos da class JavaBeans - (nome, fone, email) **/
		this.contato.setNome(request.getParameter("nome"));
		this.contato.setFone(request.getParameter("fone"));
		this.contato.setEmail(request.getParameter("email"));
		
		this.dao.inserirContato(contato);
		response.sendRedirect("main");
	}
	
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		/** Receber o id do contato que será editado - (idcon - select) **/
		String idcon = request.getParameter("idcon");
		
		this.contato.setIdcon(idcon);
		dao.selecionarContato(contato);
		
		/** Setar os atributos do formulario com o conteudo JavaBeans **/
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		/** Encaminhar ao documento editar.jsp **/
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
}
