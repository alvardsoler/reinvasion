package es.ucm.reinvasion.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

public class ServicioAplicacionUsuario {
	@Transactional
	public static Usuario create(EntityManager entityManager, String username,
			String email, String pass) {

		try {
			Usuario user = Usuario.createUser(username, email, pass, "user");
			entityManager.persist(user);
			return user;
		} catch (NoResultException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Transactional
	public static boolean deleteByUsername(EntityManager entityManager,
			String username) {
		try {
			Usuario u = readByUsername(entityManager, username);
			if (u != null)
				return (entityManager.createNamedQuery("delUsuario")
						.setParameter("idParam", u.getId()).executeUpdate() == 1);
			else
				return false;
		} catch (NoResultException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public static Usuario readById(EntityManager entityManager, long id) {
		Usuario u = (Usuario) entityManager.find(Usuario.class, id);
		return u;
	}

	@Transactional
	public static Usuario readByUsername(EntityManager entityManager,
			String username) {
		try {
			return (Usuario) entityManager.createNamedQuery("usuarioByLogin")
					.setParameter("loginParam", username).getSingleResult();
		} catch (NoResultException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	@Transactional
	public static Usuario update(EntityManager entityManager, long idUsuario,
			String email, String pass) {
		Usuario u = readById(entityManager, idUsuario);
		if (u != null) {
			if (!email.equals("")) {
				u.setEmail(email);
			}
			if (!pass.equals(""))
				u.setHashedAndSalted(Usuario.generateHashedAndSalted(pass,
						u.getSalt()));

			entityManager.refresh(u);

		}
		return u;
	}

	@Transactional
	public static Usuario delete(EntityManager entityManager, long id) {
		Usuario u = (Usuario) entityManager.createNamedQuery("delUsuario")
				.setParameter("idParam", id).getSingleResult();
		return null;
	}

	public static List<Usuario> readAll(EntityManager entityManager) {
		try {
			List<Usuario> list = entityManager.createQuery(
					"select u.login, u.puntos from Usuario u").getResultList();
			return list;
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}

	}
}
