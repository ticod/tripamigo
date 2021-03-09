package kr.tripamigo.tripamigo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity @Table(name = "user")
@Getter @Setter @ToString
public class UserDTO {

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
    private Date userBirth;

    @Column(name = "USER_GENDER")
    private int userGender;

    @Column(name = "USER_RANK")
    private int userRank;

    @Column(name = "USER_ACCESS_TOKEN")
    private String userAccessToken;

    @Column(name = "USER_REFRESH_TOKEN")
    private String userRefreshToken;

    @Column(name = "USER_REGDATE")
    private Date userRegdate;

    @Column(name = "USER_RECENT_DATE")
    private Date userRecentDate;

    @Column(name = "USER_ALERT_CHK_DATE")
    private Date userAlertChkDate;

}
