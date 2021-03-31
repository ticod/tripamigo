package kr.tripamigo.tripamigo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.service.DiaryService;
import kr.tripamigo.tripamigo.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.BoardService;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

	private final BoardService boardService;
	private final PlanService planService;
	private final DiaryService diaryService;

	@RequestMapping("/home")
    String home(Model model, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");
		List<Diary> diaryList = diaryService.getDiaryList(loginUser);
		model.addAttribute("diaryList", diaryList);

        return "mypage/home";
    }

	@RequestMapping("/diary")
    String diary(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/diary";
    }
	@GetMapping("/diaryForm")
    public String diaryForm(DiaryFormDTO diaryFormDTO, Model model) {
        return "mypage/diaryForm";
    }
	
	
	@PostMapping("/diaryForm")
	public String diaryWrite(@ModelAttribute @Valid DiaryFormDTO diaryFormDTO, BindingResult bindingResult
			,@RequestPart MultipartFile thumbnail, HttpServletRequest request, HttpSession session, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "mypage/diaryForm";
		}
		User user = (User) session.getAttribute("loginUser");
		if (user == null) {
			throw new LoginException("로그인하세요", "/login");
		}
		//diaryService.writeDiary(diaryFormDTO, user);
		throw new LoginException("글쓰기 완료", "diary");
	}
	@RequestMapping("/plan")
    String plan(Model model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");

		model.addAttribute("planList", planService.listMyPlan(loginUser));
        return "mypage/plan";
    }
	
	@RequestMapping("/board")
    String board(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/board";
    }
	
	@RequestMapping("/favorite")
    String favorite(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/favorite";
    }
	
	@RequestMapping("/note")
    String note(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/note";
    }
	
	@RequestMapping("/alarm")
    String alarm(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/alarm";
    }
	
	@RequestMapping("/followingview")
    String followingview(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/followingview";
    }
	
	@RequestMapping("/followerview")
    String followerview(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/followerview";
    }
	
	@RequestMapping("/comment")
    String comment(UserFormDTO userFormDTO, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
    	model.addAttribute(magazineList);
        return "mypage/comment";
    }

}
