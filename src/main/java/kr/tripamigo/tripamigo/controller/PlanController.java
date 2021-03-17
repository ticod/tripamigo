package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class PlanController {

    @Autowired
    PlanService planService;

    @RequestMapping("/plan")
    public String planHome(Model model) {
        model.addAttribute("planList", planService.listAll());
        return "/plan/list";
    }

}
