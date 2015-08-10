package es.ucm.reinvasion.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

public class ServicioAplicacionPartida {

	public static Partida create(EntityManager entityManager, String nombre,
			Usuario user, Calendar fecha) {

		try {
			fecha = Calendar.getInstance();
			Partida game = Partida.crearPartida(nombre, user, fecha);
			entityManager.persist(game);
			// se inserta al creador
			Usuario_Partida up = new Usuario_Partida(new UsuarioPartidaId(
					game.getId(), user.getId()));
			entityManager.persist(up);
			return game;

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;

		}

	}

	public static boolean addUserToGame(EntityManager entityManager,
			long idPartida, long idUser) {
		entityManager.persist(new Usuario_Partida(new UsuarioPartidaId(
				idPartida, idUser)));
		return true;
	}

	public static Partida getPartida(EntityManager entityManager, long id) {
		return entityManager.find(Partida.class, id);
	}

	// public static List<Partida> readAllByCreator(EntityManager entityManager,
	// Usuario creador) {
	// try {
	// List<Partida> ret;
	// ret = (List<Partida>) entityManager.createNamedQuery(
	// "partidaByCreador").setParameter("creadorParam", creador);
	//
	// return ret;
	// } catch (NoResultException e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// }

	@SuppressWarnings("unchecked")
	public static List<Partida> readAllWaiting(EntityManager entityManager) {
		return entityManager.createNamedQuery("allPartidas").setParameter("estado", Partida.EstadoPartida.ESPERANDO).getResultList();
	}

	public static List<Partida> readAllWithoutUser(EntityManager entityManager,
			long idUser) {
		
		List<Usuario_Partida> aux =  (List<Usuario_Partida>) entityManager
				.createQuery("select up from Usuario_Partida up where usuarioid !="
						+ idUser + " ").getResultList();
		List<Partida> ret = new ArrayList<Partida>();
		for (Usuario_Partida a : aux){
			ret.add(getPartida(entityManager, a.getId().getIdPartida()));
		}
		return ret;
	}

	public static List<Partida> readAllStarted(EntityManager entityManager){
		return entityManager.createNamedQuery("allPartidas").setParameter("estado", Partida.EstadoPartida.EN_CURSO).getResultList();
	}
	
	public static List<Partida> readAllByUserIn(EntityManager entityManager,
			Usuario usuario) {
		List<Partida> ret = entityManager.createNamedQuery("partidaConUsuario")
				.setParameter("idUser", usuario.getId()).getResultList();
		if(ret.isEmpty()){
			return null;
		}
		return ret;
	}

	public static Partida readByUser(EntityManager entityManager, long id) {
		Partida p = (Partida) entityManager.createNamedQuery("partidaById")
				.setParameter("idParam", id).getSingleResult();
		return p;
	}

	// public static Partida update(EntityManager entityManager, long id,
	// Jugador jugador, EstadoPartida estado) {
	// Partida p = readByUser(entityManager, id);
	// if (p != null) {
	//
	// p.addJugador(jugador);
	// p.setEstado(estado);
	//
	// entityManager.refresh(p);
	// }
	//
	// return p;
	// }

	public static Partida update(EntityManager entityManager, Partida p) {
		try {
			entityManager.persist(p);
			return entityManager.find(Partida.class, p.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Partida delete(EntityManager entityManager, Long id) {
		entityManager.createNamedQuery("delPartida")
				.setParameter("idParam", id).executeUpdate();
		return null;
	}
	
	public static boolean deleteByname(EntityManager entityManager, String name){
		Partida p = (Partida) entityManager.createNamedQuery("partidaByNombre").setParameter("nombreParam", name).getSingleResult();
		if(p!=null){
			System.out.println("He llegado");
			if(p.getEstado().equals(Partida.EstadoPartida.EN_CURSO) || p.getEstado().equals(Partida.EstadoPartida.FINALIZADA)){
				entityManager.createNamedQuery("delUserFromPartida").setParameter("idPartida", p.getId()).executeUpdate();
				entityManager.createNamedQuery("delPartidaByName").setParameter("nombreParam", name).executeUpdate();
				return true;
			}
		}
		else{
			return false;
		}
		return true;
	}

}
