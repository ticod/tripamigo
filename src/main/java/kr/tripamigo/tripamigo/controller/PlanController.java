package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.PeriodDTO;
import kr.tripamigo.tripamigo.dto.PlanFormDTO;
import kr.tripamigo.tripamigo.service.PlanService;
import kr.tripamigo.tripamigo.util.APIKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/community/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    private final static String REDIRECT_HOME = "redirect:/community/plan";

    /*** Read ***/
    // List page
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

    /*** Create ***/
    // 전체 일정 입력 (Period)
    @GetMapping("/write/first")
    public String planWriteFirst(PeriodDTO periodDTO, Model model, HttpSession session) {
        session.setAttribute("planFormDTO", new PlanFormDTO());
        model.addAttribute("periodDTO", periodDTO);
        return "/plan/write/first";
    }

    @PostMapping("/write/first")
    public String planWriteFirstToSecond(@Valid PeriodDTO periodDTO, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "/plan/write/first";
        }

        LocalDateTime start = LocalDateTime.of(periodDTO.getStartYear(), periodDTO.getStartMonth(), periodDTO.getStartDay(), periodDTO.getStartTime(), 0);
        LocalDateTime end = LocalDateTime.of(periodDTO.getEndYear(), periodDTO.getEndMonth(), periodDTO.getEndDay(), periodDTO.getEndTime(), 0);

        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        planFormDTO.setPeriodStart(start);
        planFormDTO.setPeriodEnd(end);

        session.setAttribute("planFormDTO", planFormDTO);

        return REDIRECT_HOME + "/write/second";
    }

    // 세부 일정 입력
    @GetMapping("/write/second")
    public String planWriteSecond(Model model, HttpSession session) {
        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
//        if (planFormDTO == null) {
//            return REDIRECT_HOME + "/write/first";
//        }
        model.addAttribute("googleMapAPIKey", APIKey.GOOGLE_MAP);
        return "/plan/write/second";
    }

    @PostMapping("/write/second")
    public String planWriteSecond(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        planFormDTO.setUser(loginUser);
        return REDIRECT_HOME + "/write/third";
    }

    // 글 입력 (planFormDTO)
    @GetMapping("/write/third")
    public String planWriteThird(Model model, HttpSession session) {
        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        if (planFormDTO == null) {
            return REDIRECT_HOME + "/write/first";
        }
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

    /*** Update ***/
    @GetMapping("/update/{planId}")
    public String planUpdate(@PathVariable("planId") Long planId, Model model) {
        return "/plan/update";
    }

    @PostMapping("/update/{planId}")
    public String planUpdate(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {
        return REDIRECT_HOME;
    }

    /*** Delete ***/
    @PostMapping("/delete/{planId}")
    public String planDelete(@PathVariable("planId") Long planId, Model model, HttpSession session) {
        return REDIRECT_HOME;
    }

}
