package com.minhyuk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
public class BlogDto {
    private String blogname;
    private String contents;
    private Timestamp datetime;
    private String thumbnail;
    private String title;
    private String url;

}
