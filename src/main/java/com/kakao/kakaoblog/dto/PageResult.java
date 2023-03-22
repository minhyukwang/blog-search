package com.kakao.kakaoblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.querydsl.QPageRequest;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
public class PageResult<T> {
    //
    private List<T> documents;
    private Map meta;
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;

}
