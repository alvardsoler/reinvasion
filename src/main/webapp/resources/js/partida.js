var partida;
$(function() {
	$('#vmap').vectorMap({
		map : 'usa_en',
		backgroundColor : '#a5bfdd',
		borderColor : '#818181',
		borderOpacity : 0.25,
		borderWidth : 1,
		color : '#f4f3f0',
		enableZoom : false,
		hoverOpacity : false,
		hoverColor : false,
		normalizeFunction : 'linear',
		showTooltip : true,
		onRegionClick : function(element, code, region) {
			clickPais(nombreAid[code]);
			return false;
		},
		onRegionOver : function(event, e, code, region) {
			// console.log(code,e.pageX,"_",e.pageY);
			return false;
		},

		onLabelShow : function(event, label, code) {
			label.text(code + " " + [ nombreAid[code] ]);
		},
		onRegionOut : function(element, code, region) {
			coloreaPaises();
		}
	});
	partida = JSON.parse(document.getElementById('jsonPartida').value);
	for (var i = 0; i < partida.paises.length; i++) {
		if (nombreAcoordenada[idAnombre[i]] == undefined)
			console.log(i);
	}
	dibujarInfoJugadores();
	coloreaPaises();
	muestraUnidades();
});

function colorea(idPais, color) {
	var o = {};
	o[idAnombre[idPais]] = color;
	console.log('set', 'colors', o);
	$('#vmap').vectorMap('set', 'colors', o);
}

