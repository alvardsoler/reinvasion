<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">
		<h1>RANKING</h1>
		<!-- Comienzo de la tabla del ranking -->

		<div id="tableRanking" class="center"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#tableRanking').jtable({
					title : 'Ranking de usuarios',

					actions : {
						listAction : 'InvasionServlet?action=listRanking'
					},
					fields : {
						UserID : {
							key : true,
							list : false
						},
						Username : {
							title : 'Username',
							width : '20%'
						},
						Puntos : {
							title : 'Puntos',
							width : '20%'
						}
					}
				});
				$('#tableRanking').jtable('load');

			});
		</script>
	</div>

	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>