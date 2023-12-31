package ohai.newslang.service;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.Member;
import ohai.newslang.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

//        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

//    /**
//     * 회원 수정
//     */
//    @Transactional
//    public void update(Long id, String name) {
//        Member member = memberRepository.findOne(id);
//        member.setName(name);
//    }

//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByName(member.getName());
//        if (!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
}
