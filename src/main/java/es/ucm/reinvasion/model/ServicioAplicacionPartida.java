package es.ucm.reinvasion.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import es.ucm.reinvasion.juego.Jugador;
import es.ucm.reinvasion.model.Partida.EstadoPartida;

public class ServicioAplicacionPartida {
	
	public static Partida create(EntityManager entityManager, String nombre, Usuario user,
			Calendar fecha){
		Partida p = null;
		try{
			p = (Partida) entityManager.createNamedQuery("partidaByNombre").
					setParameter("nombreParam", nombre).getSingleResult();
			p = (Partida) entityManager.createNamedQuery("partidaByCreador").
					setParameter("creadorParam", user).getSingleResult();
			return null;
		}
		catch(NoResultException exception){
			fecha = Calendar.getInstance();
			Partida game = Partida.crearPartida(nombre, user, fecha);
			entityManager.persist(game);
			return game;
		
		}
		
	}
	
	public static List<Partida> readAllByUser(EntityManager entityManager, String nombre){
		
		Usuario u = (Usuario) entityManager.createNamedQuery("usuarioByLogin")
				.setParameter("loginParam", nombre).getSingleResult();
		
		return u.getPartidas();
	
	}
	
	public static Partida readByUser(EntityManager entityManager, long id){
		Partida p = (Partida) entityManager.createNamedQuery("partidaById").setParameter("idParam", id).getSingleResult();
		return p;
	}
	
	public static Partida update(EntityManager entityManager, long id, Jugador jugador, EstadoPartida estado){
			Partida p = readByUser(entityManager, id);
			if(p!=null){
				
				p.addJugador(jugador);
				p.setEstado(estado);
				
				entityManager.refresh(p);
			}
			
		return p;
	}
	
	public static Partida delete(EntityManager entityManager, Long id){
		Partida p = (Partida) entityManager.createNamedQuery("delPartida").setParameter("idParam", id).getSingleResult();
		return null;
	}
	
}
