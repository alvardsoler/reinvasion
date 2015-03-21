package es.ucm.reinvasion;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginView(Model model) {
		
		return "login";
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String loginUser(@RequestParam("username") String username,
			@RequestParam("password") String pass, HttpServletRequest request, Model model) {
		logger.info("Trying to log in {} {}", username, pass);
		if (username.length() > 3) {
			logger.info("ok");
			return "{\"res\": \"YES\"}";
		} else {
			logger.warn("nope");
			return "{\"res\": \"NOPE\"}";
		}
	}
}
