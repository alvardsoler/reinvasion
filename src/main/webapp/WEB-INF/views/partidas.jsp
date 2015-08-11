<%@ include file="../fragments/header.jspf"%>

<script type="text/javascript">
	$().ready(function() {
		$("#crearPartida").click(function() {
			var nombrePartida = $("#pNueva").val();
			$.ajax({
				method : "POST",
				url : "${prefix}nuevaPartida",
				data : {
					nombrePartida : nombrePartida
				},
				dataType : "json",
				success : function(data) {
					if (data.res == "YES") {
						alert("¡Partida creada con éxito!");
						setTimeout(function() {
							location.reload();
						}, 0001);
					}

				}
			});
			return false;
		})

		// 				$("#unirsePartida").click(function(){
		// 					var nombrePartida = $("#pUnirse").val();
		// 					$.ajax({
		// 						method : "POST",
		// 						url : "${prefix}unirsePartida",
		// 						data : {
		// 							nombrePartida : nombrePartida
		// 						},
		// 						dataType : "json",
		// 						success : function(data) {
		// 							console.log("ok");
		// 							return null;
		// 						}
		// 					})
		// 				})

	});

	function unirsePartida(partida) {
		$.ajax({
			method : "POST",
			url : "${prefix}unirsePartida",
			data : {
				idPartida : partida
			},
			dataType : "json",
			success : function(data) {
				console.log("ok");
				setTimeout(function() {
					location.reload();
				}, 0001);
				return null;
			}
		});
	}

	function accederPartida(partida) {
		window.location.href = "${prefix}partida/" + partida;
		// 		$.ajax({
		// 			method : "POST",
		// 			url : "${prefix}accederPartida",   
		// 			data : {
		// 				idPartida : partida
		// 			},
		// 			dataType : "json",
		// 			success : function(data) {
		// 				if (data.res == "YES") {
		// 					window.location.href = "${prefix}partida/" + partida;
		// 				}
		// 				else{
		// 					alert("No se han unido aun todos los usuarios. Espere");
		// 				}
		// 			}
		// 		})

	}
</script>
<div id="content">
	<br />
	<div id="text">
		<!-- 				<br /> -->
		<!-- Esto debe rellenarse con una tabla que contenga las partidas activas del usuario siempre que este logueado y se leeria de una base de datos. Puesto que aun no
		existe se dejara esto como idea -->
		<br /> <br />
		<div id="tablaPartidas">
			<h1>Partidas</h1>
			<!-- Comienzo de la tabla del ranking -->
			<table class="listaPartidas">
				<h2>Partida en las que estás</h2>
				<thead>
					<tr>
						<td>Nombre
						<td>Estado
						<td>Acceder
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${partidasUnido}" var="p">

						<tr>
							<td>${p.nombre}
							<td>${p.estado}
							<td><button onclick="accederPartida('${p.id}')">Acceder</button>
						</tr>
					</c:forEach>
					<c:if test="${empty partidasUnido}">
						<tr>
							<td>---
							<td>---
							<td>---
						</tr>
					</c:if>
				</tbody>
			</table>
			<table class="listaPartidas">
				<h2>Partidas a las que te puedes unir</h2>
				<thead>
					<tr>
						<td>Nombre
						<td>Estado
						<td>Unirse
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${restoPartidas}" var="rp">
						<tr>
							<td>${rp.nombre}
							<td>${rp.estado}
							<td><button onclick="unirsePartida('${rp.id}')">Unirse</button>
						</tr>
					</c:forEach>
					<c:if test="${empty restoPartidas}">
						<tr>
							<td>---
							<td>---
							<td>---
						</tr>
					</c:if>
				</tbody>
			</table>


		</div>
		<br /> <br />

		<form id="crearPartidaForm">
			<label id="nombrePartida" for="partida">Nombre de partida: </label> <input
				id="pNueva" type="text" name="pNueva" value="" placeholder="Nombre"
				required /> <input id="crearPartida" type="submit" class="submit"
				name="crear" value="Crear Partida" />
		</form>

	</div>
	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>
