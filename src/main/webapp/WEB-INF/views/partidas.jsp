<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">
		<!-- 				<br /> -->
		<!-- Esto debe rellenarse con una tabla que contenga las partidas activas del usuario siempre que este logueado y se leeria de una base de datos. Puesto que aun no
		existe se dejara esto como idea -->
		<br />
		<br />
		<div id="tablaPartidas">
			<h1>Partidas</h1>
			<!-- Comienzo de la tabla del ranking -->
			<table class ="listaPartidas">
			<thead>
				<tr><td>Nombre<td>Estado</tr>
			</thead>
			<tbody>
				<c:forEach items="${partidas}" var = "p">
					<tr><td>${p.nombre} <td> ${p.estado}</tr>
				</c:forEach>
				<c:if test="${empty partidas}">
					<tr><td>---<td>---</tr>
				</c:if>
			</tbody>
			</table>
		</div>
		<br/><br/>
		
		<form id="crearPartida" method="POST" action="newpartida">
			<label id="nombrePartida" for="partida">Nombre de partida: </label> <input id="pNueva"
				type="text" name="pNueva" value="" placeholder="Nombre" required />
			<input id="crearPartida" type="submit" class="submit" name="crear"
				value="Crear Partida" />
			<script>
				$("#signupForm").validate();
			</script>
		</form>

	</div>
<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>
