package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "NOTE")
@Getter @Setter
public class Note {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_SEQ")
    private Long id;

    @Column(name = "NOTE_STATUS")
    private boolean status;

    @Column(name = "NOTE_SUBJECT")
    private String subject;

    @Column(name = "NOTE_CONTENT")
    private String content;

    @Column(name = "NOTE_REGDATE")
    private LocalDateTime regdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_SENDER_SEQ")
    private User sender;

    @Column(name = "NOTE_READ")
    private boolean read;

}
