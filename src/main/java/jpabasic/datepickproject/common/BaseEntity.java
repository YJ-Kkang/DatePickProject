package jpabasic.datepickproject.common;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Comment("생성일")
	@Column(
		name = "created_at",
		nullable = false
	)
	@CreatedDate
	private LocalDateTime createdAt;

	@Comment("수정일")
	@Column(
		name = "updated_at",
		nullable = false
	)
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Comment("삭제 여부")
	@ColumnDefault("0")
	@Column(
		name = "is_deleted",
		nullable = false,
		columnDefinition = "TINYINT"
	)
	private boolean isDeleted = false;

	@Comment("삭제일")
	@Column(
		name = "deleted_at"
	)
	@LastModifiedDate
	private LocalDateTime deletedAt;

	protected BaseEntity() {
	}

	// 소프트 딜리트(해당 메서드 호출하면 true가 됨, 기본값은 false)
	public void markAsDeleted() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}
}