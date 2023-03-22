package com.minhyuk.service;

import com.minhyuk.entity.SearchHistory;
import com.minhyuk.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryService {
    @Autowired
    private SearchHistoryRepository repository;

    public List<SearchHistory> getAll(){
        return this.repository.findAll();
    }

    public SearchHistory getHistory(String keyword) {
        Optional<SearchHistory> historyOpt = this.repository.findById(keyword);
        return historyOpt.orElse(null);
    }

    public void register(SearchHistory entity) {
        SearchHistory history = getHistory(entity.getKeyword());
        BigDecimal count = BigDecimal.ONE;
        if (!ObjectUtils.isEmpty(history))
            count = history.getCount().add(BigDecimal.ONE);
        entity.setCount(count);
        this.repository.save(entity);
    }
}
