package nts.assignment.repository.post;

import nts.assignment.domain.Post;
import nts.assignment.domain.dto.MainPostDto;
import nts.assignment.domain.dto.PostFormDto;
import nts.assignment.domain.dto.SinglePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepositoryCustom {

    Page<MainPostDto> getAllPost(Pageable pageable);

    Optional<SinglePostDto> getSinglePost(Long id);

    Optional<PostFormDto> findPostByPassword(Long id);
    Optional<Post> findPostByCredential(Long id, String pwd);

    Optional<Post> findPostWithComments(Long id);
}
