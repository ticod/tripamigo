package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.plan.PeriodDTO;
import kr.tripamigo.tripamigo.dto.plan.PlanDetailDTO;
import kr.tripamigo.tripamigo.dto.plan.PlanFormDTO;
import kr.tripamigo.tripamigo.service.PlanService;
import kr.tripamigo.tripamigo.util.APIKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/community/plan")
@RequiredArgsConstructor
public class PlanController {

    // autowired
    private final PlanService planService;

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

        LocalDateTime start = periodDTO.getStartDateTime();
        LocalDateTime end = periodDTO.getEndDateTime();

        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        planFormDTO.setPeriodStart(start);
        planFormDTO.setPeriodEnd(end);

        session.setAttribute("planFormDTO", planFormDTO);

        return "redirect:/community/plan/write/second";
    }

    // 세부 일정 입력
    @GetMapping("/write/second")
    public String planWriteSecond(PlanDetailDTO planDetailDTO,
                                  Model model, HttpSession session) {

        // to first page
        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        if (planFormDTO == null
                || planFormDTO.getPeriodStart() == null
                || planFormDTO.getPeriodEnd() == null) {
            return "redirect:/community/plan/write/first";
        }

        model.addAttribute("googleMapAPIKey", APIKey.GOOGLE_MAP);

        // 세션에 planDetailList가 있다면 ?
        @SuppressWarnings("unchecked") // planWriteSecond 메서드 참고 및 아래 세션 setAttribute 참고
        List<PlanDetailDTO> planDetailList = (List<PlanDetailDTO>) session.getAttribute("planDetailList");
        if (planDetailList != null) {
            model.addAttribute("planDetailList", planDetailList);
        }

        model.addAttribute("planDetailDTO", new PlanDetailDTO());
        return "/plan/write/second";
    }

    @PostMapping("/write/second")
    public String planWriteSecondPost(@Valid PlanDetailDTO planDetailDTO, BindingResult bindingResult,
                                      Model model, HttpSession session) {

        model.addAttribute("googleMapAPIKey", APIKey.GOOGLE_MAP);

        @SuppressWarnings("unchecked") // planWriteSecond 메서드 참고 및 아래 세션 setAttribute 참고
        List<PlanDetailDTO> planDetailList = (List<PlanDetailDTO>) session.getAttribute("planDetailList");
        if (planDetailList == null) {
            planDetailList = new ArrayList<>();
        }

        if (bindingResult.hasErrors()) {
            return "/plan/write/second";
        }

        if (planDetailDTO.getTraffic().getOrg().trim().equals("")
                || planDetailDTO.getTraffic().getDes().trim().equals("")
                || planDetailDTO.getTraffic().getInfo().trim().equals("")) {
            planDetailDTO.setTraffic(null);
        }

        if (planDetailDTO.getArea().getAddress().trim().equals("")
                || planDetailDTO.getArea().getPlaceName().trim().equals("")) {
            planDetailDTO.setArea(null);
        }

        planDetailList.add(planDetailDTO);

        session.setAttribute("planDetailList", planDetailList);
        model.addAttribute("planDetailList", planDetailList);

        System.out.println(planDetailDTO);

        // 중복 서브밋 방지 redirect
        return "redirect:/community/plan/write/second";
    }

    @RequestMapping("/write/second/delete/{index}")
    public String planDetailDelete(@PathVariable("index") int index, Model model, HttpSession session) {

        @SuppressWarnings("unchecked") // planWriteSecond 메서드 참고 및 아래 세션 setAttribute 참고
        List<PlanDetailDTO> planDetailList = (List<PlanDetailDTO>) session.getAttribute("planDetailList");

        // 잘못된 접근일 경우
        if (planDetailList == null || planDetailList.size() <= index) {
            return "redirect:/community/plan";
        }

        planDetailList.remove(index);
        session.setAttribute("planDetailList", planDetailList);
        model.addAttribute("planDetailList", planDetailList);
        model.addAttribute("googleMapAPIKey", APIKey.GOOGLE_MAP);
        return "redirect:/community/plan/write/second";
    }

    // 글 입력 (planFormDTO)
    @GetMapping("/write/third")
    public String planWriteThird(Model model, HttpSession session) {
        PlanFormDTO planFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        // to first page
        if (planFormDTO == null
                || planFormDTO.getPeriodStart() == null
                || planFormDTO.getPeriodEnd() == null) {
            return "redirect:/community/plan/write/first";
        }

        @SuppressWarnings("unchecked") // planWriteSecond 메서드 참고 및 아래 세션 setAttribute 참고
        List<PlanDetailDTO> planDetailList = (List<PlanDetailDTO>) session.getAttribute("planDetailList");

        model.addAttribute("planFormDTO", planFormDTO);
        model.addAttribute("planDetailList", planDetailList);

        return "/plan/write/third";
    }

    @PostMapping("/write/third")
    public String planWriteThird(@Valid PlanFormDTO planFormDTO, Model model, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        planFormDTO.setUser(loginUser);

        PlanFormDTO sessionPlanFormDTO = (PlanFormDTO) session.getAttribute("planFormDTO");
        planFormDTO.setPeriodStart(sessionPlanFormDTO.getPeriodStart());
        planFormDTO.setPeriodEnd(sessionPlanFormDTO.getPeriodEnd());

        Plan savePlan = planService.createAndReturn(planFormDTO);

//        @SuppressWarnings("unchecked") // planWriteSecond 메서드 참고 및 아래 세션 setAttribute 참고
//        List<PlanDetailDTO> planDetailList = (List<PlanDetailDTO>) session.getAttribute("planDetailList");
//        planService.savePlanDetails(savePlan, planDetailList);

        return "redirect:/community/plan/detail/" + savePlan.getSeq();
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
