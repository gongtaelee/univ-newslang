package ohai.newslang.service.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.domain.scrap.MemberScrapNews;
import ohai.newslang.domain.scrap.MemberScrapNewsArchive;
import ohai.newslang.repository.MemberRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsArchiveRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberScrapNewsService {

    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final MemberScrapNewsArchiveRepository memberScrapNewsArchiveRepository;
    private final MemberRepository memberRepository;

    public List<MemberScrapNewsArchive> findNewsArchiveList(Long memberId) throws Exception {
        if (memberScrapNewsRepository.isExistMemberScrapNews(memberId)){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findOne(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findAllWithMemberScrapNewsId(memberScrapNewsId);
            return memberScrapNewsArchiveList;
        }
        return null;
    }

    @Transactional
    public Long addNewsArchive(Long memberId, NewsArchive newsArchive) throws Exception {
        MemberScrapNews memberScrapNews = null;
        if (memberScrapNewsRepository.isExistMemberScrapNews(memberId)){
            memberScrapNews = memberScrapNewsRepository.findOne(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findAllWithMemberScrapNewsId(memberScrapNewsId);
            String newUrl = newsArchive.getNews().getUrl();
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getNews().getUrl() == newUrl){
                    isAlreadyUrl = true;
                    break;
                }
            }
            if (isAlreadyUrl)
            {
                return memberScrapNewsId;
            }
        }
        Member member = memberRepository.findOne(memberId);
        memberScrapNews = MemberScrapNews.newMemberScrapNews(memberScrapNews, member, newsArchive);
        memberScrapNewsRepository.save(memberScrapNews);
        return memberScrapNews.getId();
    }

    @Transactional
    public void removeNewsArchive(Long memberId, String url) throws Exception {
        if (memberScrapNewsRepository.isExistMemberScrapNews(memberId)){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findOne(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findAllWithMemberScrapNewsId(memberScrapNewsId);
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getNews().getUrl() == url){
                    // 삭제
                    memberScrapNewsArchiveRepository.delete(memberScrapNewsId, url);
                    break;
                }
            }
        }
    }
}
