package kr.tripamigo.tripamigo.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.tripamigo.tripamigo.util.CipherUtil;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CipherUtil cipherUtil;

    public void join(UserFormDTO userFormDTO) throws Exception {

    	User user = new User();
        
    	user.setUserId(userFormDTO.getId());
    	user.setUserStatus(true);

    	user.setUserSalt(cipherUtil.generateSalt());
    	user.setUserPw(cipherUtil.hashEncoding(userFormDTO.getPassword(), user.getUserSalt()));
        
        user.setUserEmail(userFormDTO.getEmail());
        user.setUserNickname(userFormDTO.getNickname());
        String birthString = userFormDTO.getYear()+"-"+userFormDTO.getMonth()+"-"+userFormDTO.getDay();
        LocalDateTime birth = LocalDate.parse(birthString).atStartOfDay();
        user.setUserBirth(birth);
        user.setUserGender(userFormDTO.getGender());

        userRepository.save(user);
    }
    
    public User selectUserOne(String userid) {
    	return userRepository.findByUserId(userid).orElseGet(() -> null);
    }

}
