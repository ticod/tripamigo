package kr.tripamigo.tripamigo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RequestParam;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Area;
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
		List<Entry<Info, Integer>> list = new ArrayList<>();
		list = new ArrayList<Entry<Info, Integer>>(infoRecommendCountMap.entrySet());
		Collections.sort(list, new Comparator<Entry<Info, Integer>>() {
			@Override
			public int compare(Entry<Info, Integer> o1, Entry<Info,Integer> o2) {
				return o2.getValue()-o1.getValue();
			}
		});
		List<Entry<Info, Integer>> infoTopFiveList = new ArrayList<>();
		int size = (list.size()<5)? list.size() : 5;
		for(int i = 0; i<size; i++) {
			infoTopFiveList.add(list.get(i));
		}
		model.addAttribute("infoRecommendCountSortedMap", infoTopFiveList);
		
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
		
		Area area = returnArea(
				infoFormDTO.getRating(),
				infoFormDTO.getBudget(),
				infoFormDTO.getName(),
				infoFormDTO.getAddress(),
				infoFormDTO.getLat(),
				infoFormDTO.getLng()
				);
		
		try{
			infoService.writeInfo(infoFormDTO,area,user);
		}catch(Exception e) {
			throw new LoginException("글쓰기 실패", "info");
		}
		
		throw new LoginException("글쓰기 완료","info");
	}
	
	@GetMapping("/updateInfo")
	public String readInfoForUpdate(InfoFormDTO infoFormDTO, @RequestParam("infoSeq")String infoSeq, HttpSession session, Model model) {
		Long iSeq = Long.parseLong(infoSeq);
		
		Info dbInfo = infoService.readInfo(iSeq);
		User loginUser = (User)session.getAttribute("loginUser");
		if(!loginUser.getUserId().equals(dbInfo.getUser().getUserId())||
				loginUser.getUserSeq()!=dbInfo.getUser().getUserSeq()) {
			throw new LoginException("본인게시물만 수정 가능","info");
		}
		
		infoFormDTO.setSubject(dbInfo.getInfoSubject());
		infoFormDTO.setContent(dbInfo.getInfoContent());
		infoFormDTO.setRating(dbInfo.getArea().getRating()+"");
		infoFormDTO.setBudget(dbInfo.getArea().getBudget()+"");
		infoFormDTO.setName(dbInfo.getArea().getName());
		infoFormDTO.setAddress(dbInfo.getArea().getAddress());
		infoFormDTO.setLat(dbInfo.getArea().getLat()+"");
		infoFormDTO.setLng(dbInfo.getArea().getLng()+"");
		
		model.addAttribute("infoSeq", iSeq);
		model.addAttribute("infoFormDTO", infoFormDTO);
		
		return "community/infoUpdateForm";
	}
	
	@PostMapping("/updateInfo")
	public String infoUpdate(@ModelAttribute @Valid InfoFormDTO infoFormDTO, BindingResult bindingResult
			, HttpServletRequest request, HttpSession session, Model model) {
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldError());
			return "community/infoForm";
		}
		
		Long infoSeq = Long.parseLong(request.getParameter("infoSeq"));
		Info dbInfo = infoService.readInfo(infoSeq);
		
		Area area = returnArea(
				infoFormDTO.getRating(),
				infoFormDTO.getBudget(),
				infoFormDTO.getName(),
				infoFormDTO.getAddress(),
				infoFormDTO.getLat(),
				infoFormDTO.getLng()
				);
		dbInfo.setInfoSubject(infoFormDTO.getSubject());
		dbInfo.setInfoContent(infoFormDTO.getContent());
		dbInfo.setArea(area);
		
		try{
			infoService.updateInfo(dbInfo);
		}catch(Exception e) {
			throw new LoginException("글수정 실패", "info");
		}
		
		throw new LoginException("글수정 완료","info");
		
	}
	
	private Area returnArea(String rat, String bud, String nm, String addr, String lt, String lg) {
		int rating = Integer.parseInt(rat);
		int budget = Integer.parseInt(bud.replace(",", ""));
		String name = nm;
		String address = addr;
		double lat = Double.parseDouble(lt);
		double lng = Double.parseDouble(lg);
		Area area = new Area(rating, budget, name, address, lat, lng);
		return area;
		
	}
}
