package kr.tripamigo.tripamigo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
import org.springframework.web.bind.annotation.RequestParam;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Area;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.dto.CommentFormDTO;
import kr.tripamigo.tripamigo.dto.InfoFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.CommentService;
import kr.tripamigo.tripamigo.service.InfoService;
import kr.tripamigo.tripamigo.service.RecommendService;

@Controller
@RequestMapping("/community")
public class InfoController {

	@Autowired
	private InfoService infoService;

	@Autowired
	private RecommendService recommendService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private BoardService boardService;

	@GetMapping("/info")
	public String info(HttpSession session, Model model) throws Exception {

		List<Info> infoList = infoService.infoList();
		model.addAttribute(infoList);

		// 추천수
		Map<Info, Integer> infoRecommendCountMap = recommendService.countInfoRecommendList(infoList);
		model.addAttribute("infoRecommendCountMap", infoRecommendCountMap);

		// 댓글수
		Map<Info, Integer> infoCommentCountMap = commentService.countCommentList(infoList);
		model.addAttribute("infoCommentCountMap", infoCommentCountMap);

		// 추천수 top5 게시글
		List<Entry<Info, Integer>> list = new ArrayList<>();
		list = new ArrayList<Entry<Info, Integer>>(infoRecommendCountMap.entrySet());
		Collections.sort(list, new Comparator<Entry<Info, Integer>>() {
			@Override
			public int compare(Entry<Info, Integer> o1, Entry<Info, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		List<Entry<Info, Integer>> infoTopFiveList = new ArrayList<>();
		int size = (list.size() < 5) ? list.size() : 5;
		for (int i = 0; i < size; i++) {
			infoTopFiveList.add(list.get(i));
		}
		model.addAttribute("infoRecommendCountSortedMap", infoTopFiveList);

		return "community/info";
	}

	@GetMapping("/infoPage")
	public String infoPage(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			CommentFormDTO commentFormDTO, Model model) {

		// 게시글
		Long infoSeq = Long.parseLong(request.getParameter("infoSeq"));
		Info info = infoService.readInfo(infoSeq);
		model.addAttribute("info", info);

		// 댓글목록
		Map<Comment, Boolean> commentListMap = new TreeMap<Comment, Boolean>(
				(o1, o2) -> o2.getCommentSeq().intValue() - o1.getCommentSeq().intValue());
		boolean isRecommend = false;
		List<Comment> commentList = commentService.commentList(2, infoSeq);
		model.addAttribute("commentList", commentList); // 댓글 존재 여부 확인용
		List<Recommend> userRecommendList = new ArrayList<Recommend>();

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser != null) {
			userRecommendList = recommendService.userRecommendList(loginUser.getUserSeq(), RecommendType.COMMENT, true);
			for (Comment c : commentList) {
				System.out.println("c.getCommentSeq().toString() : " + c.getCommentSeq().toString());
				for (Recommend r : userRecommendList) {
					System.out.println("r.getContentSeq().toString() : " + r.getContentSeq().toString());
					if (c.getCommentSeq().toString().equals(r.getContentSeq().toString())
							|| c.getCommentSeq().toString() == r.getContentSeq().toString()) {
						isRecommend = true;
						break;
					}
				}
				if (isRecommend) {
					commentListMap.put(c, true);
				} else {
					commentListMap.put(c, false);
				}
				isRecommend = false;

			}
			model.addAttribute("commentListMap", commentListMap);
		} else {
			for (Comment c : commentList) {
				commentListMap.put(c, false);
			}
			model.addAttribute("commentListMap", commentListMap);
		}
		System.out.println("userRecommendList : " + userRecommendList.toString());
		System.out.println("commentListMapAndRecommend: " + commentListMap.toString());

		//게시글 유저 추천여부
		Map<Info, Boolean> infoRecommend = new HashMap<Info, Boolean>();
		isRecommend = false;
		
		if(loginUser!=null) {
			userRecommendList = recommendService.userRecommendList(loginUser.getUserSeq(), RecommendType.INFO, true);
			for (Recommend recommend : userRecommendList) {
				if ((info.getInfoSeq().toString().equals(recommend.getContentSeq().toString())
						&& recommend.getRecommendType()==RecommendType.INFO)
						|| 
					(info.getInfoSeq().toString() == recommend.getContentSeq().toString()
						&& recommend.getRecommendType()==RecommendType.INFO)
					) {
					isRecommend = true;
					break;
				}
				System.out.println(isRecommend);
			}
		}else {
		}
		model.addAttribute("isRecommend", isRecommend);
		
		//게시글 추천수
		Map<Info, Integer> infoRecommendCount = new TreeMap<>();
		int recommendCount = 0;
		recommendCount = recommendService.contentRecommendCount(info.getInfoSeq(), RecommendType.INFO, true);
		model.addAttribute("infoRecommendCount", recommendCount);
		
		
		// 댓글추천수
		Map<Comment, Integer> countCommentRecommend = recommendService.countCommentRecommend(commentList);
		model.addAttribute("countCommentRecommend", countCommentRecommend);

		
		infoHitsUp(request, response, infoSeq);

		return "community/infoPage";
	}

	@GetMapping("/infoForm")
	public String infoForm(InfoFormDTO infoFormDTO, Model model) {
		return "community/infoForm";
	}

	@PostMapping("/infoForm")
	public String infoWrite(@ModelAttribute @Valid InfoFormDTO infoFormDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpSession session, Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/infoForm";
		}

		User user = (User) session.getAttribute("loginUser");

		System.out.println(infoFormDTO);

		Area area = returnArea(infoFormDTO.getRating(), infoFormDTO.getBudget(), infoFormDTO.getName(),
				infoFormDTO.getAddress(), infoFormDTO.getLat(), infoFormDTO.getLng());

		try {
			infoService.writeInfo(infoFormDTO, area, user);
		} catch (Exception e) {
			throw new LoginException("글쓰기 실패", "info");
		}

		throw new LoginException("글쓰기 완료", "info");
	}

	@GetMapping("/updateInfo")
	public String readInfoForUpdate(InfoFormDTO infoFormDTO, @RequestParam("infoSeq") String infoSeq,
			HttpSession session, Model model) {
		Long iSeq = Long.parseLong(infoSeq);

		Info dbInfo = infoService.readInfo(iSeq);
		User loginUser = (User) session.getAttribute("loginUser");
		if (!loginUser.getUserId().equals(dbInfo.getUser().getUserId())
				|| loginUser.getUserSeq() != dbInfo.getUser().getUserSeq()) {
			throw new LoginException("본인게시물만 수정 가능", "info");
		}

		infoFormDTO.setSubject(dbInfo.getInfoSubject());
		infoFormDTO.setContent(dbInfo.getInfoContent());
		infoFormDTO.setRating(dbInfo.getArea().getRating() + "");
		infoFormDTO.setBudget(dbInfo.getArea().getBudget() + "");
		infoFormDTO.setName(dbInfo.getArea().getName());
		infoFormDTO.setAddress(dbInfo.getArea().getAddress());
		infoFormDTO.setLat(dbInfo.getArea().getLat() + "");
		infoFormDTO.setLng(dbInfo.getArea().getLng() + "");

		model.addAttribute("infoSeq", iSeq);
		model.addAttribute("infoFormDTO", infoFormDTO);

		return "community/infoUpdateForm";
	}

	@PostMapping("/updateInfo")
	public String infoUpdate(@ModelAttribute @Valid InfoFormDTO infoFormDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpSession session, Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/infoForm";
		}

		Long infoSeq = Long.parseLong(request.getParameter("infoSeq"));
		Info dbInfo = infoService.readInfo(infoSeq);

		Area area = returnArea(infoFormDTO.getRating(), infoFormDTO.getBudget(), infoFormDTO.getName(),
				infoFormDTO.getAddress(), infoFormDTO.getLat(), infoFormDTO.getLng());
		dbInfo.setInfoSubject(infoFormDTO.getSubject());
		dbInfo.setInfoContent(infoFormDTO.getContent());
		dbInfo.setArea(area);

		try {
			infoService.updateInfo(dbInfo);
		} catch (Exception e) {
			throw new LoginException("글수정 실패", "info");
		}

		throw new LoginException("글수정 완료", "info");

	}