// jsonRecibido[0] despues de JSON.parse()
// var partida = {
// "jugadores" : [
// {
// "id" : 0,
// "paisesControlados" : [ 43, 2, 5, 0, 13, 23, 34, 29, 9, 18, 42,
// 47, 1, 28, 25, 27, 15 ],
// "cartas" : [],
// "unidadesSinDesplegar" : 0,
// "color" : "azul"
// },
// {
// "id" : 1,
// "paisesControlados" : [ 6, 31, 49, 38, 48, 10, 14, 21, 19, 4,
// 40, 39, 7, 36, 11, 33, 17 ],
// "cartas" : [],
// "unidadesSinDesplegar" : 0,
// "color" : "rojo"
// },
// {
// "id" : 2,
// "paisesControlados" : [ 37, 30, 32, 46, 22, 45, 20, 12, 16, 24,
// 41, 3, 8, 35, 26, 44 ],
// "cartas" : [],
// "unidadesSinDesplegar" : 0,
// "color" : "blanco"
// } ],
// "paises" : [ {
// "id" : 0,
// "paisesFrontera" : [ 36, 46 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 1,
// "paisesFrontera" : [ 24, 41, 9, 8 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 2,
// "paisesFrontera" : [ 17, 42, 35, 23, 41, 24 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 3,
// "paisesFrontera" : [ 4, 32, 43, 5, 31 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 4,
// "paisesFrontera" : [ 32, 3, 36 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 5,
// "paisesFrontera" : [ 49, 43, 3, 31, 35, 15, 28 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 6,
// "paisesFrontera" : [ 33, 18, 38 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 7,
// "paisesFrontera" : [ 19, 37, 30 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 8,
// "paisesFrontera" : [ 1, 9 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 9,
// "paisesFrontera" : [ 1, 41, 26, 39, 8 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 10,
// "paisesFrontera" : [ 42, 3 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 11,
// "paisesFrontera" : [ 23, 14, 47, 22, 40, 28 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 12,
// "paisesFrontera" : [ 25, 46, 36, 32, 49, 43 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 13,
// "paisesFrontera" : [ 16, 14, 47, 11 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 14,
// "paisesFrontera" : [ 13, 16, 34, 21 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 15,
// "paisesFrontera" : [ 35, 2, 23, 11, 28, 5 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 16,
// "paisesFrontera" : [ 41, 44, 48, 34, 14, 13, 23 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 17,
// "paisesFrontera" : [ 42, 2, 24 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 18,
// "paisesFrontera" : [ 38, 6, 33, 45, 29 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 19,
// "paisesFrontera" : [ 44, 48, 37, 7 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 20,
// "paisesFrontera" : [ 29 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 21,
// "paisesFrontera" : [ 47, 14, 34 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 22,
// "paisesFrontera" : [ 27, 40, 11, 47 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 23,
// "paisesFrontera" : [ 2, 41, 16, 13, 11, 28, 15, 35 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 24,
// "paisesFrontera" : [ 17, 2, 41, 1 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 25,
// "paisesFrontera" : [ 12, 49, 40, 27 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 26,
// "paisesFrontera" : [ 44, 41, 9, 39 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 27,
// "paisesFrontera" : [ 25, 40, 22 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 28,
// "paisesFrontera" : [ 49, 5, 15, 23, 11, 40 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 29,
// "paisesFrontera" : [ 45, 18, 20 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 30,
// "paisesFrontera" : [ 7, 37, 33 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 31,
// "paisesFrontera" : [ 3, 43, 5, 35, 42 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 32,
// "paisesFrontera" : [ 36, 4, 3, 43, 12 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 33,
// "paisesFrontera" : [ 37, 30, 6, 18, 45 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 34,
// "paisesFrontera" : [ 21, 14, 16, 48, 37 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 35,
// "paisesFrontera" : [ 42, 17, 2, 23, 15, 31, 5 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 36,
// "paisesFrontera" : [ 46, 4, 32, 12, 0 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 37,
// "paisesFrontera" : [ 34, 48, 19, 30, 33, 7 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 38,
// "paisesFrontera" : [ 6, 18 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 39,
// "paisesFrontera" : [ 9, 26 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 40,
// "paisesFrontera" : [ 27, 25, 49, 28, 11, 22 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 41,
// "paisesFrontera" : [ 24, 1, 9, 26, 44, 16, 23, 2 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 42,
// "paisesFrontera" : [ 31, 35, 17 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 43,
// "paisesFrontera" : [ 32, 12, 3, 31, 5, 49 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 44,
// "paisesFrontera" : [ 26, 16, 48, 19 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 45,
// "paisesFrontera" : [ 33, 18, 29 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 46,
// "paisesFrontera" : [ 12, 36, 0 ],
// "unidades" : 1,
// "propietario" : 2
// }, {
// "id" : 47,
// "paisesFrontera" : [ 13, 21, 22, 11 ],
// "unidades" : 1,
// "propietario" : 0
// }, {
// "id" : 48,
// "paisesFrontera" : [ 34, 16, 44, 19, 37 ],
// "unidades" : 1,
// "propietario" : 1
// }, {
// "id" : 49,
// "paisesFrontera" : [ 12, 43, 5, 28, 40, 25 ],
// "unidades" : 1,
// "propietario" : 1
// } ],
// "turno" : 1,
// "jugadorActivo" : 0,
// "fin" : false
// };

// jsonRecibido[1] despues de JSON.parse()
var idUsuario = 0;

function esFrontera(unId, otroId) {
	return partida.paises[unId].paisesFrontera.indexOf(otroId) != -1;
}
function esEnemigo(idPais) {
	return partida.paises[idPais].propietario != idUsuario;
}

function clickPais(idPais) {
	if (estado.actual == estado.ataque) {
		if (esEnemigo(idPais)) {
			estado.actual.clickSuyo(idPais);
		} else {
			estado.actual.clickMio(idPais);
		}
		coloreaPaises();
	} else if (estado.actual == estado.movimiento) {
		if (esEnemigo(idPais)) {
			console.log("No se puede mover sobre paises enemigos");
		} else if (estado.idSel === undefined) {
			estado.actual.clickOrigen(idPais);
		} else
			estado.actual.clickDestino(idPais);
	}
}
var op = {};

