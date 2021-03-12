package kr.tripamigo.tripamigo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.tripamigo.tripamigo.util.CipherUtil;
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
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private CipherUtil cipherUtil;

    @RequestMapping("/home")
    String home(UserFormDTO userFormDTO, Model model) {
    	
        return "home";
    }

    @GetMapping("/login")
    String login(UserFormDTO userFormDTO, Model model) {
        return "login";
    }
    
    @GetMapping("/signup")
    String signupForm(UserFormDTO userFormDTO, Model model) {
    	System.out.println("signupForm메서드");
    	return "signup";
    }
    
    @PostMapping("/login")
    String login(@ModelAttribute @Valid UserFormDTO userFormDTO, BindingResult bindingResult, HttpSession session, Model model)
			throws Exception {
    	
    	if(bindingResult.hasFieldErrors("id")||bindingResult.hasFieldErrors("password")) {
    		return "login";
    	}
    	
    	User dbUser = userService.selectUserOne(userFormDTO.getId());

    	if (dbUser == null) {
    		throw new LoginException("로그인 실패, 아이디 확인", "/login");
		}
    	
    	if(dbUser.getUserPw().equals(
    			cipherUtil.hashEncoding(
    					userFormDTO.getPassword(), dbUser.getUserSalt()))) {
    		model.addAttribute("loginUser", dbUser);
    		session.setAttribute("loginUser", dbUser);
    		return "redirect:home";
    		
    	} else {
			throw new LoginException("로그인 실패, 아이디 확인", "/login");
    	}
    
    }
    @RequestMapping("/logout")
    String logout(HttpSession session, Model model) {
    	session.invalidate();
    	return "redirect:home";
    }
    
    @RequestMapping(value="/idOverlapChk", method=RequestMethod.POST)
    public @ResponseBody String idOverlapChk(@RequestParam("id") String id) {
    	String idchk = "n";
    	User dbuser = userService.selectUserOne(id);
    	if(dbuser==null) {
    		idchk="y";
    	}
    	return idchk;
    	
    }
    
    @PostMapping("/signup")
    String signup(@ModelAttribute @Valid UserFormDTO userFormDTO, BindingResult bindingResult, HttpSession session, Model model) throws Exception {
    	
    	if(bindingResult.hasErrors()) {
    		return "signup";
    	}
    	userService.join(userFormDTO);
    	
    	throw new LoginException(userFormDTO.getId()+"님 회원가입 완료", "/home");
    
    }

}
