package jpabasic.datepickproject.common.entity.like;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "`LIKE`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(
		name = "like_status",
		nullable = false
	)
	private boolean likeStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public Like(User user, Post post) {
		this.user = user;
		this.post = post;
		this.likeStatus = true;
	}

	// 좋아요 상태를 변경 하기 위한 코드.
	// false 로 설정하면 false 고정되기 떄문에 토글이 불가능 하다.
	public void switchLike() {
		this.likeStatus = !this.likeStatus;
	}

	//
	public String checkLike() {
		return this.likeStatus ? "좋아요를 누르셨습니다":"좋아요를 취소 하셨습니다.";
	}
}