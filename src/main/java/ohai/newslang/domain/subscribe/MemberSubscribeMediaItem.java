package ohai.newslang.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import ohai.newslang.domain.subscribe.reference.Media;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MemberSubscribeMediaItem {

    @Id
    @GeneratedValue
    @Column(name = "member_subscribe_media_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_item_id")
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_subscribe_item_id")
    private MemberSubscribeItem memberSubscribeItem;
}
