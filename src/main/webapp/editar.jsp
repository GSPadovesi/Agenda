<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de Contatos</title>
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="icon" href="imagens/fone.png">
</head>
<body>
	<h1>Editar Contato</h1>
	<form name="formContato" action="update">
		<table>
			<tr>
				<td><input type="text" name="idcon" id="Input3" readonly
					value="<%out.print(request.getAttribute("idcon"));%>"></td>
			</tr>

			<tr>
				<td><input type="text" name="nome" class="Input1"
					value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>

			<tr>
				<td><input type="text" name="fone" class="Input2"
					value="<%out.print(request.getAttribute("fone"));%>"></td>
			</tr>

			<tr>
				<td><input type="text" name="email" class="Input1"
					value="<%out.print(request.getAttribute("email"));%>"></td>
			</tr>
		</table>
		<input type="button" value="Editar" class="Botao1" onclick="validar()">
		<!--  <input type="button" value="Excluir" class="botao1" onclick="validar()"> -->
	</form>
	<script src="script/validador.js"></script>
</body>
</html>