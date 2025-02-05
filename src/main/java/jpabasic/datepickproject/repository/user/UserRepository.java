package jpabasic.datepickproject.repository.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jpabasic.datepickproject.common.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	// 이메일 또는 사용자명에 포함된 값을 기준으로 검색 (부분 일치)
	Page<User> findByEmailContainingOrUserNameContaining(
		String email,
		String username,
		Pageable pageable
	);
}
