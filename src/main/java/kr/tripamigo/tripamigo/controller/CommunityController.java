package kr.tripamigo.tripamigo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.service.BoardService;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private BoardService svc;
	
    @RequestMapping("/home")
    public String home(Model model) {
        return "community/home";
    }
    
    @GetMapping("/magazine")
    public String magazine(Model model) {
    	
    	List<Magazine> magazineList = svc.magazineList();
    	model.addAttribute(magazineList);
    	
    	
    	return "community/magazine";
    }

}
