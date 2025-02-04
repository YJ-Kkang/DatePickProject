package jpabasic.datepickproject.common.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jpabasic.datepickproject.common.BaseEntity;
import jpabasic.datepickproject.common.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "POST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	/**
	 *  < 이하 BaseEntity에서 상속받을 요소들 >
	 *  LocalDateTime created_at "생성일"
	 *  LocalDateTime upated_at "수정일"
	 *  LocalDateTime deleted_at "삭제일"
	 */

	// post 식별자
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 유저 식별자(외래키)
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = false // user가 항상 필수 필드인 것을 명시
	)
	@JoinColumn(
		name = "user_id",
		nullable = false
	)
	private User user;

	// post 제목
	@Column(
		name = "post_title",
		nullable = false,
		length = 100 // 제목 길이 제한
	)
	private String title;

	// post 내용
	@Column(
		name = "post_content",
		nullable = false,
		length = 2000 // 내용 길이 제한
	)
	private String content;

	// 게시글에 표시 되는 좋아요 수
	@Column(name = "like_count", nullable = false)
	private long likeCount = 0L; // 기본값 0

	// 소프트 딜리트(기본값 false | true: 삭제된 post 의미)
	//베이스 엔티티에 메서드 만들어서 사용.

	// 생성자
	public Post(User user, String title, String content) {
		this.user = user;
		this.title = title;
		this.content = content;
	}

	public void updatePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// 좋아요 수
	public void changePostLike(long likeCount) {
		this.likeCount = likeCount;
	}
}