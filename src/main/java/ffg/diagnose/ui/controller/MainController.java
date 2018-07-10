package ffg.diagnose.ui.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ffg.diagnose.ui.renderer.ReactRenderer;

@Controller
public class MainController {

	@Autowired
	ReactRenderer renderer;

	@RequestMapping("/")
	public void root(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html");
		Map<String, Object> index = new HashMap<>();
		index.put("index", "Hola");
        
		try {
			response.getWriter().write(renderer.render(index));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/user/index")
	public String userIndex() {
		return "user/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

}