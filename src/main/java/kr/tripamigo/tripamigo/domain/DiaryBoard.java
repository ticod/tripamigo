package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "DIARY_BOARD")
@Getter @Setter
public class DiaryBoard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_BOARD_SEQ")
    private Long diaryBoardSeq;

    @Column(name = "DIARY_BOARD_STATUS")
    private boolean diaryBoardStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIARY_SEQ")
    private Diary diary;

    @Column(name = "DIARY_SUBJECT")
    private String diarySubject;

    @Column(name = "DIARY_CONTENT")
    private String diaryContent;

    @Column(name = "DIARY_THUMBNAIL")
    private String diaryThumbnail;

    @Column(name = "DIARY_REGDATE")
    private LocalDateTime diaryRegdate;

}
