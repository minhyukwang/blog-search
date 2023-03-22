package com.kakao.kakaoblog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "TB_SEARCH_HISTORY")
@Entity(name = "SearchHistory")
@Getter
@Setter
@NoArgsConstructor
public class SearchHistory {
    @Id
    @Column(name = "KEYWORD")
    private String keyword;
    private BigDecimal count;
}