package es.ucm.reinvasion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	@ResponseBody
	public String registroUser(@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String pass,
			@RequestParam("passwordValidation") String pass2,
			HttpServletRequest request, Model model, HttpSession session) {
		logger.info("Intentando registrar con: {} {}", username, email);
		if (pass.equals(pass2)) {
			Usuario u = ServicioAplicacionUsuario.create(entityManager,
					username, email, pass);
			if (u != null)
				session.setAttribute("usuario", u);
		} else
			return "{\"res\": \"NOPE\",\"msg\": \"Las claves no coinciden\"}";
		return "{\"res\": \"YES\"}";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String loginUser(@RequestParam("username") String username,
			@RequestParam("password") String pass, HttpServletRequest request,
			Model model) {
		logger.info("Intentando iniciar sesión con: {} {}", username, pass);
		if (username.length() > 3) {
			logger.info("ok");
			return "{\"res\": \"YES\"}";
		} else {
			logger.warn("nope");
			return "{\"res\": \"NOPE\"}";
		}
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
		Usuario u = (Usuario) session.getAttribute("user");
		if (u != null) {
			return u.getRol().equals("admin");
		} else {
			return false;
		}
	}

}
