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
			<script type="text/javascript">
			var d;
				$(document)
						.ready(
								function() {
									$('#tablaPartidas')
											.jtable(
													{
														actions : {
															listAction : 'InvasionServlet?action=listAllPartidas',
															createAction : 'InvasionServlet?action=createPartida',
															updateAction : 'InvasionServlet?action=updatePartida',
															deleteAction : 'InvasionServlet?action=deletePartida'
														},
														recordsLoaded : function(
																event, data) {															
															$(
																	'.jtable-data-row')
																	.click(
																			function() {																				
																				d= data;
																				d.records[0].nombre;
																				$.ajax({
																					url : "partidas/addusergame/" + d.records[0].nombre,
																					type : 'POST',
																					dataType : 'json',		
																					contentType : 'application/json',
																					mimeType : 'application/json',
																					success : function(data) {
																						alert("win");
																						
																						// nos llega info asi q deberíamos repintar el mapa con el resultado
																						// de esto

																					},
																					error : function(data, status, er) {
																						alert("error: " + data + " status: " + status + " er:" + er);
																					}
																				});
																				
																				
																				
																			});
														},
														fields : {
															nombre : {
																title : 'Name',
																width : '20%'
															},
															estado : {
																title : 'Estado de la partida',
																width : '5%'
															}

														}
													});
									$('#tablaPartidas').jtable('load');

								});
			</script>
		</div>

		<form id="crearPartida" method="POST" action="newpartida">

			<label for="partida">Nombre de partida: </label> <input id="pNueva"
				type="text" name="pNueva" value="" placeholder="Nombre" required />
			<input id="crearPartida" type="submit" class="submit" name="crear"
				value="Crear Partida" />
			<script>
				$("#signupForm").validate();
			</script>
		</form>

	</div>

</div>

<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>
