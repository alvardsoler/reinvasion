var partida;
var msPolling = 10000;
var idUsuario;
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
	idUsuario = document.getElementById("usuarioId").value;
	partida.jugadores[getUserPosition(idUsuario)].unidadesSinDesplegar == 0 ? estado.actual = estado.movimiento
			: estado.actual = estado.despliegue;
	// estado.actual = estado.despliegue;
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

function esFrontera(unId, otroId) {
	return partida.paises[unId].paisesFrontera.indexOf(otroId) != -1;
}
function esEnemigo(idPais) {
	return partida.paises[idPais].propietario != idUsuario;
}
function esJugadorActivo(){
	return idUsuario == partida.jugadorActivo;
}
function clickPais(idPais) {
	if (esJugadorActivo()) {
		if (idPais == estado.idSel) {
			estado.idSel = undefined;
			return;
		}

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
		} else if (estado.actual == estado.despliegue){
			if (esEnemigo(idPais)) {
				printInfo("No se puede desplegar unidades sobre países enemigos");
			} else if (estado.idSel === undefined){
				estado.actual.clickDestino(idPais);
			} else {
				estado.idSel = idPais;
				estado.actual.clickDestino(idPais);
			}
		}
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
				printInfo(op)
				op.op1 = estado.idSel;
				op.op2 = id;
				op.operacion = "ataque";
				printInfo("atacando de " + estado.idSel + " a " + id);
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
				printInfo("moviendo unidades de " + estado.idSel + " a " + id);
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
				op.op1 = idUsuario;
				op.op2 = id;
				op.op3 = 1;
				op.operacion = "despliegue";
				printInfo("desplegando unidades en " + estado.idSel);
				console.log(op);
				sendInfo();
			}
		}

	},
	idSel : undefined,
	actual : undefined
};
// estado.actual = estado.movimiento;

function pasar() {
	if (!esJugadorActivo()) return;
	if (estado.actual == estado.despliegue)
		estado.actual = estado.movimiento;
	else if (estado.actual == estado.movimiento)
		estado.actual = estado.ataque;
	else if (estado.actual == estado.ataque) {
		estado.actual = estado.despliegue;
		op.op1 = idUsuario;
		op.op2 = undefined;
		op.operacion = "siguiente";
	}
}

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

for ( var clave in nombreAid) {
	idAnombre[nombreAid[clave]] = clave;
}
function colorToRgb(colorStr) {
	var colores = [ {
		rgb : 'rgb(250,10,10)',
		str : 'rojo'
	}, {
		rgb : 'rgb(10,250,10)',
		str : 'verde'
	}, {
		rgb : 'rgb(10,10,250)',
		str : 'azul'
	} ];
	for (var c = 0; c < colores.length; c++)
		if (colores[c].str == colorStr)
			return colores[c].rgb;
}

function getUserPosition(idUser) {
	for (var i = 0; i < partida.jugadores.length; i++) {
		if (idUser == partida.jugadores[i].id)
			return i;
	}
}
function getColorUser(idUser) {
	return colorToRgb(partida.jugadores[getUserPosition(idUser)].color);
}

function coloreaPaises() {
	var o = {};
	for (var i = 0; i < partida.paises.length; i++) {
		var p = partida.paises[i];
		o[idAnombre[p.id]] = (p.id === estado.idSel) ? "#ffff00"
				: getColorUser(p.propietario);
		// partida.jugadores[p.propietario].color;
	}
	$('#vmap').vectorMap('set', 'colors', o);
};

// for (var i = 0; i < partida.paises.length; i++) {
// if (nombreAcoordenada[idAnombre[i]] == undefined)
// console.log(i);
// };

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

function printInfo(txt) {
	var panel = document.getElementById('msgLog');
	panel.textContent = panel.textContent.concat(txt + "\n");
}

function sendInfo() {
	var idPartida = document.getElementById('idPartida').value;
	console.log(op);
	op.idPartida = idPartida;
	console.log(op);
	$.ajax({
		url : "../postPartida",
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

function strToHex(rgb) {
	if (rgb == 'rojo')
		return "#FF0000";
	else if (rgb == 'verde')
		return "#00FF00";
	else
		return "#0000FF";
}
function dibujarInfoJugadores() { // habrá que cambiarlo por el nombre del
	// jugador
	var div = document.getElementById('infoJugadores');
	div.textContent = "";
	for (var i = 0; i < partida.jugadores.length; i++) {
		var str = '<li class="list-group-item" style="background-color: '
				+ strToHex(partida.jugadores[i].color) + '">'
				+ partida.jugadores[i].id + '<span class="badge">' + partida.jugadores[i].unidadesSinDesplegar + '</span>' +'</li>';
		var strActivo = '<li class="list-group-item" style="background-color: '
				+ strToHex(partida.jugadores[i].color) + '">'
				+ partida.jugadores[i].id + '<span class="badge">' + 'Su turno ' + partida.jugadores[i].unidadesSinDesplegar +
				 '</span></li>';
		if (partida.jugadores[i].id == partida.jugadorActivo)
			div.innerHTML = div.innerHTML + strActivo;
		else
			div.innerHTML = div.innerHTML + str;
	}

}

function getInfo() {
	var idPartida = document.getElementById('idPartida').value;
	console.log(idPartida);
	var url = "../partida/" + idPartida;
	$.get(url).done(function(json) {
		partida = JSON.parse(document.getElementById('jsonPartida').value);
		dibujarInfoJugadores();
		window.setTimeout("getInfo()", msPolling);
	}).fail(function(jqxhr, textStatus, error) {
		var err = textStatus + ", " + error;
		console.log("Request Failed: " + err);
	});

}
window.setTimeout("getInfo()", msPolling);