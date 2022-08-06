# NTS 인턴십 면접 사전 과제

<h5>게시글과 댓글 기능을 가진 게시판 구현</h5>

# ERD
![erd](https://user-images.githubusercontent.com/81704910/183257328-d1e083a6-c64d-4dc6-b645-7eb95d602915.png)


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

