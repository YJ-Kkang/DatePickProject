package jpabasic.datepickproject.dto.post.response;

import java.util.List;

import lombok.Getter;

@Getter
public class SearchKeywordListResponseDto {

	// 인기 검색어 관련 안내 메시지 (데이터 있을 경우, 없을 경우)
	private final String message;

	// 기본 메시지를 DTO 내부에서 설정 (비즈니스 로직이 아닌 단순 응답 포맷이기에, 서비스단이 아닌 DTO에서 관리하도록 함)
	private static final String DEFAULT_SUCCESS_MESSAGE = "인기 검색어가 성공적으로 조회되었습니다.";
	private static final String DEFAULT_EMPTY_MESSAGE = "인기 검색어가 존재하지 않습니다.";

	// 인기 키워드 리스트
	private final List<SearchKeywordResponseDto> keywords;

	public SearchKeywordListResponseDto(List<SearchKeywordResponseDto> keywords) {
		// keywords가 비어있으면 DEFAULT_EMPTY_MESSAGE, 아닌 경우 DEFAULT_SUCCESS_MESSAGE
		this.message = keywords.isEmpty() ? DEFAULT_EMPTY_MESSAGE : DEFAULT_SUCCESS_MESSAGE;
		this.keywords = keywords;
	}
}
