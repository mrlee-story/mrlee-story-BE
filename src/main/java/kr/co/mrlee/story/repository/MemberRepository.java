package kr.co.mrlee.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mrlee.story.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	boolean existsByTelNumber(String telNumber);

	MemberEntity findByEmail(String email);

	MemberEntity findByMemberNumber(int memberNumber);

}
