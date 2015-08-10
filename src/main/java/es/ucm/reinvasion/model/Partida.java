package es.ucm.reinvasion.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.gson.Gson;

import es.ucm.reinvasion.juego.JuegoPartida;
import es.ucm.reinvasion.juego.LeerMapa;

@Entity
@NamedQueries({
		@NamedQuery(name = "partidaById", query = "select p from Partida p where p.id = :idParam"),
		@NamedQuery(name = "partidaByNombre", query = "select p from Partida p where p.nombre = :nombreParam"),
		@NamedQuery(name = "partidaByCreador", query = "select p from Partida p where p.creador =:creadorParam"),
		// @NamedQuery(name = "partidaConUsuario", query =
		// "select p from Partida p where p.jugadores IN (:idUser)"),
		@NamedQuery(name = "delPartida", query = "delete from Partida p where p.id = :idParam"),
		@NamedQuery(name = "delPartidaByName", query = "delete from Partida p where p.nombre = :nombreParam"),
		@NamedQuery(name = "allPartidas", query = "select p from Partida p where p.estado = :estado") })
public class Partida {
	public enum EstadoPartida {
		ESPERANDO, EN_CURSO, FINALIZADA;
	};

	private Long id;
	private String nombre;
	private Usuario creador;
	private EstadoPartida estado;
	private Calendar fechaInicio;
	private String json;

	public Partida() {
	}

	public static Partida crearPartida(String nombre, Usuario creador,
			Calendar fechaInicio) {
		Partida p = new Partida();
		p.nombre = nombre;
		p.creador = creador;
		p.fechaInicio = fechaInicio;
		p.estado = EstadoPartida.ESPERANDO;
		
		return p;
	}

	private static int MAX_JUGADORES = 3;

	/**
	 * Añade el id de un jugador a la partida si esta no ha comenzado
	 * devolviendo true. Si la partida ya comenzó o finalizó devuelve false. En
	 * el caso de que la partida llegue a su máximo con el jugador añadido,
	 * comienza la partida.
	 * 
	 * @param jugador
	 * @return
	 * @throws IOException
	 */
	public boolean addJugador(Usuario nuevoUsuario) throws IOException {
		if (this.estado == EstadoPartida.ESPERANDO) {
			// this.jugadores.add(nuevoUsuario);
			// if (this.jugadores.size() == MAX_JUGADORES) {
			// this.estado = EstadoPartida.EN_CURSO;
			// inicializarPartida();
			// }
			// return true;

		}
		return false;
	}

	String[] colores = { "rojo", "verde", "azul" };

	public void inicializarPartida() throws IOException {
		JuegoPartida jp = new JuegoPartida();
		// for (Usuario j : jugadores) {
		// jp.addJugador(colores[jugadores.size() - 1]);
		// }
		LeerMapa mapa = new LeerMapa("Mapa");
		mapa.inicializarMapa(jp);
		mapa.asignarJugadoresPaises(jp);
		Gson gson = new Gson();
		json = gson.toJson(jp, JuegoPartida.class);
		if (estado != EstadoPartida.EN_CURSO) // por si la partida comienza
												// antes de llegar al máximo
			estado = EstadoPartida.EN_CURSO;
	}

	/* Getters & setters */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = false, nullable = false)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// @ManyToMany(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	// @JoinColumn(name = "partida_id")
	// public List<Usuario> getJugadores() {
	// return jugadores;
	// }
	//
	// public void setJugadores(List<Usuario> jugadores) {
	// this.jugadores = jugadores;
	// }

	@ManyToOne(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(unique = false, nullable = false)
	public EstadoPartida getEstado() {
		return estado;
	}

	public void setEstado(EstadoPartida estado) {
		this.estado = estado;
	}

	@Column(unique = false, nullable = false)
	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", nombre=" + nombre + ", creador="
				+ creador + ", estado=" + estado + ", fechaInicio="
				+ fechaInicio + "]";
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(length = 10240)
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}