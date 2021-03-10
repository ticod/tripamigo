package kr.tripamigo.tripamigo.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
@Getter @Setter
public class Magazine extends Board {



}
