# NTS 인턴십 면접 사전 과제

<h5>게시글과 댓글 기능을 가진 게시판 구현</h5>

# 주의사항 #

현재 schema.sql에 sequence table이 누락되어 있습니다.

테스트를 하기 위해선 resources/schema.sql에 아래와 같은 SQL문이 추가되어야 합니다.

죄송합니다.

DROP TABLE IF EXISTS hibernate_sequence;

create table hibernate_sequence(
	next_val bigint
);

insert into hibernate_sequence (next_val) values(1);


# ERD
![erd2](https://user-images.githubusercontent.com/81704910/183444283-d74bb77d-2a91-45a6-84d2-7782917eadd2.png)



# 기술 스택

- Back End
  - Spring Boot, JPA, QueryDsl, Spring Security(Bcrypt Only)
  - MySQL
- Front End
  - HTML, CSS, JS
  - Thymeleaf

# 특이사항

<h5>로그인이 없는 익명 게시판</h5>
<ul>
<li>로그인을 사용하면 게시글의 수정 및 삭제가 너무 단순해진다.</li>
<li>비밀번호를 통한 수정 및 삭제를 하고 중복 추천은 IP등을 활용할 수 있는지 찾아본다.</li>
</ul>

# 결과사진

<h5> 게시글 작성 & 수정 & 추천 & 조회수 등</h5>

![14](https://user-images.githubusercontent.com/81704910/183832395-5e0081e4-b954-4fc6-9bb5-47e56056b5e3.png)


![2](https://user-images.githubusercontent.com/81704910/183831652-fbde959b-82bd-4a15-aaa4-d1244e1e9493.png)

![3](https://user-images.githubusercontent.com/81704910/183831684-3a45ca0d-8869-49cf-8f94-19f337a668cc.png)

![4](https://user-images.githubusercontent.com/81704910/183831711-2a6ee1c7-cb21-4d12-96d6-02bbc87aea05.png)

![6](https://user-images.githubusercontent.com/81704910/183831953-fa2d7dfb-820b-4741-9338-c91d2dda8c35.png)

![7](https://user-images.githubusercontent.com/81704910/183831960-ffe77131-1d83-4d55-96f9-474cecbb8696.png)

<h5> 댓글 삭제 & 더보기 </h5>

![9](https://user-images.githubusercontent.com/81704910/183832164-6df03240-c9b0-461f-b15b-9845aefc3d08.png)

![10-더보기](https://user-images.githubusercontent.com/81704910/183832159-ff41835e-dd60-402a-998a-77a8f620e3e1.png)



<h5> 검색 & 페이징</h5>

![11](https://user-images.githubusercontent.com/81704910/183831764-67b7eba8-1276-491d-bf46-922e00d4ee37.png)

![12-검색](https://user-images.githubusercontent.com/81704910/183831800-da5752fc-58e8-455d-bd66-8b3774d9a7e0.png)

![13](https://user-images.githubusercontent.com/81704910/183831814-3282b115-8681-4877-9780-9ebaf9bc01a4.png)

