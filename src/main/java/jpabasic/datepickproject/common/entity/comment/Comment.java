package jpabasic.datepickproject.common.entity.comment;

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
import jpabasic.datepickproject.common.entity.post.Post;
import jpabasic.datepickproject.common.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Column(name = "content")
	private String content;

	public Comment(User user, Post post, String content) {
		this.user = user;
		this.post = post;
		this.content = content;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}