var estado = {
	"ataque" : {
		tipo : "ataque",
		clickMio : function(id) {
			if (estado.idSel === undefined || id !== estado.idSel) {
				estado.idSel = id;
			} else {
				// click en seleccionado: deselecciona
				estado.idSel = undefined;
			}
		},
		clickSuyo : function(id) {
			if (estado.idSel === undefined) {
				return;
			} else if (esFrontera(id, estado.idSel)) {
				console.log("atacando de ", estado.idSel, id);
				op.op1 = estado.idSel;
				op.op2 = id;
				op.operacion = "ataque";
				console.log(op);
				sendInfo();
				// $("#info").append("atacando de ", estado.idSel," a ", id,
				// "<br>");
			}
		}
	},
	"movimiento" : {
		tipo : "movimiento",
		clickOrigen : function(id) {
			if (estado.idSel === undefined || id !== estado.idSel) {
				estado.idSel = id;
			} else {
				estado.idSel = undefined;
			}
		},
		clickDestino : function(id) {
			if (estado.idSel === undefined) {
				return;
			} else if (esFrontera(id, estado.idSel)) {
				op.op1 = estado.idSel;
				op.op2 = id;
				op.operacion = "movimiento";
				console.log(op);
				console.log("moviendo unidades de ", estado.idSel, id);
				sendInfo();
			}
		}
	},
	"despliegue" : {
		tipo : "despliegue",
		clickDestino : function(id) {
			if (estado.idSel === undefined || id !== estado.idSel) {
				estado.idSel = id;
				op.op1 = id;
				op.op2 = undefined;
				op.operacion = "ataque";
				console.log(op);
				sendInfo();
			}
		}

	},
	idSel : undefined,
	actual : undefined
};
estado.actual = estado.movimiento;

var idAnombre = {}

var nombreAcoordenada = {
	"ak" : [ 70, 320 ],
	"al" : [ 410, 270 ],
	"ar" : [ 342, 243 ],
	"az" : [ 130, 235 ],
	"ca" : [ 50, 190 ],
	"co" : [ 200, 180 ],
	"ct" : [ 535, 119 ],
	"de" : [ 520, 163 ],
	"fl" : [ 480, 330 ],
	"ga" : [ 450, 270 ],
	"hi" : [ 190, 350 ],
	"ia" : [ 325, 147 ],
	"id" : [ 120, 105 ],
	"il" : [ 370, 170 ],
	"in" : [ 403, 170 ],
	"ks" : [ 278, 198 ],
	"ky" : [ 425, 195 ],
	"la" : [ 345, 295 ],
	"md" : [ 500, 160 ],
	"ma" : [ 540, 102 ],
	"me" : [ 555, 60 ],
	"mi" : [ 408, 105 ],
	"mn" : [ 316, 93 ],
	"mo" : [ 340, 195 ],
	"ms" : [ 378, 270 ],
	"mt" : [ 180, 70 ],
	"nc" : [ 490, 213 ],
	"nd" : [ 260, 72 ],
	"ne" : [ 265, 155 ],
	"nh" : [ 538, 90 ],
	"nj" : [ 524, 144 ],
	"nv" : [ 85, 160 ],
	"nm" : [ 190, 240 ],
	"ny" : [ 507, 108 ],
	"oh" : [ 440, 163 ],
	"ok" : [ 290, 238 ],
	"or" : [ 65, 90 ],
	"pa" : [ 490, 140 ],
	"ri" : [ 548, 119 ],
	"sc" : [ 475, 240 ],
	"sd" : [ 260, 114 ],
	"tn" : [ 408, 220 ],
	"tx" : [ 270, 295 ],
	"ut" : [ 140, 175 ],
	"va" : [ 493, 185 ],
	"vt" : [ 526, 82 ],
	"wa" : [ 80, 45 ],
	"wi" : [ 362, 108 ],
	"wv" : [ 462, 170 ],
	"wy" : [ 190, 125 ]

};

