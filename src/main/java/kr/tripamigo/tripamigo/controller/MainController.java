package kr.tripamigo.tripamigo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService svc;

    @RequestMapping("/home")
    String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    String login(Model model) {
        return "login";
    }
    
    @PostMapping("/signup")
    String signup(@RequestParam Map<String, Object> param, Model model) throws Exception {
    	UserFormDTO dto = new UserFormDTO();
    	
    	dto.setId((String)param.get("id"));
    	dto.setPassword((String)param.get("password"));
    	dto.setEmail((String)param.get("email"));
    	dto.setYear((String)param.get("year"));
    	dto.setMonth((String)param.get("month"));
    	dto.setDay((String)param.get("day"));
    	dto.setNickname((String)param.get("nickname"));
    	dto.setGender(Integer.parseInt((String)param.get("gender")));
    	
    	svc.join(dto);
    	
    	return "redirect:/home";
    }
    
    @PostMapping("/login")
    String login(@RequestParam Map<String, Object> param, Model model) {
    	
    	
    	
    	
    	return "home";
    }

}
