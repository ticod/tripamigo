package kr.tripamigo.tripamigo.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.tripamigo.tripamigo.domain.Diary;
import kr.tripamigo.tripamigo.domain.board.Board;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.BoardType;
import kr.tripamigo.tripamigo.service.DiaryService;
import kr.tripamigo.tripamigo.service.InfoService;
import kr.tripamigo.tripamigo.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.service.BoardService;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

	private final BoardService boardService;
	private final PlanService planService;
	private final DiaryService diaryService;
	private final InfoService infoService;

	private static User getLoginUser(HttpSession session) {
		return (User) session.getAttribute("loginUser");
	}

	@RequestMapping("/home")
    String home(Model model, HttpSession session) {
		User loginUser = getLoginUser(session);
		List<Diary> diaryList = diaryService.getDiaryList(loginUser);

		model.addAttribute("diaryList", diaryList);

        return "mypage/home";
    }

	@RequestMapping("/plan")
    String plan(Model model, HttpSession session) {
		User loginUser = getLoginUser(session);
		List<Plan> planList = planService.getMyPlanList(loginUser);

		model.addAttribute("planList", planList);

        return "mypage/plan";
    }
	
	@RequestMapping("/board")
    String board(Model model, HttpSession session) {
		User loginUser = getLoginUser(session);
		Map<BoardType, List<Board>> boardList = boardService.boardList();
		List<Plan> planList = planService.getMyPlanList(loginUser);
		List<Info> infoList = infoService.getInfoListBy(loginUser);

		model.addAttribute("boardList", boardList);
		model.addAttribute("planList", planList);
		model.addAttribute("infoList", infoList);

        return "mypage/board";
    }
	
	@RequestMapping("/scrap")
    String favorite(Model model) {

        return "mypage/scrab";
    }
	
	@RequestMapping("/note")
    String note(Model model) {

        return "mypage/note";
    }
	
	@RequestMapping("/alarm")
    String alarm(Model model) {

        return "mypage/alarm";
    }
	
	@RequestMapping("/followingview")
    String followingview(Model model) {

        return "mypage/followingview";
    }
	
	@RequestMapping("/followerview")
    String followerview(Model model) {

        return "mypage/followerview";
    }
	
	@RequestMapping("/comment")
    String comment(Model model) {

        return "mypage/comment";
    }

}
