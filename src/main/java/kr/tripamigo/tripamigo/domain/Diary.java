package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import kr.tripamigo.tripamigo.domain.board.Board;

import java.util.List;

@Entity @Table(name = "DIARY")
@Getter @Setter
public class Diary{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SEQ")
	private User user;
	 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_SEQ")
    private Long diarySeq;

    @Column(name = "DIARY_STATUS")
    private boolean diaryStatus;

    @Enumerated
    @Column(name = "DIARY_OPEN")
    private OpenScope diaryOpen;

    @OneToMany(mappedBy = "diary")
    private List<DiaryBoard> boards;

}
