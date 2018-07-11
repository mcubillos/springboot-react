package ffg.diagnose.ui.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ffg.diagnose.ui.renderer.ReactRenderer;

@Controller
public class WebController {

	@Autowired
	ReactRenderer renderer;
	
	@GetMapping("/")
	public void root(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html");
		Map<String, Object> index = new HashMap<>();
		Map<String, Object> indexInfo = new HashMap<>();
		
		indexInfo.put("data", null);
		index.put("index", indexInfo);
        
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
	
	@GetMapping("/login")
	public void login(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html");

		Map<String, Object> login = new HashMap<>();
		Map<String, Object> loginInfo = new HashMap<>();

		loginInfo.put("data", null);
		login.put("login", loginInfo);
		try {
			response.getWriter().write(renderer.render(login));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/user")
	public void userIndex(HttpServletResponse response) {
		response.setHeader("Content-Type", "text/html");

		Map<String, Object> user = new HashMap<>();
		Map<String, Object> userInfo = new HashMap<>();
		
		userInfo.put("data", null);
		user.put("user", userInfo);
		try {
			response.getWriter().write(renderer.render(user));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
