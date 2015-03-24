package es.ucm.reinvasion;

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

import es.ucm.reinvasion.model.Usuario;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	@ResponseBody
	public String registroUser(@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String pass,
			@RequestParam("passwordValidation") String pass2,
			HttpServletRequest request, Model model) {
		logger.info("Intentando registrar con: {} {}", username, email);

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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("VIEW: Welcome home!");

		return "home";
	}

	@RequestMapping(value = "/partida/{idPartida}", method = RequestMethod.GET)
	public String partidasView(@PathVariable("idPartida") long idPartida,
			Model model) {
		logger.info("VIEW: Cargando la partida {}", idPartida);
		return "partida";
	}

	@RequestMapping(value = "/partidas/{username}", method = RequestMethod.GET)
	public String partidasView(@PathVariable("username") String username,
			Model model) {
		logger.info("VIEW: Cargando las partidas del usuario {}", username);
		return "partidas";
	}

	@RequestMapping(value = "/usuario/{username}", method = RequestMethod.GET)
	public String userView(@PathVariable("username") String username,
			Model model) {
		logger.info("VIEW: Cargando el usuario {}", username);
		return "usuario";
	}

	@RequestMapping(value = "/ranking", method = RequestMethod.GET)
	public String rankingView(Model model) {
		logger.info("VIEW: Cargando el ranking");
		return "ranking";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminView(Model model) {
		logger.info("VIEW: Cargando panel de admin");
		return "admin";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		logger.info("VIEW: Logout...");
		return "login";
	}

	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public String registroView(Model model) {
		logger.info("VIEW: Cargando registro");
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
