package kr.tripamigo.tripamigo.domain;

import kr.tripamigo.tripamigo.dto.OAuthKakaoInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "USER")
@Getter @Setter @ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "USER_STATUS")
    private boolean userStatus;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_SALT")
    private String userSalt;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_NICKNAME")
    private String userNickname;

    @Column(name = "USER_BIRTH")
    private LocalDateTime userBirth;

    @Column(name = "USER_GENDER")
    private int userGender;

    @Column(name = "USER_RANK")
    private int userRank;

    @Column(name = "USER_PROFILE_IMAGE")
    private String userProfileImage;

    @Column(name = "USER_ACCESS_TOKEN")
    private String userAccessToken;

    @Column(name = "USER_REFRESH_TOKEN")
    private String userRefreshToken;

    @Column(name = "USER_REGDATE", insertable=false)
    private LocalDateTime userRegdate;

    @Column(name = "USER_RECENT_DATE", insertable=false)
    private LocalDateTime userRecentDate;

    @Column(name = "USER_ALERT_CHK_DATE", insertable=false)
    private LocalDateTime userAlertChkDate;

    public User joinOAuthUser(String userId, OAuthKakaoInfoDTO infoDTO,
                              int gender, int rank, OAuthTokenDTO tokens) {
        this.userId = userId;
        this.userPw = "";
        this.userSalt = "";
        this.userEmail = infoDTO.getEmail();
        this.userNickname = infoDTO.getNickname();
        this.userBirth = infoDTO.getBirth();
        this.userGender = gender;
        this.userRank = rank;
        this.userAccessToken = tokens.getAccessToken();
        this.userRefreshToken = tokens.getRefreshToken();
        return this;
    }

}
