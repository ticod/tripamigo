package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "BOARD")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BOARD_TYPE")
@Getter @Setter @ToString
public abstract class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    @Column(name = "BOARD_STATUS")
    private boolean boardStatus;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "BOARD_SUBJECT")
    private String boardSubject;

    @Column(name = "BOARD_CONTENT")
    private String boardContent;

    @Column(name = "BOARD_THUMBNAIL")
    private String boardThumbnail;

    @Column(name = "BOARD_REGDATE")
    private LocalDateTime boardRegdate;

    @Column(name = "BOARD_HITS")
    private int boardHits;

    @Column(name = "BOARD_TAG")
    private String boardTag;

}
