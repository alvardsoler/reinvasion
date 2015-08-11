<%@ include file="../fragments/header.jspf"%>

<div id="content">
	<br />
	<div id="text">
		<h1>PARTIDA</h1>
		
		<div id="infoJugadores"></div>
		<div id="vmap" style="width: 600px; height: 400px;" class="center"></div>
		
		<%-- 		<script src="${prefix}resources/js/jqvmap/jquery.vmap.js"></script> --%>
		<%-- 		<script src="${prefix}resources/js/jqvmap/maps/jquery.vmap.usa.js"></script> --%>
		<%-- 		<script src="${prefix}resources/js/partida.js"></script> --%>
		<script src="../resources/js/jqvmap/jquery.vmap.js"></script>
		<script src="../resources/js/jqvmap/maps/jquery.vmap.usa.js"></script>
		<script src="../resources/js/partida.js"></script>
		<input type="hidden" id="idPartida" name="idPartida" value="${idPartida}" />
		<input type="hidden" id="jsonPartida" value='${jsonPartida}' />
		<p>${jsonPartida}</p>
	</div>
</div>

<div class="end"></div>
</div>
<%@ include file="../fragments/footer.jspf"%>