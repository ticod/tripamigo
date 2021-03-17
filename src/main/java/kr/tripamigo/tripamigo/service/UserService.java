package kr.tripamigo.tripamigo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.tripamigo.tripamigo.dto.OAuthKakaoInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;
import kr.tripamigo.tripamigo.dto.UserIdOAuthType;
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

    public void join(UserFormDTO userFormDTO) throws Exception {

    	User user = new User();
        
    	user.setUserId(userFormDTO.getId());
    	user.setUserStatus(true);

    	user.setUserSalt(CipherUtil.generateSalt());
    	user.setUserPw(CipherUtil.hashEncoding(userFormDTO.getPassword(), user.getUserSalt()));
        
        user.setUserEmail(userFormDTO.getEmail());
        user.setUserNickname(userFormDTO.getNickname());
        String birthString = userFormDTO.getYear()+"-"+userFormDTO.getMonth()+"-"+userFormDTO.getDay();
        LocalDateTime birth = LocalDate.parse(birthString).atStartOfDay();
        user.setUserBirth(birth);
        user.setUserGender(userFormDTO.getGender());

        userRepository.save(user);
    }
    
    public User selectUserOne(String userid) {
    	return userRepository.findByUserId(userid).orElse(null);
    }

    public void joinByKakao(OAuthTokenDTO tokens, OAuthKakaoInfoDTO infoDTO) {
        String userId = UserIdOAuthType.KAKAO.getValue() + infoDTO.getId();
        User user = new User();
        user.joinOAuthUser(userId, infoDTO, 1, 0, tokens);
        userRepository.save(user);
    }

    public User loginByKakao(User user, OAuthTokenDTO tokens) {
        user.setUserAccessToken(tokens.getAccessToken());
        user.setUserRefreshToken(tokens.getRefreshToken());
        return userRepository.save(user);
    }

    public void withdrawal(User loginUser) {
        loginUser.withdrawal();
        userRepository.save(loginUser);
    }
}
