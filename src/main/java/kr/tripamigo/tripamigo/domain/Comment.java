package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "COMMENT")
@Getter @Setter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    @Column(name = "COMMENT_STATUS", insertable=false)
    private boolean commentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "COMMENT_CONTENT_TYPE")
    private int commentContentType;

    @Column(name = "COMMENT_CONTENT_SEQ")
    private Long commentContentSeq;

    @Column(name = "COMMENT_REGDATE", insertable=false)
    private LocalDateTime commentRegdate;

    @Column(name = "COMMENT_CONTENT")
    private String commentContent;

}
