package com.kakao.kakaoblog;

import com.google.gson.Gson;
import com.kakao.kakaoblog.dto.BlogDto;
import com.kakao.kakaoblog.dto.PageResult;
import com.kakao.kakaoblog.entity.SearchHistory;
import com.kakao.kakaoblog.service.SearchHistoryService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
public class KakaoBlogController {

    @Value("${kakao.blog.api.key}")
    private String apiKey;
    @Autowired
    private SearchHistoryService service;

    @GetMapping("/blogs")
    public PageResult<BlogDto> getBlogs(@RequestParam(value = "keyword") String keyword,
                                        Pageable pageable) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // sort recency(최신순), 기본 값 accuracy
        String sort = !ObjectUtils.isEmpty(pageable.getSort()) ? "recency" : "accuracy";
        // page 결과 페이지 번호, 1~15 사이의 값
        int page = !ObjectUtils.isEmpty(pageable.getPageNumber()) ? pageable.getPageNumber() : 1;
        // size 한 페이지에 보여질 문서 수, 1~30 사이의 값, 기본 값 15
        int size = !ObjectUtils.isEmpty(pageable.getPageSize()) ? pageable.getPageSize() : 15;

        String url = "https://dapi.kakao.com/v2/search/blog?" +
                "query=" + keyword + "&sort=" + sort + "&page=" + page + "&size=" + size;

        // 검색어 업데이트
        SearchHistory history = new SearchHistory();
        history.setKeyword(keyword);
        this.service.register(history);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "KakaoAK " + apiKey)
                .build();
        Gson gson = new Gson();
        try (Response response = client.newCall(request).execute()) {
            if (!ObjectUtils.isEmpty(response) && !ObjectUtils.isEmpty(response.body())) {
                PageResult entity = gson.fromJson(response.body().string(), PageResult.class);
                entity.setTotalElements(((Double) entity.getMeta().get("total_count")).intValue());
                entity.setPage(page);
                entity.setTotalPages(((Double) entity.getMeta().get("pageable_count")).intValue());
                entity.setSize(size);
                return entity;
            } else
                return null;
        }
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SearchHistory>> getSearchHisory() throws IOException {
        //
        List<SearchHistory> list = this.service.getAll();
        if(ObjectUtils.isEmpty(list))
            return null;
        list = list.stream().sorted(Comparator.comparing(SearchHistory::getCount, Comparator.reverseOrder())).toList();
        int lastSize = Math.min(list.size(), 9);
        list = list.subList(0, lastSize);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        return ResponseEntity.ok().headers(headers).body(list);
    }

}
