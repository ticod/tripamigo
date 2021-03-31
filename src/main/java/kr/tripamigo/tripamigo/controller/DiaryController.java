package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.DiaryFormDTO;
import kr.tripamigo.tripamigo.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/mypage/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    private User getLoginUser(HttpSession session) {
        return (User) session.getAttribute("loginUser");
    }

    @GetMapping("/create")
    String createDiary(Model model) {
        model.addAttribute("diaryFormDTO", new DiaryFormDTO());
        return "diary/create";
    }

    @PostMapping("/create")
    String createDiary(@Valid DiaryFormDTO diaryFormDTO, HttpSession session) {
        diaryService.createDiary(diaryFormDTO, getLoginUser(session));
        return "diary/create";
    }


}
