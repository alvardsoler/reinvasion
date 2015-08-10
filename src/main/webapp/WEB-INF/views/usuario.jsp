<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">
		<h1>USUARIO</h1>
	</div>
	<br />
	<c:choose>
		<c:when test="${username == userView.login }">
			<form id="signupForm" method="get" action="">
				<fieldset>
					<div id="userImage">
						<img src="${prefix}usuario/photo?id=${userView.id}" /> 
					</div>
					<table id="userData" class="center">
						<tr>
							<th>Nombre usuario:</th>
							<th><input type="text" id="user" value="${userView.login}"
								placeholder="Name" required disabled="true"/></th>
						</tr>
						<tr>
							<th><br /></th>
						</tr>
						<tr>
							<th>Correo electrónico:</th>
							<th><input type="email" id="email" value="${userView.email}"
								placeholder="Email" required /></th>
						</tr>
						<tr>
							<th><br /></th>
						</tr>
						<tr>
							<th>Contraseña:</th>
							<th><input type="password" id="password" value=""
								placeholder="Password" required /></th>
						</tr>

					</table>
					<br /> <br />
					
					<div id="submit">
						<button class="updateUser">Modificar</button>
					</div>
				</fieldset>
			</form>

			<script>
				$("#signupForm").validate();
			</script>
			<div id="submit1">
				<form method="POST" enctype="multipart/form-data"
					action="${prefix}usuario">
					<input type="file" name="photo"> <input hidden="submit"
						name="id" value="${userView.id}" />
					<button type="submit" name="upload" value="ok">Actualizar</button>
				</form>

			</div>
			<input type="hidden" name="usuario" value="${sessionScope.usuario.id} " />
		</c:when>
		<c:otherwise>
			<table id="userData" class="center">
				<tr>
					<th>Nombre usuario:</th>
					<th>${userView.username}</th>
				</tr>
				<tr>
					<th><br /></th>
				</tr>
				<tr>
					<th>Correo electrónico:</th>
					<th>${userView.email}</th>
				</tr>
			</table>
			<div id="userImage">
				<img src="${prefix}usuario/photo?id=${userViewed.id}" /> 
			</div>
		</c:otherwise>
	</c:choose>
	<br /> <br />

	<script type="text/javascript">
		$(function() {
			$(".updateUser").click(function() {
				var emailUser = $("#email").val();
				var username = $("#user").val();
				var password = $("#password").val();
				// var idUser = document.getElementsByName("usuario")[0].value.trim();
				$.ajax({
					method : "POST",
					url : "${prefix}updateUser",
					data : {
						username : username,
						emailUser : emailUser,
						passUser : password
					},
					dataType : "json",
					success : function(data) {
						if (data.res == "YES") {
							alert("Sus datos han sido actualizados");
							window.location.href = "usuario/" + username;
							setTimeout(function() {
								location.reload();
							}, 0001);
						} else {
							alert("No se han podido actualizar los datos");
						}

					}
				})
			})

		});
	</script>

</div>

<div class="end"></div>
<!-- </div> -->
<%@ include file="../fragments/footer.jspf"%>