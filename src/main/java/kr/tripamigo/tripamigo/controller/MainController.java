package kr.tripamigo.tripamigo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.tripamigo.tripamigo.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService svc;

    @RequestMapping("/home")
    String home(UserFormDTO userFormDTO, Model model) {
    	
        return "home";
    }

    @GetMapping("/login")
    String login(UserFormDTO userFormDTO, Model model) {
        return "login";
    }
    
    /*
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
    */
    
    @PostMapping("/signup")
    String signup(@Valid UserFormDTO userFormDTO, BindingResult bindingResult, Model model) throws Exception {
    	
    	if(bindingResult.hasErrors()) {
    		return "home::#f";
    	}
    	
    	svc.join(userFormDTO);
    	
    	return "redirect:/home";
    	//이넘타입으로 y / n 넘겨주는 방법?
    }
    
    @PostMapping("login")
    String login(@ModelAttribute @Valid UserFormDTO userFormDTO, BindingResult bindingResult, HttpSession session, Model model)
			throws Exception {
    	
    	if(bindingResult.hasFieldErrors("id")||bindingResult.hasFieldErrors("password")) {
    		return "login";
    	}
    	
    	User dbuser = svc.selectUserOne(userFormDTO.getId());

    	if (dbuser == null) {
    		throw new LoginException("로그인 실패, 아이디 확인", "/login");
		}
    	
    	if(dbuser.getUserPw().equals(userFormDTO.getPassword())) {
    		model.addAttribute("loginUser", dbuser);
    		session.setAttribute("loginUser", dbuser);
    		return "redirect:home";
    		
    	} else {
			throw new LoginException("로그인 실패, 아이디 확인", "/login");
    	}
    
    }
    @RequestMapping("logout")
    String logout(HttpSession session, Model model) {
    	session.invalidate();
    	return "redirect:home";
    }
    
    @RequestMapping(value="/idOverlapChk", method=RequestMethod.POST)
    public @ResponseBody String idOverlapChk(@RequestParam("id") String id) {
    	String idchk = "n";
    	User dbuser = svc.selectUserOne(id);
    	if(dbuser==null) {
    		idchk="y";
    	}
    	return idchk;
    	
    }
    @RequestMapping(value="/submitChk", method=RequestMethod.POST)
    public @ResponseBody String submitChk(@RequestParam("userFormDTO") UserFormDTO userFormDTO) {
    	
    	System.out.println(userFormDTO);
    	
    	
    	return "y";
    	
    }

}
