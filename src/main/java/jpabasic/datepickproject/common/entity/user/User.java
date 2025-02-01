package jpabasic.datepickproject.common.entity.user;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jpabasic.datepickproject.common.BaseEntity;
import lombok.Getter;

@Entity
@Table(name = "USER")
@Getter
public class User extends BaseEntity {

	@Comment("유저 식별자")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT")
	private Long id;

	@Comment("유저명")
	@Column(
		name = "user_name",
		nullable = false
	)
	private String userName;

	@Comment("이메일")
	@Column(
		name = "email",
		nullable = false
	)
	private String email;

	@Comment("비밀번호")
	@Column(
		name = "password",
		nullable = false
	)
	private String password;

	protected User() {

	}

	public User(
		String email,
		String encryptedPassword
	) {
		this.email = email;
		this.password = encryptedPassword;
	}
}