	private Area returnArea(String rat, String bud, String nm, String addr, String lt, String lg) {
		int rating = Integer.parseInt(rat);
		int budget = Integer.parseInt(bud.replace(",", ""));
		String name = nm;
		String address = addr;
		double lat = Double.parseDouble(lt);
		double lng = Double.parseDouble(lg);
		Area area = new Area(budget, rating, name, address, lat, lng);
		return area;

	}

	private void infoHitsUp(HttpServletRequest request, HttpServletResponse response, Long infoSeq) {
		String cookieName = infoSeq + "";
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(60); // 초단위

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
				infoService.hitsUp(infoSeq);
			}
		} else { // 쿠키가 없으면.
			System.out.println("쿠키 자체가 없어서 추가하고 조회수 올림.");
			response.addCookie(cookie);
			infoService.hitsUp(infoSeq);
		}
	}

	@RequestMapping("/deleteInfo")
	public String deleteInfo(HttpSession session, HttpServletRequest request, Model model) {
		Long infoSeq = Long.parseLong(request.getParameter("infoSeq"));

		Info info = infoService.readInfo(infoSeq);
		info.setInfoStatus(false);
		User user = (User) session.getAttribute("loginUser");

		try {
			if (user != null || user.getUserId().equals(info.getUser().getUserId())
					|| user.getUserId().equals("admin")) {
				infoService.delete(info);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new LoginException("글삭제실패", "/community/info");
		}
		throw new LoginException("글삭제성공", "/community/info");
	}

}
