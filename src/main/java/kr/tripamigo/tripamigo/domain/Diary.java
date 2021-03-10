package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "DIARY")
@Getter @Setter
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_SEQ")
    private Long diarySeq;

    @Column(name = "DIARY_STATUS")
    private boolean diaryStatus;

    @Enumerated
    @Column(name = "DIARY_OPEN")
    private OpenScope diaryOpen;

}
