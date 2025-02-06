package jpabasic.datepickproject.dto.post.response;

import jpabasic.datepickproject.common.entity.post.SearchKeyword;
import lombok.Getter;

@Getter
public class SearchKeywordResponseDto {
	// 순위
	private final int rank;

	// 키워드
	private final String keyword;

	// 검색 횟수
	private final long count;

	public SearchKeywordResponseDto(int rank, SearchKeyword searchKeyword) {
		this.rank = rank;
		this.keyword = searchKeyword.getKeyword();
		this.count = searchKeyword.getCount();
	}
}
