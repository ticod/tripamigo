package kr.tripamigo.tripamigo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.BoardType;
import kr.tripamigo.tripamigo.dto.CommentFormDTO;
import kr.tripamigo.tripamigo.dto.MagazineFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.CommentService;
import kr.tripamigo.tripamigo.service.InfoService;
import kr.tripamigo.tripamigo.service.PagingService;
import kr.tripamigo.tripamigo.service.PlanService;
import kr.tripamigo.tripamigo.service.RecommendService;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private PlanService planService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private PagingService pagingService;

	private void boardHitsUp(HttpServletRequest request, HttpServletResponse response, Long boardSeq) {
		String cookieName = boardSeq + "";
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(60); // ?????????

		boolean hitSwitch = false;

		Cookie[] cookies = request.getCookies();

		System.out.println(cookies);

		if (cookies != null) { // ????????? ?????????.
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					System.out.println("?????? ????????? ??????");
					hitSwitch = true;
				}
			}
			if (!hitSwitch) {
				System.out.println("?????? ?????? ??????");
				response.addCookie(cookie);
				boardService.boardHitsUp(boardSeq);
			}
		} else { // ????????? ?????????.
			System.out.println("?????? ????????? ????????? ???????????? ????????? ??????.");
			response.addCookie(cookie);
			boardService.boardHitsUp(boardSeq);
		}
	}

	@RequestMapping("/home")
	public String home(Model model) {
		List<Magazine> magazineList = boardService.magazineList().subList(0, 5);
		List<Info> infoList = infoService.infoList().subList(0, 5);
		List<Plan> planList = planService.listAll().subList(0, 5);
		model.addAttribute(magazineList);
		model.addAttribute(infoList);
		model.addAttribute(planList);
		
		
		return "community/home";
	}
	
	 @RequestMapping("/magazine")
	 public String magazine(Model model, @PageableDefault(page=0, size = 8, sort = "boardSeq") Pageable pageable, @RequestParam(value="findString",  required=false, defaultValue="") String findString) {
		System.out.println("findString : "+findString);
		
		
		if(findString.equals("") ) {
			model.addAttribute("magazineList", boardService.magazineListAllPaging(pageable));
			model.addAttribute("pageList", boardService.getPageList(pageable, null));
			
			model.addAttribute("pagingDTO", pagingService.getPaging(BoardType.MAGAZINE, pageable));
		}else {
			model.addAttribute("magazineList", boardService.magazineListAllPagingAndFind(pageable, findString));
			model.addAttribute("pageList", boardService.getPageList(pageable, findString));
			model.addAttribute("findString", findString);
			
			model.addAttribute("pagingDTO", pagingService.getSearchPaging(BoardType.MAGAZINE, pageable, findString));
		}
        
		
		model.addAttribute("curPage", pageable.getPageNumber());
        return "community/magazine";
    }

	@GetMapping("/magazineForm") // AOP?????? ??????
	public String magazineForm(MagazineFormDTO magazineFormDTO, HttpSession session, Model model) {
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser.getUserRank()<1) {
			throw new LoginException("????????? ?????? ???????????? ????????? ??? ????????????.","/community/magazine");
		}
		return "community/magazineForm";
	}
	
	@PostMapping("/magazineForm")
	public String magazineWrite(@ModelAttribute @Valid MagazineFormDTO magazineFormDTO, BindingResult bindingResult
			,@RequestPart MultipartFile file, HttpServletRequest request, HttpSession session, Model model) throws Exception {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/magazineForm";
		}
		//?????????
		if(magazineFormDTO.getFile() != null && !magazineFormDTO.getFile().isEmpty()) {
			uploadFileCreate(magazineFormDTO.getFile(),request, "community/file/");
		}
		magazineFormDTO.setThumbnail(magazineFormDTO.getFile().getOriginalFilename());
		
		//??????
		String tags = magazineFormDTO.getTags();
		magazineFormDTO.setTags(tags.replaceAll("#", ""));
		
		System.out.println(magazineFormDTO);
		
		//??????
		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			throw new LoginException("??????????????????", "/login");
		}
		boardService.writeMagazine(magazineFormDTO, user);

		throw new LoginException("????????? ??????", "magazine");
	}
	
	@GetMapping("/updateBoard")
	public String readMagazineForUpdate(MagazineFormDTO magazineFormDTO, @RequestParam("boardSeq") String boardSeq, HttpSession session, @RequestParam("type") int type, HttpServletRequest request, Model model) {
		Long bSeq = Long.parseLong(boardSeq);
		int btype = type;
		Magazine magazine = boardService.readMagazine(bSeq);
		User loginUser = (User)session.getAttribute("loginUser");
		
		if(!magazine.getUser().getUserSeq().equals(loginUser.getUserSeq()) || magazine.getUser().getUserSeq()!=(loginUser.getUserSeq())) {
			if(loginUser.getUserRank()!=2) {
				throw new LoginException("?????????????????? ????????????","/community/magazine");
			}
		}
		
		model.addAttribute("magazine",magazine);
		magazineFormDTO.setContent(magazine.getBoardContent());
		magazineFormDTO.setSubject(magazine.getBoardSubject());
		magazineFormDTO.setThumbnail(magazine.getBoardThumbnail());
		magazineFormDTO.setTags(magazine.getBoardTag());
		
		System.out.println("magazine.getBoardTag(): "+magazine.getBoardTag());
		
		List<String> dbTagList = Arrays.asList(magazine.getBoardTag().split(","));
		System.out.println(dbTagList);
		
		model.addAttribute("dbTagList",dbTagList);
		model.addAttribute("magazineSeq",bSeq);
		
		model.addAttribute("magazineFormDTO", magazineFormDTO);
		
		return "community/magazineUpdateForm";
	}
	
	@PostMapping("/updateBoard")
	public String updateMagazine(@ModelAttribute @Valid MagazineFormDTO magazineFormDTO, BindingResult bindingResult
			,@RequestPart MultipartFile file, HttpServletRequest request, HttpSession session, Model model) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/magazineUpdateForm";
		}
		System.out.println(request.getParameter("thumb"));
		
		if(magazineFormDTO.getFile() != null && !magazineFormDTO.getFile().isEmpty()) {
			uploadFileCreate(magazineFormDTO.getFile(),request, "community/file/");

			magazineFormDTO.setThumbnail(magazineFormDTO.getFile().getOriginalFilename());
		}else {
			magazineFormDTO.setThumbnail(request.getParameter("thumb"));
		}
		
		String tags = magazineFormDTO.getTags();
		magazineFormDTO.setTags(tags.replaceAll("#", ""));
		
		System.out.println(magazineFormDTO);
		
		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			throw new LoginException("??????????????????", "/login");
		}
		Long magazineSeq = Long.parseLong(request.getParameter("magazineSeq"));
		Magazine dbMagazine = boardService.readMagazine(magazineSeq);
        dbMagazine.setBoardSubject(magazineFormDTO.getSubject());
        dbMagazine.setBoardContent(magazineFormDTO.getContent());
        dbMagazine.setBoardThumbnail(magazineFormDTO.getThumbnail());
        dbMagazine.setBoardTag(magazineFormDTO.getTags());
		
		try{
			boardService.updateMagazine(dbMagazine);
		}catch(Exception e) {
			throw new LoginException("?????? ??????", "magazine");
		}
		//boardService.writeMagazine(magazineFormDTO, user);

		throw new LoginException("?????? ??????", "magazine");
		
	}
	
	
	// MultipartFile??? ???????????? ????????? ??????
	private void uploadFileCreate(MultipartFile picture, HttpServletRequest request, String path) {
			
		String orgFile = picture.getOriginalFilename();  // : ????????? ??? ????????? ?????? ??????.
//		String uploadPath = request.getServletContext().getRealPath("/") + path; // ??????????????? ?????? ?????? ?????? img?????? ?????? ????????? ????????? ????????????,
		String uploadPath = "C:/Users/2016005/git/tripamigo/src/main/resources/static/uploadFiles/";
		System.out.println(uploadPath);
		
		File fpath = new File(uploadPath);   	
		if(!fpath.exists()) fpath.mkdirs();  // ??????????????? ????????? ?????????. 
			
		try {
			picture.transferTo(new File(uploadPath + orgFile)); 
				// picture : ????????? ??? ????????? ?????? ?????????. 
				//transferTo : ????????? ??? ????????? ????????? ?????? File??? ??????.??? ??????????????? ?????????.
				
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/imgupload")
	public String imgupload(HttpServletRequest request, HttpServletResponse response, @RequestParam("upload") MultipartFile file, Model model) {
//		String path = request.getServletContext().getRealPath("/") + "culture/board/imgfile/";
		String path = "C:/Users/2016005/git/tripamigo/src/main/resources/static/uploadFiles/";
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		
		if(!file.isEmpty()) {
			uploadFileCreate(file, request, path);
		}else {
		}
		//upload : ckeditor?????? ????????? ??????????????????????????? ????????? ?????????.
		String fileName  = file.getOriginalFilename();
		model.addAttribute("fileName", "/uploadFiles/" + fileName);
		model.addAttribute("CKEditorFuncNum", request.getParameter("CKEditorFuncNum"));
		
		return "community/ckeditor";
	}

	@GetMapping("/magazinePage")
	public String magazineDetail(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			CommentFormDTO commentFormDTO, Model model) {

		Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
		Magazine magazine = boardService.readMagazine(boardSeq);

		List<Comment> commentList = commentService.commentList(0, magazine.getBoardSeq());

		model.addAttribute("magazine", magazine);
		model.addAttribute("commentList", commentList);
		
		User loginUser = (User)session.getAttribute("loginUser");
		// recommendStatus??? 1?????? ??????OK,   0?????? ??????NO(?????? ??? ?????? ??????)
		
		Map<Comment, Boolean> commentListMap = new TreeMap<Comment, Boolean>((o1,o2)->o2.getCommentSeq().intValue()-o1.getCommentSeq().intValue());
		System.out.println("commentListMap: "+commentListMap.toString());
		boolean isRecommend=false;
		
		List<Recommend> userRecommendList = new ArrayList<Recommend>();
		
		Long status = 1L;
		if(loginUser!=null) {
			userRecommendList = recommendService.userRecommendList(loginUser.getUserSeq(), RecommendType.COMMENT, true);
			for(Comment c : commentList) {
				System.out.println("c.getCommentSeq().toString() : "+ c.getCommentSeq().toString());
				for(Recommend r : userRecommendList) {
					System.out.println("r.getContentSeq().toString() : "+ r.getContentSeq().toString());
					if(c.getCommentSeq().toString().equals(r.getContentSeq().toString()) || c.getCommentSeq().toString()==r.getContentSeq().toString()) {
						isRecommend=true;
						break;
					}
				}
				if(isRecommend) {
					commentListMap.put(c, true);	
				}else {
					commentListMap.put(c, false);	
				}
				isRecommend=false;
				
			}
			model.addAttribute("commentListMap",commentListMap);
		}else {
			for(Comment c: commentList) { 
				commentListMap.put(c, false);
			}
			model.addAttribute("commentListMap",commentListMap);
		}
		System.out.println("userRecommendList : " + userRecommendList.toString());
		System.out.println("commentListMap: "+commentListMap.toString());
		
		//???????????????
		Map<Comment, Integer> countCommentRecommend = recommendService.countCommentRecommend(commentList);
		model.addAttribute("countCommentRecommend",countCommentRecommend);
		
		
		
		//??????
		String tagList="";
		if(magazine.getBoardTag()!=null) {
			String[] tags = magazine.getBoardTag().split(",");
			for(int i =0; i<tags.length; i++) {
				tagList += "#"+tags[i];
				if(i<tags.length-1) {
					tagList += ",";
				}
			}
		}
		System.out.println(tagList);
		model.addAttribute("tagList",tagList);

		boardHitsUp(request, response, boardSeq);

		System.out.println("commentList:"+commentList.toString());
		return "community/magazinePage";
	}

	@RequestMapping(value = "/recommend", method = RequestMethod.POST)
	public @ResponseBody boolean commentRecommend(@RequestParam("contentSeq") Long contentSeq,
			@RequestParam("userSeq") Long userSeq, @RequestParam("type") int type) {
		System.out.println(contentSeq);
		System.out.println(userSeq);
		System.out.println(type);
		RecommendType recommendType = null;
		switch(type) {
		case 0 : recommendType = RecommendType.BOARD; break;
		case 1 : recommendType = RecommendType.PLAN; break;
		case 2 : recommendType = RecommendType.INFO; break;
		case 3 : recommendType = RecommendType.COMMENT; break;
		}
		
		Recommend dbRecommend = recommendService.readRecommend(userSeq, recommendType, contentSeq);
		
		if (dbRecommend == null) {
			Recommend recommend = new Recommend();
			recommend.createRecommend(userSeq, recommendType, contentSeq);
			
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

	@RequestMapping("/deleteBoard") ///// ?????? ??????
	public String deleteBoard(HttpSession session, HttpServletRequest request, Model model) {
		Long boardSeq = Long.parseLong(request.getParameter("boardSeq"));
		int type = Integer.parseInt(request.getParameter("type"));

		Magazine magazine = boardService.readMagazine(boardSeq);
		magazine.setBoardStatus(false);
		User user = (User) session.getAttribute("loginUser");

		try {
			
			if(user != null || user.getUserId().equals(magazine.getUser().getUserId()) || user.getUserId().equals("admin")) {
				boardService.delete(magazine);
				System.out.println("?????? ??????");
			}else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("?????? ??????");
			throw new LoginException("???????????????", "/community/home");
		}
		System.out.println("?????? ??????");
		switch (type) {
		// 0 : ??????
		case 0:
			throw new LoginException("??? ?????? ??????", "/community/review");
		// 1: ?????????
		case 1:
			throw new LoginException("??? ?????? ??????", "/community/magazine");
		// 2: ????????????
		case 2:
			throw new LoginException("??? ?????? ??????", "/community/promotion");
		}
		return "";
	}
	
	

	@PostMapping("/comment")
	public String commentWrite(CommentFormDTO commentFormDTO, HttpSession session, Model model) throws Exception {
		System.out.println("comment?????????");

		User user = (User) session.getAttribute("loginUser");
		int boardType = Integer.parseInt(commentFormDTO.getBoardType());
		String boardSeq = commentFormDTO.getBoardSeq();

		int contentType = 0; // 0????????????, 1????????????(boardType??? ?????? ??????)

		String page = "";
		switch (boardType) { // boardType??? ?????? redirect???????????? ?????????.
		case 1:
			page = "magazinePage?boardSeq=";
			contentType = 0;
			break;
		case 2:
			page = "infoPage?infoSeq=";
			contentType = 2;
			break;
		}
		try {
			commentService.writeComment(contentType, commentFormDTO, user);
		} catch (Exception e) {
			throw new LoginException("?????? ?????? ?????? : ????????? ??????", page + boardSeq);
		}
		throw new LoginException("?????? ?????? ??????", page+boardSeq);
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
		switch (bType) { // boardType??? ?????? redirect???????????? ?????????.
		case 1:
			page = "magazinePage?boardSeq=";
			break;
		case 2: 
			page = "infoPage?infoSeq=";
			break;
		}
		try {
			commentService.deleteComment(comment);
			return "redirect:" + page + bSeq;
		} catch (Exception e) {
			throw new LoginException("?????? ?????? ??????", "redirect:" + page + bSeq);
		}

	}
	
}
