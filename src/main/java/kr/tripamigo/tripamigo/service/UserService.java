package kr.tripamigo.tripamigo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.repository.UserJpaRepository;

@Service
@Transactional
public class UserService {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void join(UserFormDTO userFormDTO) throws Exception {
    	User user = new User();

    	//userseq / userstatus
        
    	user.setUserId(userFormDTO.getId());
    	user.setUserStatus(true);
        user.setUserPw(userFormDTO.getPassword());
        
        user.setUserSalt("");
        
        user.setUserEmail(userFormDTO.getEmail());
        user.setUserNickname(userFormDTO.getNickname());
        String birth = userFormDTO.getYear()+"-"+userFormDTO.getMonth()+"-"+userFormDTO.getDay();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date userbirth = null;
        userbirth = sdf.parse(birth);
        
        System.out.println(userbirth);
        user.setUserBirth(userbirth);
        user.setUserGender(userFormDTO.getGender());
        user.setUserSalt("");
        
        System.out.println(user);
        userJpaRepository.save(user);
    }

}