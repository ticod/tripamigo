package kr.tripamigo.tripamigo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.dto.InfoFormDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
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

	
	@GetMapping("/info")
	public String info(HttpSession session, Model model) throws Exception {
		
		List<Info> infoList = infoService.infoList();
		model.addAttribute(infoList);
		
		//추천수
		Map<Info, Integer> infoRecommendCountMap = recommendService.countInfoRecommendList(infoList);
		model.addAttribute("infoRecommendCountMap",infoRecommendCountMap);
		
		//댓글수
		Map<Info, Integer> infoCommentCountMap = commentService.countCommentList(infoList);
		model.addAttribute("infoCommentCountMap", infoCommentCountMap);
		
		//추천수 top5 게시글
		List<Info> sortedList = new ArrayList();
		sortedList.addAll(infoRecommendCountMap.keySet());
		
		Collections.sort(sortedList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Object v1 =infoRecommendCountMap.get(o1);
				Object v2 =infoRecommendCountMap.get(o2);

				return ((Comparable) v2).compareTo(v1);
			}
			
		});
//		Collections.reverse(sortedInfoList);
		List<Info> sortedInfoList = new ArrayList();
		
		int size = (sortedList.size()<5)? sortedList.size():5;  
		for(int i = 0; i<size;i++) {
			sortedInfoList.add(sortedList.get(i));
		}
		Map<Info, Integer> infoRecommendCountSortedMap = recommendService.countInfoRecommendList(sortedInfoList);
		model.addAttribute("infoRecommendCountSortedMap", infoRecommendCountSortedMap);
		
		return "community/info";
	}
	
	
	
	@GetMapping("/infoForm")
	public String infoForm(InfoFormDTO infoFormDTO, Model model) {
		return "community/infoForm";
	}
	
	@PostMapping("/infoForm")
	public String infoWrite(@ModelAttribute @Valid InfoFormDTO infoFormDTO, BindingResult bindingResult
			, HttpServletRequest request, HttpSession session, Model model) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/infoForm";
		}
		User user = (User) session.getAttribute("loginUser");
		
		System.out.println(infoFormDTO);
		
		infoService.writeInfo(infoFormDTO,user);
		
		
		throw new LoginException("글쓰기 완료","info");
		
	}
}
