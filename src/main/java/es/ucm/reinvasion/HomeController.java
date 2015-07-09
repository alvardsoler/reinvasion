package es.ucm.reinvasion;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import es.ucm.reinvasion.model.ServicioAplicacionUsuario;
import es.ucm.reinvasion.model.Usuario;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String jsonOK = "{\"res\": \"YES\"}";
	private static final String jsonNO = "{\"res\": \"NO\"}";

	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String registroUser(@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String pass,
			@RequestParam("passwordValidation") String pass2,
			HttpServletRequest request, Model model, HttpSession session) {
		logger.info("Intentando registrar con: {} {}", username, email);
		if (pass.equals(pass2)) {
			Usuario u = ServicioAplicacionUsuario.create(entityManager,
					username, email, pass);
			if (u != null) {
				session.setAttribute("usuario", u);
				return "{\"res\": \"YES\"}";
			}

		} else
			return "{\"res\": \"NOPE\",\"msg\": \"Las claves no coinciden\"}";
		return "{\"res\": \"NOPE\",\"msg\": \"Algún error al crear el usuario\"}";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	@ResponseBody
	public String loginUser(@RequestParam("username") String username,
			@RequestParam("password") String pass, HttpServletRequest request,
			Model model, HttpSession session) {
		logger.info("Intentando iniciar sesión con: {} {}", username, pass);
		ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
		Usuario u = sau.readByUsername(entityManager, username);
		if (u != null && u.isPassValid(pass)) {
			logger.info("ok");
			session.setAttribute("usuario", u);
			getTokenForSession(session);
			if (isAdmin(session))
				return "{\"res\": \"YES\", \"to\": \"admin\"}";
			else
				return "{\"res\": \"YES\", \"to\": \"" + u.getLogin() + "\"}";
		} else {
			logger.warn("nope");
			return "{\"res\": \"NOPE\"}";
		}
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String updateUser(@RequestParam("idUser") long idUsuario,
			@RequestParam("emaiUser") String email,
			@RequestParam("passUser") String pass, HttpServletRequest request,
			Model mode, HttpSession session) {
		logger.info("Updating user {}", idUsuario);
		if (isAdmin(session)
				|| ((Usuario) session.getAttribute("usuario")).getId() == idUsuario) {
			ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
			if (sau.update(entityManager, idUsuario, email, pass) != null)
				return jsonOK;
		}
		return jsonNO;

	}

	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String delUser(@RequestParam("username") String username,
			@RequestParam("csrf") String token, HttpServletRequest request,
			Model mode, HttpSession session) {
		logger.info("Intentando eliminar el usuario {}", username);
		if (isAdmin(session) && isTokenValid(session, token)) {
			ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
			if (sau.deleteByUsername(entityManager, username)) {
				return "{\"res\": \"YES\"}";
			} else
				return "{\"res\": \"NOPE\"}";
		} else
			return "{\"res\": \"NOPE\"}";

	}

	/* Funciones de carga de páginas */

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("VIEW: Welcome home!");
		model.addAttribute("pageTitle", "Home - Invasion Strategy Game");

		return "home";
	}

	@RequestMapping(value = "/partida/{idPartida}", method = RequestMethod.GET)
	public String partidasView(@PathVariable("idPartida") long idPartida,
			Model model) {
		logger.info("VIEW: Cargando la partida {}", idPartida);
		model.addAttribute("prefix", "../");
		model.addAttribute("pageTitle", "Partida - Invasion Strategy Game");
		return "partida";
	}

	@RequestMapping(value = "/partidas/{username}", method = RequestMethod.GET)
	public String partidasView(@PathVariable("username") String username,
			Model model) {
		logger.info("VIEW: Cargando las partidas del usuario {}", username);
		model.addAttribute("prefix", "../");
		model.addAttribute("pageTitle", "Partidas - Invasion Strategy Game");
		return "partidas";
	}

	@RequestMapping(value = "/usuario/{username}", method = RequestMethod.GET)
	public String userView(@PathVariable("username") String username,
			Model model) {
		logger.info("VIEW: Cargando el usuario {}", username);
		model.addAttribute(
				"userViewed",
				entityManager.createNamedQuery("usuarioByLogin")
						.setParameter("loginParam", username).getSingleResult());
		model.addAttribute("prefix", "../");
		model.addAttribute("pageTitle", "Usuario - Invasion Strategy Game");

		return "usuario";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginView(Model model) {
		logger.info("VIEW: Cargando login");
		model.addAttribute("pageTitle", "Login - Invasion Strategy Game");

		return "login";
	}

	@RequestMapping(value = "/ranking", method = RequestMethod.GET)
	public String rankingView(Model model) {
		logger.info("VIEW: Cargando el ranking");
		model.addAttribute("pageTitle", "Ranking - Invasion Strategy Game");
		return "ranking";
	}

	@ResponseBody
	@RequestMapping(value = "/loadRanking", method = RequestMethod.GET)
	public String loadRanking(Model model) {
		ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
		List<Usuario> list = sau.readAll(entityManager);
		logger.info("Loading ranking... with {} elems", list.size());
		Gson gson = new Gson();
		return gson.toJson(list);

	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminView(Model model, HttpSession session) {
		logger.info("VIEW: Cargando panel de admin");
		model.addAttribute("pageTitle", "Admin - Invasion Strategy Game");
		if (isAdmin(session))
			return "admin";
		else
			return "home";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		logger.info("VIEW: Logout...");
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public String registroView(Model model) {
		logger.info("VIEW: Cargando registro");
		model.addAttribute("pageTitle", "Registro - Invasion Strategy Game");

		return "registro";
	}

	/* Funciones auxiliares */

	/**
	 * Returns true if the user is logged in and is an admin
	 */
	static boolean isAdmin(HttpSession session) {
		Usuario u = (Usuario) session.getAttribute("usuario");
		if (u != null) {
			return u.getRol().equals("admin");
		} else {
			return false;
		}
	}

	/**
	 * Get token for session and set it.
	 * 
	 * @param session
	 * @return token
	 */
	private static String getTokenForSession(HttpSession session) {
		String token = UUID.randomUUID().toString();
		session.setAttribute("csrf_token", token);
		return token;
	}

	/**
	 * Check if token is valid.
	 * 
	 * @param session
	 * @param token
	 * @return if token is valid for session
	 */
	private static boolean isTokenValid(HttpSession session, String token) {
		String t = (String) session.getAttribute("csrf_token");
		return (t != null) && t.equals(token);
	}

}
