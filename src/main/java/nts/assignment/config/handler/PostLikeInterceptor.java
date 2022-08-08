package nts.assignment.config.handler;

import lombok.extern.slf4j.Slf4j;
import nts.assignment.domain.AnonymousLike;
import nts.assignment.domain.Post;
import nts.assignment.repository.AnonymousLikeRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class PostLikeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI(); // => /post/{id}/like
        String remoteAddr = request.getRemoteAddr();

        log.info("URI = {}",requestURI);
        log.info("IP = {}",remoteAddr);


        String[] splitURI = requestURI.split("/");
        long pId = Long.parseLong(splitURI[2]);

        request.setAttribute("IP",remoteAddr);
        return true;
    }

//    private boolean dayPassCheck(AnonymousLike action) {
//        int now = LocalDateTime.now().getSecond();
//        int actionDate = action.getActionDate().getSecond();
//        if((actionDate - now) < MINUTE){
//            log.info("time passed");
//            return true;
//        }
//        return false;
//    }
}
