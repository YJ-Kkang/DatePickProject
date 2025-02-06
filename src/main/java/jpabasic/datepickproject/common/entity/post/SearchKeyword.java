package jpabasic.datepickproject.common.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "search_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeyword {

	// SearchKeyword 식별자
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 키워드
	@Column(
		nullable = false,
		unique = true // 키워드 중복 방지
	)
	private String keyword;

	@Column(
		nullable = false
	)
	private long count = 1L; // 최초 검색 시 1로 초기화

	// 생성자: 새로운 키워드 생성
	public SearchKeyword(String keyword) {
		this.keyword = keyword;
	}

	// 검색 횟수 1 증가
	public void increaseCount() {
		this.count++;
	}
}