var nombreAid = {
	"ak" : 0,
	"al" : 1,
	"ar" : 2,
	"az" : 3,
	"ca" : 4,
	"co" : 5,
	"ct" : 6,
	"de" : 7,
	"fl" : 8,
	"ga" : 9,
	"hi" : 10,
	"ia" : 11,
	"id" : 12,
	"il" : 13,
	"in" : 14,
	"ks" : 15,
	"ky" : 16,
	"la" : 17,
	"ma" : 18,
	"md" : 19,
	"me" : 20,
	"mi" : 21,
	"mn" : 22,
	"mo" : 23,
	"ms" : 24,
	"mt" : 25,
	"nc" : 26,
	"nd" : 27,
	"ne" : 28,
	"nh" : 29,
	"nj" : 30,
	"nm" : 31,
	"nv" : 32,
	"ny" : 33,
	"oh" : 34,
	"ok" : 35,
	"or" : 36,
	"pa" : 37,
	"ri" : 38,
	"sc" : 39,
	"sd" : 40,
	"tn" : 41,
	"tx" : 42,
	"ut" : 43,
	"va" : 44,
	"vt" : 45,
	"wa" : 46,
	"wi" : 47,
	"wv" : 48,
	"wy" : 49
};

var colores = [ 'rgb(27,158,119)', 'rgb(217,95,2)', 'rgb(117,112,179)',
		'rgb(231,41,138)', 'rgb(102,166,30)', 'rgb(230,171,2)',
		'rgb(166,118,29)', 'rgb(102,102,102)' ];

for ( var clave in nombreAid) {
	idAnombre[nombreAid[clave]] = clave;
}

function coloreaPaises() {
	var o = {};
	for (var i = 0; i < partida.paises.length; i++) {
		var p = partida.paises[i];
		o[idAnombre[p.id]] = (p.id === estado.idSel) ? "#ffff00"
				: colores[p.propietario];
		// partida.jugadores[p.propietario].color;
	}
	$('#vmap').vectorMap('set', 'colors', o);
};

//for (var i = 0; i < partida.paises.length; i++) {
//	if (nombreAcoordenada[idAnombre[i]] == undefined)
//		console.log(i);
//};

function muestraUnidades() {
	for (var i = 0; i < partida.paises.length; i++) {
		var p = partida.paises[i];
		var coord = nombreAcoordenada[idAnombre[p.id]];
		// $("#vmap").append("<div class='info' style='top: " + coord[1] +"px;
		// left: "+coord[0]+"px'>"+ idAnombre[p.id] +": " + p.unidades +
		// "</div>");
		if (coord == undefined)
			console.log(p);
		$("#vmap").append(
				"<div class='info' style='top: " + coord[1] + "px; left: "
						+ coord[0] + "px'>" + p.unidades + "</div>");
	}
};

var idPartida = 0;

function sendInfo() {
	console.log(op);
	op.idPartida = idPartida;
	console.log(op);
	$.ajax({
		url : "postPartida",
		type : 'POST',
		dataType : 'json',
		data : JSON.stringify(op),
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
}

// $(document).ready(function(){
// idPartida = $("#idPartida").val();
//	  
// });
function dibujarInfoJugadores() { // habrá que cambiarlo por el nombre del jugador
	var div = document.getElementById('infoJugadores');
	div.textContent = "";
	for (var i = 0; i < partida.jugadores.length; i++){
		if (i == partida.jugadorActivo) div.innerHTML = div.innerHTML +  "-> " + partida.jugadores[i].id + "<br\>";
		else div.innerHTML = div.innerHTML +  + "   " + partida.jugadores[i].id + "<br\>";
	}

}

function getInfo() {
	var idPartida = document.getElementById('idPartida').value;
	console.log(idPartida);
	var url = "../partida/" + idPartida;
	$.get(url).done(function(json) {
		partida = JSON.parse(document.getElementById('jsonPartida').value);		
		dibujarInfoJugadores();
		window.setTimeout("getInfo()", 3000);				
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("Request Failed: " + err);
	});

}
window.setTimeout("getInfo()", 3000);