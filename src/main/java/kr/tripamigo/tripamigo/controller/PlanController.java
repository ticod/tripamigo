package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PlanController {

    @Autowired
    PlanService planService;

}
