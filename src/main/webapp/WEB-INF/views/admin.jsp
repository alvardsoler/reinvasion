<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">

		<c:if test="${role != 'admin' }">
			<c:redirect url="/"></c:redirect>
		</c:if>
		<h1>ADMIN</h1>

		<div id="tablaUsuarios"></div>
		<div id="tablaPartidas"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tablaUsuarios').jtable({
					title : 'Usuarios',
					paging : true,
					selecting : true,
					sorting : true,
					actions : {
						listAction : 'InvasionServlet?action=listAllUsers',
						createAction : 'InvasionServlet?action=createUser',
						updateAction : 'InvasionServlet?action=updateUser',
						deleteAction : 'InvasionServlet?action=deleteUser'
					},
					fields : {
						UserID : {
							key : true,
							list : false
						},
						Username : {
							title : 'Username',
							width : '10%'
						},
						Rol : {
							title : 'Rol',
							width : '5%'
						},
						Email : {
							title : 'Email',
							width : '20%'
						},												
						Puntos : {
							title : 'Puntos',
							width : '20%'
						}
					}
				});

				$('#tablaPartidas').jtable({
					title : "Partidas",
					paging : true,
					selecting : true,
					sorting : true,
					actions : {
						listAction : 'InvasionServlet?action=listAllPartidas',
						createAction : 'InvasionServlet?action=createPartida',
						updateAction : 'InvasionServlet?action=updatePartida',
						deleteAction : 'InvasionServlet?action=deletePartida'
					},
					fields : {
						id : {
							key : true,
							list : false
						},
						nombre : {
							title : 'Partida',
							width : '20%'
						},
						jugadores : {
							title : 'Jugadores',
							width : '30%'
						},
						creador : {
							title : 'Creador',
							width : '5%'
						},
						estado : {
							title : 'Estado de la partida',
							width : '5%'
						},
						fechaInicio : {
							title : 'Iniciada',
							width : '5%',
							type : 'date',
							displayFormat : 'dd-mm-yy'
						},
						fechaFin : {
							title : 'Acabada',
							width : '5%',
							type : 'date',
							displayFormat : 'dd-mm-yy'
						}

					}

				});
				$('#tablaUsuarios').jtable('load');
				$('#tablaPartidas').jtable('load');
			});
		</script>


		<div class="end"></div>
	</div>
	<%@ include file="../fragments/footer.jspf"%>