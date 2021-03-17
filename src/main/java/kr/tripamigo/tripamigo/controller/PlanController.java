package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.PlanFormDTO;
import kr.tripamigo.tripamigo.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/community/plan")
public class PlanController {

    @Autowired
    PlanService planService;

    private final static String REDIRECT_HOME = "redirect:/community/plan";

    /* Read */
    @RequestMapping("")
    public String planHome(Model model) {
        model.addAttribute("planList", planService.listAll());
        return "/plan/list";
    }

    @GetMapping("/detail/{planId}")
    public String planDetail(@PathVariable("planId") Long planId, Model model) {
        model.addAttribute("plan", planService.getPlanBy(planId));
        return "/plan/detail";
    }

    /* Create */
    @GetMapping("/write/first")
    public String planWriteFirst(PlanFormDTO planFormDTO, Model model) {
        return "/plan/write/first";
    }

    @PostMapping("/write/first")
    public String planWriteFirst(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        planFormDTO.setUser(loginUser);
        return REDIRECT_HOME + "/write/second";
    }

    @GetMapping("/write/second")
    public String planWriteSecond(PlanFormDTO planFormDTO, Model model) {
        return "/plan/write/second";
    }

    @PostMapping("/write/second")
    public String planWriteSecond(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        planFormDTO.setUser(loginUser);
        return REDIRECT_HOME + "/write/third";
    }

    @GetMapping("/write/third")
    public String planWriteThird(PlanFormDTO planFormDTO, Model model) {
        return "/plan/write/third";
    }

    @PostMapping("/write/third")
    public String planWriteThird(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        planFormDTO.setUser(loginUser);

        Plan plan = new Plan();
        plan.createFrom(planFormDTO);
        plan = planService.createAndReturn(plan);

        return REDIRECT_HOME + "/" + plan.getSeq();
    }

    /* Update */
    @GetMapping("/update/{planId}")
    public String planUpdate(@PathVariable("planId") Long planId, Model model) {
        return "/plan/update";
    }

    @PostMapping("/update/{planId}")
    public String planUpdate(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {
        return REDIRECT_HOME;
    }

    /* Delete */
    @PostMapping("/delete/{planId}")
    public String planDelete(@PathVariable("planId") Long planId, Model model, HttpSession session) {
        return REDIRECT_HOME;
    }

}
