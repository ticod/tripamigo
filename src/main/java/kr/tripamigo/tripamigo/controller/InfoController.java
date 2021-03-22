package kr.tripamigo.tripamigo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.dto.InfoFormDTO;
import kr.tripamigo.tripamigo.service.InfoService;

@Controller
@RequestMapping("/community")
public class InfoController {

	@Autowired
	private InfoService infoService;

	
	@GetMapping("/info")
	public String info(HttpSession session, Model model) {
		List<Info> infoList = infoService.infoList();
		model.addAttribute(infoList);

		return "community/info";
	}
	
	@GetMapping("/infoForm")
	public String infoForm(InfoFormDTO infoFormDTO, Model model) {
		return "community/infoForm";
	}
}
