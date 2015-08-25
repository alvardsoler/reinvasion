package es.ucm.reinvasion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import es.ucm.reinvasion.juego.JuegoPartida;
import es.ucm.reinvasion.model.Partida;
import es.ucm.reinvasion.model.ServicioAplicacionPartida;
import es.ucm.reinvasion.model.ServicioAplicacionUsuario;
import es.ucm.reinvasion.model.Usuario;
import es.ucm.reinvasion.model.UsuarioPartidaId;
import es.ucm.reinvasion.model.Usuario_Partida;

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

	@RequestMapping(value = "/nuevaPartida", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String newPartida(@RequestParam("nombrePartida") String nPartida,
			HttpServletRequest request, Model model, HttpSession session) {

		Usuario u = (Usuario) session.getAttribute("usuario");

		Partida p = ServicioAplicacionPartida.create(entityManager, nPartida,
				u, Calendar.getInstance());
		if (p != null) {
			return jsonOK;
		} else
			return jsonNO;

	}

	@RequestMapping(value = "/unirsePartida", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String unirsePartida(@RequestParam("idPartida") long idPartida,
			HttpServletRequest request, Model model, HttpSession session) {
		logger.info("!!!!!!!!!!! -> Unirse a partida");
		Usuario u = (Usuario) session.getAttribute("usuario");

		Partida p = ServicioAplicacionPartida.getPartida(entityManager,
				idPartida);
		Long num = (Long) entityManager.createNamedQuery("numberPlayers")
				.setParameter("idPartida", p.getId()).getSingleResult();

		if (num < 3) {
			if (p.getEstado() == Partida.EstadoPartida.ESPERANDO) {
				List<Partida> lp = (List<Partida>) entityManager
						.createNamedQuery("isUserInPartida")
						.setParameter("idUser", u.getId())
						.setParameter("idPartida", (long) idPartida)
						.getResultList();
				if (lp.size() == 0) { // se introduce el usuario
					ServicioAplicacionPartida.addUserToGame(entityManager,
							idPartida, u.getId());
					if (num + 1 == 3) {
						p.setEstado(Partida.EstadoPartida.EN_CURSO);
					}
					return jsonOK;
				} else
					return jsonNO;// ya está dentro

			} // else la partida ya ha comenzado
			else
				return jsonNO;
		} else {
			return jsonNO;
		}

	}

	@RequestMapping(value = "/accederPartida", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String accederPartida(@RequestParam("idPartida") long idPartida,
			HttpServletRequest request, Model model, HttpSession session) {
		logger.info("!!!!!!!!!!! -> Acceder a partida");
		Partida p = ServicioAplicacionPartida.getPartida(entityManager,
				idPartida);
		Long num = (Long) entityManager.createNamedQuery("numberPlayers")
				.setParameter("idPartida", p.getId()).getSingleResult();

		if (num < 3) {
			return jsonNO;
		} else if (num == 3) {
			return jsonOK;
		} else {
			return jsonNO;
		}
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
	public String updateUser(@RequestParam("username") String username,
			@RequestParam("emailUser") String email,
			@RequestParam("passUser") String pass, HttpServletRequest request,
			Model mode, HttpSession session) {
		logger.info("Updating user {} {} {}", username, email);
		/*
		 * Lo que debería hacer: Si el usuario es admin: actualizar los datos
		 * pasados Si el usuario no es el admin: comprobar que tienen la misma
		 * ID, que la clave es correcta y después hacer las modificaciones
		 */
		logger.info("El nombre del usuario es: ", username);
		if (isAdmin(session)
				|| ((Usuario) session.getAttribute("usuario")).getLogin()
						.equalsIgnoreCase(username)) {
			ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
			if (sau.update(entityManager, username, email, pass) != null) {
				return jsonOK;
			}

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

	@RequestMapping(value = "/modUser", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String modUser(@RequestParam("username") String username,
			@RequestParam("csrf") String token, HttpServletRequest request,
			Model mode, HttpSession session) {
		logger.info("Intentando modificar el usuario {}", username);
		if (isAdmin(session) && isTokenValid(session, token)) {
			ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
			Usuario u = sau.readByUsername(entityManager, username);
			if (u!=null) {
				return "{\"res\": \"YES\"}";
			} else
				return "{\"res\": \"NOPE\"}";
		} else
			return "{\"res\": \"NOPE\"}";

	}
	
	@RequestMapping(value = "/delGame", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String delGame(@RequestParam("gamename") String gamename,
			@RequestParam("csrf") String token, HttpServletRequest request,
			Model mode, HttpSession session) {
		logger.info("Intentando eliminar la partida {}", gamename);
		if (isAdmin(session) && isTokenValid(session, token)) {
			ServicioAplicacionPartida sau = new ServicioAplicacionPartida();
			if (sau.deleteByname(entityManager, gamename)) {
				return "{\"res\": \"YES\"}";
			} else{
				return "{\"res\": \"NOPE\"}";
			}
		} else
			return "{\"res\": \"NOPEA\"}";

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

	@Transactional
	@RequestMapping(value = "/partida/{idPartida}", method = RequestMethod.GET)
	public String getPartida(@PathVariable("idPartida") long idPartida,
			Model model) throws IOException {
		logger.info("VIEW: Cargando la partida {}", idPartida);
		model.addAttribute("prefix", "../");
		model.addAttribute("pageTitle", "Partida - Invasion Strategy Game");
		logger.info("Cogiendo datos de partida {}", idPartida);

		if (!model.containsAttribute("idPartida"))
			model.addAttribute("idPartida", idPartida);

		// harbria que comprobar que el usuario está en la partida
		Partida p = ServicioAplicacionPartida.getPartida(entityManager,
				idPartida);
		if (p.getJson() == null) {
			p.inicializarPartida(entityManager);
			ServicioAplicacionPartida.update(entityManager, p);
		}
		if (p.getEstado() != Partida.EstadoPartida.ESPERANDO)
			model.addAttribute("jsonPartida", p.getJson());

		return "partida";
	}
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/postPartida", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session)
			throws ServletException, IOException {

		// 1. get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));

		Gson gson = new Gson();
		String json = "";
		json = br.readLine();

		Operacion operacion = gson.fromJson(json, Operacion.class);
		
		
		Partida p = ServicioAplicacionPartida.getPartida(entityManager, Integer.parseInt(operacion.getIdPartida()));
			

		// es.ucm.invasion.juego.Partida partida = p.getPartida();
		JuegoPartida partida = gson.fromJson(p.getJson(), JuegoPartida.class);
		
		if (operacion.getOperacion().equals("ataque")) {
			partida.atacar(operacion.getOp1(), operacion.getOp2());
		} else if (operacion.getOperacion().equals("movimiento")) {
			partida.mover(operacion.getOp1(), operacion.getOp2(),
					operacion.getOp3());
		} else if (operacion.getOperacion().equals("siguiente")) {		
			partida.siguiente();
//			partida.cambiarCartas(operacion.getOp1(), operacion.getOp2());
		} else if (operacion.getOperacion().equals("despliegue")) {
			partida.desplegarUnidades(operacion.getOp1(), operacion.getOp2(),
					operacion.getOp3());
		}
		
		p.setJson(partida.serializa());
		ServicioAplicacionPartida.update(entityManager, p);
		
		logger.info(operacion.toString());

		model.addAttribute("jsonPartida", p.getJson());

	}

	@RequestMapping(value = "/partidas/{username}", method = RequestMethod.GET)
	public String partidasView(@PathVariable("username") String username,
			Model model, HttpSession session) {
		logger.info("VIEW: Cargando las partidas del usuario {}", username);

		List<Usuario_Partida> partidasUsuario = new ArrayList<Usuario_Partida>();
		Usuario u = (Usuario) session.getAttribute("usuario");

		// ServicioAplicacionPartida sap = new ServicioAplicacionPartida();

		// p = sap.readAllByUserIn(entityManager, u);
		partidasUsuario = entityManager.createNamedQuery("partidasUsuario")
				.setParameter("idUser", u.getId()).getResultList();

		List<Partida> ret = new ArrayList<Partida>();

		if (partidasUsuario.size() > 0) {
			for (int i = 0; i < partidasUsuario.size(); i++) {
				UsuarioPartidaId id = partidasUsuario.get(i).getId();
				ret.add(ServicioAplicacionPartida.getPartida(entityManager,
						id.getIdPartida()));
			}
			model.addAttribute("partidasUnido", ret);
		}

		// List<Partida> resto =
		// ServicioAplicacionPartida.readAll(entityManager);

		List<Partida> resto = ServicioAplicacionPartida
				.readAllWaiting(entityManager);
		List<Partida> noUnido = new ArrayList<Partida>();

		if (resto.size() > 0) {
			for (int i = 0; i < resto.size(); i++) {
				boolean found = false;
				for (int j = 0; j < ret.size() && !found; j++) {
					if (ret.get(j).getId().equals(resto.get(i).getId())) {
						found = true;
					}
				}

				if (!found) {
					noUnido.add(resto.get(i));
				}
			}
		}
		model.addAttribute("restoPartidas", noUnido);

		// for (int i = 0; i < sap.readAllByUser(entityManager,
		// username).size(); i++) {
		// p.add(sap.readAllByUser(entityManager, username).get(i));
		// }
		logger.info("Loading partidas from {}", username);
		logger.info("Values: {}", partidasUsuario);

		model.addAttribute("prefix", "../");
		model.addAttribute("pageTitle", "Partidas - Invasion Strategy Game");

		return "partidas";
	}

	@Transactional
	@RequestMapping(value = "/usuario/{username}", method = RequestMethod.GET)
	public String userView(@PathVariable("username") String username,
			Model model) {
		logger.info("VIEW: Cargando el usuario {}", username);
		ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
		Usuario u = sau.readByUsername(entityManager, username);
		// Usuario u = (Usuario)
		// entityManager.createNamedQuery("usuarioByLogin")
		// .setParameter("loginParam", username).getSingleResult();
		if (u != null)
			model.addAttribute("userView", u);
		else
			logger.warn("No data for {}", username);
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
		List<Usuario> usuarios = new LinkedList<Usuario>();
		ServicioAplicacionUsuario sau = new ServicioAplicacionUsuario();
		logger.info("El tamaño del array de usuarios es igual a: "
				+ sau.readAll(entityManager).size());
		usuarios = sau.readAll(entityManager);
		model.addAttribute("allUsers", usuarios);
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
		if (isAdmin(session)) {
			List<Usuario> users = ServicioAplicacionUsuario
					.readAll(entityManager);
			model.addAttribute("users", users);
			List<Partida> games = ServicioAplicacionPartida
					.readAllStarted(entityManager);
			model.addAttribute("games", games);
			return "admin";
		} else
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

	private HashMap<String, Object> JSONROOT = new HashMap<String, Object>();

	@ResponseBody
	@RequestMapping(value = "/InvasionServlet")
	@Transactional
	public void loadData(@RequestParam("action") String action,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {

		if (action != null) {
			Gson gson = new Gson();
			// logger.info(request.getParameterMap().toString());
			response.setContentType("application/json");
			if (action.equals("listAllUsers")) {

				logger.info("cargando ranking de usuarios");
				List<Usuario> usuarios = entityManager
						.createQuery(
								"Select u.id, u.login, u.rol, u.email, u.puntos From Usuario u")
						.getResultList();

				JSONROOT.put("Result", "OK");
				JSONROOT.put("Records", usuarios);

				String array = gson.toJson(JSONROOT);
				logger.info("***El array contiene: " + array);
				response.getWriter().print(gson.toJson(JSONROOT));

			}
		}
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

	/**
	 * Returns a users' photo
	 * 
	 * @param id
	 *            id of user to get photo from
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/usuario/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] userPhoto(@RequestParam("id") String id) throws IOException {
		File f = ContextInitializer.getFile("user", id);
		InputStream in = null;
		if (f.exists()) {
			in = new BufferedInputStream(new FileInputStream(f));
		} else {
			in = new BufferedInputStream(this.getClass().getClassLoader()
					.getResourceAsStream("unknown-user.jpg"));
		}

		return IOUtils.toByteArray(in);
	}

	/**
	 * Uploads a photo for a user
	 * 
	 * @param id
	 *            of user
	 * @param photo
	 *            to upload
	 * @return
	 */
	@RequestMapping(value = "/usuario", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(
			@RequestParam("photo") MultipartFile photo,
			@RequestParam("id") String id) {
		if (!photo.isEmpty()) {
			try {
				byte[] bytes = photo.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(ContextInitializer.getFile("user",
								id)));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded "
						+ id
						+ " into "
						+ ContextInitializer.getFile("user", id)
								.getAbsolutePath() + "!";
			} catch (Exception e) {
				return "You failed to upload " + id + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload a photo for " + id
					+ " because the file was empty.";
		}
	}

}
