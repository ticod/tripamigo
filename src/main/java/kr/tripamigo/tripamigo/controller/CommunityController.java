package kr.tripamigo.tripamigo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Magazine;
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
    public String magazineWrite(@ModelAttribute @Valid MagazineFormDTO magazineFormDTO, BindingResult bindingResult, HttpSession session, Model model) throws Exception{
    	
    	if(bindingResult.hasErrors()) {
    		return "community/magazineForm";
    	}
    	
    	System.out.println(magazineFormDTO);
    	User user = (User)session.getAttribute("loginUser");
    	
    	if(user == null) {
    		throw new LoginException("로그인하세요", "/login");
    	}
    	boardService.writeMagazine(magazineFormDTO, user);
    	
    	throw new LoginException("글쓰기 완료","magazine");
    }
    
    @GetMapping("/magazinePage")
    public String magazineDetail(HttpSession session, HttpServletRequest request, Model model) {
    	
    	Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
    	Magazine magazine = boardService.readMagazine(boardSeq);
    	List<Comment> commentList = commentService.commentList(1, magazine.getBoardSeq());
    	model.addAttribute("magazine",magazine);
    	model.addAttribute("commentList", commentList);
    	
//    	System.out.println(recommendService.recommendCount(RecommendType.COMMENT, magazine.getBoardSeq().intValue()));
    	
    	
    	return "community/magazinePage";
    }
    
    @RequestMapping("/deleteBoard")/////수정 필요
    public String deleteBoard(HttpSession session, HttpServletRequest request, Model model) {
    	Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
    	try{
    		boardService.delete(boardSeq);
    		System.out.println("삭제 성공");
    		return "/community/home";
    	}catch(Exception e) {
    		System.out.println("삭제 실패");
    		throw new LoginException("글삭제실패","/community/home");
    	}
    	
    	
    	
    }
    
}
