<%@ include file="../fragments/header.jspf"%>

<script type="text/javascript">
	$(document).ready(function() {
		$('#btnDel').click(function() {
			var username = $('#nameDel').val();
				$.ajax({
					method : "POST",
					url : "${prefix}delUser",
					data : {
						username : username,
						csrf : "${csrf_token}"
					},
					dataType : "json",
					success : function(data) {
						if (data.res == "YES") {
							alert("Usuario eliminado");
							setTimeout(function() {
								location.reload();
							}, 0001);
						} else {
							alert("Error al eliminar el usuario");
						}
					}

				});
			});
		$('#btnDelPartida').click(function() {
			var gamename = $('#nameDelPartida').val();
				$.ajax({
					method : "POST",
					url : "${prefix}delGame",
					data : {
						gamename : gamename,
						csrf : "${csrf_token}"
					},
					dataType : "json",
					success : function(data) {
						if (data.res == "YES") {
							alert("Partida eliminada");
							setTimeout(function() {
								location.reload();
							}, 0001);
						} else if(data.res == "NOPEA"){
							alert("No tiene permiso para eliminar la partida");
						}
						else{
							alert("Error al eliminar la partida");
						}
					} 
					
				});
		});
	});

</script>

<div id="content">
	<br />
	<div id="text">

		<%-- 		<c:if test="${usuario.rol != 'admin' }"> --%>
		<%-- 			<c:redirect url="/"></c:redirect> --%>
		<%-- 		</c:if> --%>
		<h1>ADMIN</h1>
		
		<h1>Usuarios del sistema</h1>
		<table class="tableAdminUsers">
		<thead>
			<tr><td>Id<td>Login<td>Rol<td>Email<td>Puntos<td>Avatar</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="u">
				<tr><td>${u.id}<td>${e:forHtmlContent(u.login)}<td>${u.rol}
				<td>${u.email}<td>${u.puntos}<td><img src="usuario/photo?id=${u.id}"/>
			</c:forEach>
		</tbody>	
		</table>

		<div id="delete">
			<label>Usuario para borrar: </label><input type="text" id="nameDel" />
			<input type="button" id="btnDel" value="Eliminar" />
		</div>
		
		<h1>Partidas</h1>
		<table class="tableAdminGames">
		<thead>
			<tr><td>Id</td><td>Nombre</td><td>Creador</td><td>Estado</td><td>FechaInicio</td></tr>
		</thead>
		<tbody>
			<c:forEach items="${games}" var="g">
				<tr><td>${g.id}<td>${e:forHtmlContent(g.nombre)}<td>---
				<td>${g.estado}<td>--- </tr>
			</c:forEach>
			<c:if test="${empty games}">
						<tr>
							<td>---
							<td>---
							<td>---
							<td>---
							<td>---
						</tr>
					</c:if>
		</tbody>	
		</table>
		
		<div id="delete">
			<label>Partida que desea borrar: </label><input type="text" id="nameDelPartida" />
			<input type="button" id="btnDelPartida" value="Eliminar" />
		</div>
		

		<div class="end"></div>
	</div>
	<%@ include file="../fragments/footer.jspf"%>