package jpabasic.datepickproject.service.post;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabasic.datepickproject.common.entity.post.SearchKeyword;
import jpabasic.datepickproject.dto.post.response.SearchKeywordListResponseDto;
import jpabasic.datepickproject.dto.post.response.SearchKeywordResponseDto;
import jpabasic.datepickproject.repository.post.KeywordRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeywordService {

	private final KeywordRepository keywordRepository;

	// 키워드 저장 or 검색 횟수 증가
	@Transactional
	public void saveOrUpdateSearchKeyword(String keyword) {
		keywordRepository.findByKeyword(keyword)
			.ifPresentOrElse(
				SearchKeyword::increaseCount, // 존재하면 count 증가
				() -> keywordRepository.save(new SearchKeyword(keyword)) // 없으면 새로 저장
			);
	}

	// 역대 인기 키워드(검색어) 조회 API
	// DB에서 검색어 TOP 10을 가져와 순위, 응답 메시지와 함께 반환
	public SearchKeywordListResponseDto getTop10SearchKeywords() {
		// DB에서 인기 키워드 TOP 10 가져옴.
		List<SearchKeyword> top10Keywords = keywordRepository.findTop10ByOrderByCountDesc();

		// 인기 키워드에 순위 부여, SearchKeywordResponseDto로 변환
		/* [0]부터 [top10Keywords.size() - 1]까지 연속된 정수 스트림(인덱스 리스트) 생성
		 * i(인덱스)를 사용해 top10Keywords 리스트에서 i번째 데이터를 가져옴.
		 * [i + 1]을 순위로 사용해 SearchKeywordResponseDto 객체 생성.
		 * toList(): 스트림을 리스트 형태로 변환하여 반환
		 */
		List<SearchKeywordResponseDto> keywordList = IntStream.range(0, top10Keywords.size())
			.mapToObj(i -> new SearchKeywordResponseDto(i + 1, top10Keywords.get(i)))
			.toList();

		// SearchKeywordListResponseDto에 답아 응답 메시지와 함께 반환
		return new SearchKeywordListResponseDto(keywordList);
	}

}
