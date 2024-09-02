package kr.co.mrlee.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.mrlee.story.entity.GuestEntity;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Integer> {

	GuestEntity findByGuestNumber(int commenterNumber);

}
