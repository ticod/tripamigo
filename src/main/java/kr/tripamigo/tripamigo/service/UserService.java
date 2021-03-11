package kr.tripamigo.tripamigo.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import kr.tripamigo.tripamigo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;

@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void join(UserFormDTO userFormDTO) {
    	User user = new User();

    	//userseq / userstatus
        
    	user.setUserId(userFormDTO.getId());
    	user.setUserStatus(true);
        user.setUserPw(userFormDTO.getPassword());
        
        user.setUserSalt("");
        
        user.setUserEmail(userFormDTO.getEmail());
        user.setUserNickname(userFormDTO.getNickname());
        String birth = userFormDTO.getYear()+"-"+userFormDTO.getMonth()+"-"+userFormDTO.getDay();
        LocalDateTime ldt = LocalDateTime.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setUserBirth(ldt);
        user.setUserGender(userFormDTO.getGender());
        user.setUserSalt("");
        
        System.out.println(user);
        userRepository.save(user);
    }

}
