<%@ include file="../fragments/header.jspf"%>
<script src="../resources/js/jqvmap/jquery.vmap.js"></script>
<script src="../resources/js/jqvmap/maps/jquery.vmap.usa.js"></script>
<script src="../resources/js/partida.js"></script>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">

<style>
.tab-pane {
	height: 300px;
	overflow-y: scroll;
	width: 100%;
}
</style>
</br>
<div class="container">
	<!-- <div class="container" style="background: black"> -->
	<div class="row">
		<!-- 		<div class="row-height"> -->
		<!-- <div class="col-xs-6 col-sm-3 col-sm-height col-sm-top" style="background: red;"> -->
		<div class="col-sm-3">
			<h4>Jugadores</h4>
			<ul class="list-group" id="infoJugadores">
			</ul>
		</div>
		<!-- 			<div  style="width: 600px; height: 400px;" class="center"> -->
		<div class="col-sm-6 " id="vmap" style="width: 600px; height: 400px;"></div>
		  <button type="button" class="btn btn-default" onClick="pasar()">Pasar</button>		
		<!-- <div class="col-sm-9" style="background: green"> -->
		<!-- 			</div> -->
		<!-- 		</div> -->
	</div>
	<div class="panel panel-default" style="background: grey">
		<div class="panel-body">
			<div class="tab-content">
				<div class="tab-pane active" id="msgLog">
					
					
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="idPartida" name="idPartida"
		value="${idPartida}" /> <input type="hidden" id="jsonPartida"
		value='${jsonPartida}' />
		<input type="hidden" id="usuarioId"
		value='${usuario.id}' />
</div>
<!-- </div> -->

<!-- <div id="content"> -->
<!-- 	<br /> -->
<!-- 	<div id="text"> -->
<!-- 		<h1>PARTIDA</h1> -->

<!-- 		<div id="infoJugadores"></div> -->


<%-- 		<%-- 		<script src="${prefix}resources/js/jqvmap/jquery.vmap.js"></script> --%>

<%-- 		<%-- 		<script src="${prefix}resources/js/jqvmap/maps/jquery.vmap.usa.js"></script> --%>

<%-- 		<%-- 		<script src="${prefix}resources/js/partida.js"></script> --%>



<%-- 		<p>${jsonPartida}</p> --%>
<!-- 	</div> -->
<!-- </div> -->


</div>
<%@ include file="../fragments/footer.jspf"%>