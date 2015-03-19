<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">
		<h1>LOGIN</h1>
		<br />
		<form id="signinForm" method="POST" action="loguser">
			<fieldset>
				<table id="userData" class="center">

					<tr>
						<th><label for="user">Nombre de usuario: </label></th>
						<th><input id="login" type="text" name="login" value=""
							placeholder="Name" required /></th>
					</tr>
					<tr>
						<th><br /></th>
					</tr>
					<tr>
						<th><label for="pass">Clave de usuario: </label></th>
						<th><input id="pass" type="password" name="pass" value=""
							placeholder="Password" required /></th>
					</tr>

					<tr>
						<th><input type="submit" class="submit" name="loguser"
							value="Acceder" /></th>
						<th><input type="button" name="lost"
							value="He perdido mi clave" /></th>
					</tr>
				</table>
				<br /> <br />
				<table class="center">
					<tr>
						<th><label> <input type="checkbox" name="remember_me"
								id="remember_me" /> Recordar en este ordenador
						</label></th>
					</tr>
					<tr>
						<th><input type="hidden" id="source" name="source"
							value="${requestScope['javax.servlet.forward.servlet_path']}" />
						</th>
					</tr>
					<c:if test="${not empty loginError}">
						<br>
						<span class='errorLogin'>Login o contraseña incorrectos</span>
					</c:if>
				</table>
			</fieldset>
		</form>

	</div>

	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>
