package ohai.newslang.domain.subscribe;

import lombok.Getter;
import ohai.newslang.domain.Member;

import javax.persistence.*;

@Entity
@Getter
public class SubscribeKeyword {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String keyword;
}