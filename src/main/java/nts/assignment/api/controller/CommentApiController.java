package nts.assignment.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nts.assignment.api.controller.response.CommentResponse;
import nts.assignment.domain.dto.CommentDto;
import nts.assignment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/post/{id}/moreComment")
    public ResponseEntity<CommentResponse> getMoreComment(@PathVariable("id") Long id, Pageable pageable) {
        //댓글을 불러왔을때 2개의 경우로 나눈다.
        //1. 가져올 댓글이 1개 이상인 경우 => 데이터 보낸다.
        //2. 가져올 댓글이 없는 경우 => size를 check하여 더 표시할 댓글이 없음을 알린다.
        Page<CommentDto> commentDtos = commentService.getCommentDtos(id, pageable);
        CommentResponse commentResponse;
        if (commentDtos.getNumberOfElements() <= 0) {
            commentResponse = CommentResponse.builder()
                    .comments(null)
                    .size(0)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .statusCode(HttpStatus.NO_CONTENT.value()).build();
        } else {
            commentResponse = CommentResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .size(commentDtos.getNumberOfElements())
                    .comments(commentDtos.getContent())
                    .build();
        }
        return new ResponseEntity<>(commentResponse, commentResponse.getHttpStatus());
    }
}
