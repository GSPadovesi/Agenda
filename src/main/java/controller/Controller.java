package controller;

import java.io.IOException;


import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
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
		} else if(action.equals("/update")) {
			editarContato(request, response);
		} else if(action.equals("/delete")) {
			deletarContato(request, response);
		} else if(action.equals("/report")) {
			gerarRelatorio(request, response);
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
	
	/** setar campos da pagina de editar com as informações do idcon **/
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
	
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.contato.setIdcon(request.getParameter("idcon"));
		this.contato.setNome(request.getParameter("nome"));
		this.contato.setFone(request.getParameter("fone"));
		this.contato.setEmail(request.getParameter("email"));
		
		this.dao.editarContato(contato);
		response.sendRedirect("main");
		
		}
	
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		//recebimento do id do contato a ser excluido (Confirmador.js)
		String idcon = request.getParameter("idcon");
		
		this.contato.setIdcon(idcon);
		
		this.dao.deletarContato(contato);
		response.sendRedirect("main");
				
	}
	
	/** Método para gerar relatório em PDF **/
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Document documento = new Document();
		try {
			/** Tipo de conteudo **/
			response.setContentType("apllication/pdf");
			
			/** Nome do Documento **/
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			
			/** Criar Documento **/
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			/** Abrir documento -> conteudo **/
			documento.open();
			documento.add(new Paragraph("Lista de Contatos:"));
			documento.add(new Paragraph(" "));
			
			/** Criar a tabela **/
			PdfPTable tabela = new PdfPTable(3);
			
			/** Cabeçalho **/
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			
			/** Adicionando os cabeçalhos na tabela **/
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			/** Adicionando os contatos na tabela **/
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
