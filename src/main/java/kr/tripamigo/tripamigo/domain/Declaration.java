package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "DECLARATION")
@Getter @Setter
public class Declaration {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DECLA_SEQ")
    private Long declaSeq;

    @Column(name = "DECLA_STATUS")
    private boolean declaStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "DECLA_TYPE")
    private int declaType;

}
