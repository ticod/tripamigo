package kr.tripamigo.tripamigo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

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
        System.out.println("birth : ======="+birth);
        LocalDateTime ldt = LocalDate.parse(birth).atStartOfDay();
        System.out.println(ldt);
        user.setUserBirth(ldt);
        user.setUserGender(userFormDTO.getGender());
        user.setUserSalt("");
        
        System.out.println(user);
        userRepository.save(user);
    }
    
    public User selectUserOne(String userid) {
    	return userRepository.findByUserId(userid).orElseGet(() -> null);
    }

}
