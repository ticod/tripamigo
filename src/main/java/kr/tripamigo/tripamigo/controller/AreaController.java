package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.dto.AreaSummaryDTO;
import kr.tripamigo.tripamigo.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/summary")
    @ResponseBody
    AreaSummaryDTO summary(String address, String placeName) {
        return infoService.getAreaDataBy(address, placeName);
    }

}
