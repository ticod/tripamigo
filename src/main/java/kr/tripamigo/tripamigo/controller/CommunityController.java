package kr.tripamigo.tripamigo.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.CommentFormDTO;
import kr.tripamigo.tripamigo.dto.MagazineFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.CommentService;
import kr.tripamigo.tripamigo.service.RecommendService;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RecommendService recommendService;

	private void boardHitsUp(HttpServletRequest request, HttpServletResponse response, Long boardSeq) {
		String cookieName = boardSeq + "";
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(10); // 초단위

		boolean hitSwitch = false;

		Cookie[] cookies = request.getCookies();

		System.out.println(cookies);

		if (cookies != null) { // 쿠키가 있으면.
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					System.out.println("같은 쿠키가 존재");
					hitSwitch = true;
				}
			}
			if (!hitSwitch) {
				System.out.println("같은 쿠키 없음");
				response.addCookie(cookie);
				boardService.boardHitsUp(boardSeq);
			}
		} else { // 쿠키가 없으면.
			System.out.println("쿠키 자체가 없어서 추가하고 조회수 올림.");
			response.addCookie(cookie);
			boardService.boardHitsUp(boardSeq);
		}
	}

	@RequestMapping("/home")
	public String home(Model model) {
		return "community/home";
	}

	@GetMapping("/magazine")
	public String magazine(HttpSession session, Model model) {
		List<Magazine> magazineList = boardService.magazineList();
		model.addAttribute(magazineList);

		return "community/magazine";
	}

	@GetMapping("/magazineForm") // AOP적용 필요
	public String magazineForm(MagazineFormDTO magazineFormDTO, Model model) {
		return "community/magazineForm";
	}

//    @RequestMapping(value="/magazineForm", method=RequestMethod.POST)
//    public @ResponseBody String magazineForm(@ModelAttribute @Valid MagazineFormDTO magazineFormDTO, BindingResult bindingResult, HttpSession session, Model model) throws Exception{
//    	if(bindingResult.hasErrors()) {
//    		return "/community/magazineForm";
//    		
//    	}
//    	throw new LoginException("글쓰기 완료","home");
//    }

	@PostMapping("/magazineForm")
	public String magazineWrite(@ModelAttribute @Valid MagazineFormDTO magazineFormDTO, BindingResult bindingResult
			,@RequestPart MultipartFile thumbnail, HttpServletRequest request, HttpSession session, Model model) throws Exception {

		System.out.println(thumbnail.getOriginalFilename());
		
		System.out.println("바인딩리절트 전");
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/magazineForm";
		}
		System.out.println("바인딩리절트 후");
		System.out.println(magazineFormDTO);
		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			throw new LoginException("로그인하세요", "/login");
		}
//		boardService.writeMagazine(magazineFormDTO, user);

		throw new LoginException("글쓰기 완료", "magazine");
	}

	@GetMapping("/magazinePage")
	public String magazineDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			CommentFormDTO commentFormDTO, Model model) {

		Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
		Magazine magazine = boardService.readMagazine(boardSeq);

		List<Comment> commentList = commentService.commentList(0, magazine.getBoardSeq());

		model.addAttribute("magazine", magazine);
		model.addAttribute("commentList", commentList);

		boardHitsUp(request, response, boardSeq);

		System.out.println(commentList);
		return "community/magazinePage";
	}

	@RequestMapping(value = "/commentRecommend", method = RequestMethod.POST)
	public @ResponseBody boolean commentRecommend(@RequestParam("contentSeq") Long contentSeq,
			@RequestParam("userSeq") Long userSeq) {
		System.out.println(contentSeq);
		System.out.println(userSeq);

		Recommend dbRecommend = recommendService.readRecommend(userSeq, RecommendType.COMMENT, contentSeq);
		
		if (dbRecommend == null) {
			Recommend recommend = new Recommend();
			recommend.createRecommend(userSeq, RecommendType.COMMENT, contentSeq);
			
			recommendService.hitsRecommend(recommend);
			return true;
			
		}else if(!dbRecommend.isStatus()) {
			dbRecommend.setStatus(true);
			recommendService.hitsRecommend(dbRecommend);
			return true;
			
		}else {
			dbRecommend.setStatus(false);
			recommendService.cancelRecommend(dbRecommend);
			return false;
		}
	}

	@RequestMapping("/deleteBoard") ///// 수정 필요
	public String deleteBoard(HttpSession session, HttpServletRequest request, Model model) {
		Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
		int type = Integer.parseInt(request.getParameter("type"));

		Magazine magazine = boardService.readMagazine(boardSeq);
		magazine.setBoardStatus(false);
		User user = (User) session.getAttribute("loginUser");

		try {
			boardService.delete(magazine);

			if (user == null || !user.getUserId().equals(magazine.getUser().getUserId())) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("삭제 실패");
			throw new LoginException("글삭제실패", "/community/home");
		}
		System.out.println("삭제 성공");
		switch (type) {
		// 0 : 리뷰
		case 0:
			throw new LoginException("글 삭제 성공", "/community/review");
		// 1: 매거진
		case 1:
			throw new LoginException("글 삭제 성공", "/community/magazine");
		// 2: 여행홍보
		case 2:
			throw new LoginException("글 삭제 성공", "/community/promotion");
		}
		return "";
	}

	@PostMapping("/comment")
	public String commentWrite(CommentFormDTO commentFormDTO, HttpSession session, Model model) throws Exception {
		System.out.println("comment메서드");

		User user = (User) session.getAttribute("loginUser");
		int boardType = Integer.parseInt(commentFormDTO.getBoardType());
		String boardSeq = commentFormDTO.getBoardSeq();

		int contentType = 0; // 0커뮤니티, 1여행계획(boardType에 따라 바뀜)

		String page = "";
		switch (boardType) { // boardType에 따라 redirect페이지가 달라짐.
		case 1:
			page = "magazinePage?boardSeq=";
			break;
		}
		try {
			commentService.writeComment(contentType, commentFormDTO, user);
			return "redirect:" + page + boardSeq;
		} catch (Exception e) {
			throw new LoginException("댓글 등록 실패", page + boardSeq);
		}
	}

	@RequestMapping("/deleteComment")
	public String commentDelete(@RequestParam("commentSeq") String commentSeq,
			@RequestParam("boardType") String boardType, @RequestParam("boardSeq") String boardSeq, Model model) {
		int bType = Integer.parseInt(boardType);
		String bSeq = boardSeq;
		Long cSeq = Long.parseLong(commentSeq);

		Comment comment = commentService.readComment(cSeq);
		comment.setCommentStatus(false);
		String page = "";
		switch (bType) { // boardType에 따라 redirect페이지가 달라짐.
		case 1:
			page = "magazinePage?boardSeq=";
			break;
		}
		try {
			commentService.deleteComment(comment);
			return "redirect:" + page + bSeq;
		} catch (Exception e) {
			throw new LoginException("댓글 삭제 실패", "redirect:" + page + bSeq);
		}

	}

}
