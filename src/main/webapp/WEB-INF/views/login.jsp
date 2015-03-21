<%@ include file="../fragments/header.jspf"%>


<script type="text/javascript">
	$(function() {
		$("#login").blur(function() {
			if ($("#login").val().length < 3)
				// 				$("#usernametr").append("X");
				console.log("mas letras");
		});
		$(".loguser").click(function() {
			var username = $("#login").val();
			var password = $("#pass").val();
			$.ajax({
				method : "POST",
				url : "${prefix}loginUser",
				data : {
					username : username,
					password : password
				},
				dataType : "json",
				success : function(data) {
					if (data.res == "YES")
						alert("ok");
					else
						alert("NOPE");
				}

			});

		})
	})
</script>
<div id="content">
	<br />
	<div id="text">
		<h1>LOGIN</h1>
		<br />
		<form id="signinForm">
			<fieldset>
				<table id="userData" class="center">

					<tr id="usernametr">
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
						<th><button class="loguser">Acceder</button></th>
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
						<!-- 						<th><input type="hidden" id="source" name="source" -->
						<%-- 							value="${requestScope['javax.servlet.forward.servlet_path']}" /> --%>
						<!-- 						</th> -->
					</tr>
					<c:if test="${not empty loginError}">
						<br>
						<span class='errormsg'>Login o contraseña incorrectos</span>
					</c:if>
				</table>
			</fieldset>
		</form>

	</div>

	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>
