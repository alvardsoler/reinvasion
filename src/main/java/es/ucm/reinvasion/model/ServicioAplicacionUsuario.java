package es.ucm.reinvasion.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public abstract class ServicioAplicacionUsuario {

	public static Usuario create(EntityManager entityManager, String username,
			String email, String pass) {
		Usuario u = null;
		try {
			// Se comprueba que el username no está ya registrado
			u = (Usuario) entityManager.createNamedQuery("usuarioByLogin")
					.setParameter("loginParam", username).getSingleResult();
			u = (Usuario) entityManager.createNamedQuery("usuarioByEmail")
					.setParameter("emailParam", email).getSingleResult();
			return null;
		} catch (NoResultException exception) {
			Usuario user = Usuario.createUser(username, email, pass, "user");
			entityManager.persist(user);
			return user;

		}

	}

	public static Usuario read(EntityManager entityManager, String username) {
		try {
			return (Usuario) entityManager.createNamedQuery("usuarioByLogin")
					.setParameter("loginParam", username).getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	public static Usuario update(EntityManager entityManager, Usuario usuario, String email, String pass){
		return null;
	}
	public static Usuario delete(EntityManager entityManager, long id){
		Usuario u = (Usuario) entityManager.createNamedQuery("delUsuario").setParameter("idParam", id).getSingleResult();
		return null;
	}

}
