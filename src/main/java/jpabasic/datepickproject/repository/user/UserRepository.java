package jpabasic.datepickproject.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jpabasic.datepickproject.common.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
