<%@ include file="../fragments/header.jspf"%>
<script>
	$()
			.ready(
					function() {
						$(".regUser").click(function() {
							var username = $("#uname").val();
							var email = $("#uemail").val();
							var password = $("#upass").val();
							var password2 = $("#upass2").val();						
							$.ajax({
								method : "POST",
								url : "${prefix}registrarUsuario",
								data : {
									username : username,
									email: email,
									password : password,
									passwordValidation: password2
								},
								dataType : "json",
								success : function(data) {
									console.log("ok");
									alert("ok");
									if (data.res == "YES")
										console.log("ok");
									else {
										console.log("nop");
									}
								}

							});
						});
						//validate signup form on keyup and submit
						$("#signupForm")
								.validate(
										{
											rules : {
												username : {
													required : true,
													minlength : 4
												},
												password : {
													required : true,
													minlength : 5
												},
												passwordValidation : {
													required : true,
													minlength : 5,
													equalTo : "#upass"
												},
												email : {
													required : true,
													email : true
												}

											},
											messages : {
												username : {
													required : "Por favor introduzca un nombre de usuario",
													minlength : "Tu nombre de usuario debe tener al menos 4 caracteres"
												},
												password : {
													required : "Por favor introduce una clave",
													minlength : "Tu clave debe tener al menos 5 carácteres"
												},
												passwordValidation : {
													required : "Por favor introduce una clave",
													minlength : "Tu clave debe tener al menos 5 carácteres",
													equalTo : "Las claves son diferentes"
												},
												email : "Por favor introduzca un email correcto"
											}
										});

					});
</script>
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
						<th><input id="uname" type="text" name="username" value=""
							placeholder="user" /></th>
					</tr>
					<tr>
						<th><br /></th>
					</tr>
					<tr>
						<th><label for="uemail">Correo electrónico: </label></th>
						<th><input id="uemail" type="email" name="email" value=""
							placeholder="correo@ejemplo.com" /></th>
					</tr>
					<tr>
						<th><br /></th>
					</tr>
					<tr>
						<th><label for="upass">Clave de usuario: </label></th>
						<th><input id="upass" type="password" name="password"
							value="" placeholder="Password" /></th>
					</tr>
					<tr>
						<th><br /></th>
					</tr>
					<tr>
						<th><label for="upass2">Repita clave de usuario: </label></th>
						<th><input id="upass2" type="password"
							name="passwordValidation" value=""
							placeholder="Repita clave de usuario" /></th>
					</tr>
					<tr>
						<th><br /></th>
					</tr>
					<tr>
						<th><label> <input type="checkbox" name="remember_me"
								id="remember_me" /> Recordar en este ordenador
						</label></th>
					</tr>
				</table>
				<br />
				<div id="submit">
					<button class="regUser">Registrar</button>
				</div>
			</fieldset>
		</form>
		<!-- fuente http://www.granneman.com/webdev/coding/css/centertables/ -->
	</div>

	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>