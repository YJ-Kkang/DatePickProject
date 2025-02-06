package jpabasic.datepickproject.controller.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabasic.datepickproject.dto.post.response.SearchKeywordListResponseDto;
import jpabasic.datepickproject.service.post.KeywordService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/keywords")
@RequiredArgsConstructor
public class KeywordController {
	private final KeywordService keywordService;

	// 역대 인기 키워드(검색어) 조회 기능
	/**
	 * - DB 기반으로 역대 검색어 TOP 10 조회
	 * - 역대 인기 검색어 리스트 및 안내 메시지 반환
	 */
	@GetMapping("/all-time")
	public ResponseEntity<SearchKeywordListResponseDto> getAllTimeSearchKeywords() {
		SearchKeywordListResponseDto response = keywordService.getTop10SearchKeywords();
		return ResponseEntity.ok(response);
	}

}
