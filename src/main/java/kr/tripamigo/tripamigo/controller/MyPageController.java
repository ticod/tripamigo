package kr.tripamigo.tripamigo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
//import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
//import kr.tripamigo.tripamigo.repository.DiaryRepository;
import kr.tripamigo.tripamigo.service.BoardService;
//import kr.tripamigo.tripamigo.service.DiaryService;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private BoardService svc;
	/*
	@Autowired
	private DiaryService dsvc;
	*/
	@RequestMapping("/home")
    String home(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/home";
    }

	@RequestMapping("/diary")
    String diary(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/diary";
    }
	
	@GetMapping("/diaryform")
    String diaryform(UserFormDTO userFormDTO, Model model) {
        return "mypage/diaryform";
    }
	
	
	/*
	@PostMapping("/diaryForm")
    public String diaryWrite(/*@ModelAttribute @Valid DiaryFormDTO diaryFormDTO, BindingResult bindingResult, HttpSession session, Model model) throws Exception{
    	if(bindingResult.hasErrors()) {
    		return "community/diaryForm";
    	}
    	
    	System.out.println(diaryFormDTO);
    	User user = (User)session.getAttribute("loginUser");
    	if(user == null) {
    		throw new LoginException("로그인하세요", "/login");
    	}
    	dsvc.writeDiary(diaryFormDTO, user);
    	throw new LoginException("글쓰기 완료","diary");
		return "mypage/diaryform";
    }
*/
	@RequestMapping("/plan")
    String plan(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/plan";
    }
	
	@RequestMapping("/board")
    String board(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/board";
    }
	
	@RequestMapping("/favorite")
    String favorite(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/favorite";
    }
	
	@RequestMapping("/note")
    String note(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/note";
    }
	
	@RequestMapping("/alarm")
    String alarm(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/alarm";
    }
	
	@RequestMapping("/followingview")
    String followingview(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/followingview";
    }
	
	@RequestMapping("/followerview")
    String followerview(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/followerview";
    }
	
	@RequestMapping("/comment")
    String comment(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/comment";
    }

}
