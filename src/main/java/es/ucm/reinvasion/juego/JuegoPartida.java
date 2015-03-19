package es.ucm.reinvasion.juego;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import static es.ucm.reinvasion.juego.Dado.tirarDado;

public class JuegoPartida {

	private ArrayList<Jugador> jugadores;
	private ArrayList<Pais> paises;

	private int turno;
	private int jugadorActivo;
	private boolean fin;

	public String serializa() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public void addPais(Pais a) {
		paises.add(a);
	}

	public ArrayList<Pais> getPaises() {
		return paises;
	}
	
	public String serializaPaises() {
		Gson gson = new Gson();
		return gson.toJson(paises);
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public static JuegoPartida deserializa(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, JuegoPartida.class);
	}

	public JuegoPartida() {
		jugadores = new ArrayList<Jugador>();
		paises = new ArrayList<Pais>();
		setTurno(1);
		setJugadorActivo(0);
		fin = false;
	}

	public void addJugador(String color) {
		Jugador j = new Jugador(color, jugadores.size());
		jugadores.add(j);
	}

	public void addPais(int propietario) {
		Pais p = new Pais(propietario, paises.size());
		paises.add(p);
		getJugador(propietario).addPais(p.getId());
	}

	public Jugador getJugador(int id) {
		return jugadores.get(id);
	}

	public Pais getPais(int id) {
		return paises.get(id);
	}

	public void addFrontera(int idA, int idB) {
		Pais a = getPais(idA);
		Pais b = getPais(idB);
		a.addFrontera(idB);
		b.addFrontera(idA);
	}
	
	public void desplegarUnidades(int propietario, int destino, int cantidad){
		desplegarUnidades(getJugador(propietario), getPais(destino), cantidad);
		
	}

	public void desplegarUnidades(Jugador propietario, Pais destino,
			int cantidad) {
		if (propietario.controlaPais(destino.getId())
				&& (propietario.getUnidadesSinDesplegar() >= cantidad)) {
			destino.addUnidades(cantidad);
			propietario.setUnidadesSinDesplegar(propietario
					.getUnidadesSinDesplegar() - cantidad);
		}
	}

	public void cambiarCartas(int jugador, int carta) {
		cambiarCartas(getJugador(jugador), Carta.valueOf(String.valueOf(carta)));

	}

	public void cambiarCartas(Jugador jugador, Carta carta) {
		jugador.setUnidadesSinDesplegar(jugador.getUnidadesSinDesplegar()
				+ carta.unidades);
	}

	public void mover(int origen, int destino, int cantidad) {
		mover(getPais(origen), getPais(destino), cantidad);
	}

	public void mover(Pais origen, Pais destino, int cantidad) {
		if (origen.getUnidades() > cantidad) {
			origen.setUnidades(origen.getUnidades() - cantidad);
			destino.setUnidades(destino.getUnidades() + cantidad);
		}
	}

	public void atacar(int atacante, int defensor) {
		atacar(getPais(atacante), getPais(defensor));
	}

	public void atacar(Pais atacante, Pais defensor) {
		int[] dados;
		int resultAtacante = 0;
		int resultDefensor = 0;
		String dadosAtacante = "";
		String dadosDefensor = "";
		if ((atacante.getUnidades() > 1) && (atacante.esFrontera(defensor))) {
			dados = Dado.tirarDados(atacante.getUnidades(),
					defensor.getUnidades());

			ArrayList<Integer> tiradasAtacante = new ArrayList<>();
			ArrayList<Integer> tiradasDefensa = new ArrayList<>();

			for (int i = 0; i < dados[0]; i++) {
				resultAtacante = tirarDado();
				tiradasAtacante.add(resultAtacante);
				dadosAtacante += "Dado_" + i + " = " + resultAtacante + " | ";
			}
			for (int i = 0; i < dados[1]; i++) {
				resultDefensor = tirarDado();
				tiradasDefensa.add(resultDefensor);
				dadosDefensor += "Dado_" + i + " = " + resultDefensor + " | ";
			}

			System.out.println("El atacante ha sacado:" + dadosAtacante
					+ " y tiene: " + atacante.getUnidades() + " unidades");
			System.out.println("El defensor ha sacado:" + dadosDefensor
					+ " y tiene: " + defensor.getUnidades() + " unidades");

			int resultado[];
			resultado = ataque(tiradasAtacante, tiradasDefensa);

			atacante.quitarUnidades(resultado[0]);
			defensor.quitarUnidades(resultado[1]);

			if (defensor.getUnidades() < 1) {
				Jugador d = getJugador(defensor.getPropietario());
				d.quitarPais(defensor.getId());
				defensor.setPropietario(atacante.getPropietario());
				Jugador a = getJugador(atacante.getPropietario());
				a.addPais(defensor.getId());
				System.out.println("El atacante gana el país defensor");
			}
			// System.out.println("El atacante ha sacado:" + dados[0] +
			// " y tiene: " + atacante.getUnidades() + " unidades");
			// System.out.println("El defensor ha sacado:" + dados[1] +
			// " y tiene: " + defensor.getUnidades() + " unidades");
		}

	}

	/**
	 *
	 * @param atacante
	 *            resultados de dados de ataque
	 * @param defensor
	 *            resultados de dados de defensa
	 * @return ejercitos que pierde cada uno
	 */
	private static int[] ataque(ArrayList<Integer> atacante,
			ArrayList<Integer> defensor) {
		int r[] = new int[2];
		Collections.sort(atacante, Collections.reverseOrder());
		Collections.sort(defensor, Collections.reverseOrder());
		if (atacante.get(0) > defensor.get(0)) {
			r[1]++;
			System.out.println("El defensor pierde una unidad");
		} else {
			r[0]++;
			System.out.println("El atacante pierde una unidad");
		}
		if (atacante.size() >= 2 && defensor.size() == 2) {
			if (atacante.get(1) > defensor.get(1)) {
				r[1]++;
				System.out.println("El defensor pierde una unidad.");
			} else {
				r[0]++;
				System.out.println("El atacante pierde una unidad.");
			}
		}
		return r;
	}

	public boolean terminada() {
		return fin;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public int getJugadorActivo() {
		return jugadorActivo;
	}

	public void setJugadorActivo(int jugadorActivo) {
		this.jugadorActivo = jugadorActivo;
	}
}
