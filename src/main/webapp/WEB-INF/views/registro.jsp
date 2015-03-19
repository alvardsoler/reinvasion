<%@ include file="../fragments/header.jspf" %>
<div id="content">
			<br />
			<div id="text">
				<h1>REGISTRO</h1>
				<br />

				<form id="signupForm" method="get" action="">
					<fieldset>
						<table id="userData" class="center">
							<tr>
								<th><label for="uname">Nombre de usuario: </label></th>
								<th><input id="uname" type="text" name="Name" value=""
									placeholder="user" required /></th>
							</tr>
							<tr>
								<th><br/></th>
							</tr>
							<tr>
								<th><label for="uemail">Correo electrónico: </label></th>
								<th><input id="uemail" type="email" name="email" value=""
									placeholder="correo@ejemplo.com" required /></th>
							</tr>
							<tr>
								<th><br/></th>
							</tr>
							<tr>
								<th><label for="upass">Clave de usuario: </label></th>
								<th><input id="upass" type="password" name="password"
									value="" placeholder="Password" required /></th>
							</tr>
							<tr>
								<th><br/></th>
							</tr>
							<tr>
								<th><label for="upass2">Repita clave de usuario: </label></th>
								<th><input id="upass2" type="password"
									name="passwordValidation" value=""
									placeholder="Repita clave de usuario" required /></th>
							</tr>
							<tr>
								<th><br/></th>
							</tr>
							<tr>
								<th><label> <input type="checkbox"
										name="remember_me" id="remember_me" /> Recordar en este
										ordenador
								</label></th>
							</tr>
						</table>
						<br/><div id="submit"><input type="submit" name="commit" value="Registrar" /></div>
					</fieldset>
				</form>
				<script>
					$("#signupForm").validate();
				</script>
				<!-- fuente http://www.granneman.com/webdev/coding/css/centertables/ -->
			</div>

			<div class="end"></div>
		</div>
<%@ include file="../fragments/footer.jspf" %>