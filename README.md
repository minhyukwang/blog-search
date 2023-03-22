# blog-search </br>
- Spring Boot, JPA, Gradle </br>
- jar 파일 위치경로 </br>
  https://drive.google.com/file/d/1VCD6mhS3cXC-TyMDxwhXR3TAhkU90otV/view?usp=sharing
- 호출 API Sample </br>
  1. 키워드 검색 localhost:9292/blogs?query=맛집&page=1&sort=accuracy&size=10 </br>
    - keyword : 검색어
    - sort: recency(최신순), 기본 값 accuracy </br>
    - page: pageNumber </br>
    - size: pageSize </br>
  2. 인기검색어 localhost:9292/popular </br>

- 외부 라이브러리 사용 </br>
 com.h2database:h2:1.4.200 => H2 DB연동 </br>
 com.google.code.gson:gson:2.8.9 => String객체 Json Type으로 변환 </br>
