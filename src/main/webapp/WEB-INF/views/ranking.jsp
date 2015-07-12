<%@ include file="../fragments/header.jspf"%>
<div id="content">
	<br />
	<div id="text">
		<h1>RANKING</h1>
		<!-- Comienzo de la tabla del ranking -->

		<div id="tableRanking" class="center"></div>
		<table class= "ranking">
			<thead>
					<tr><td>UserId<td>Username<td>Puntos</tr>
			</thead>
			<tbody>
				<c:forEach items="${allUsers}" var = "users">
					<tr><td>${users.id} <td> ${users.login} <td>${users.puntos}</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>