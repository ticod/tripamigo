package kr.tripamigo.tripamigo.domain;

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

    @Column(name = "USER_ACCESS_TOKEN", insertable=false)
    private String userAccessToken;

    @Column(name = "USER_REFRESH_TOKEN", insertable=false)
    private String userRefreshToken;

    @Column(name = "USER_REGDATE", insertable=false)
    private LocalDateTime userRegdate;

    @Column(name = "USER_RECENT_DATE", insertable=false)
    private LocalDateTime userRecentDate;

    @Column(name = "USER_ALERT_CHK_DATE", insertable=false)
    private LocalDateTime userAlertChkDate;

}
