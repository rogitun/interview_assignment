package nts.assignment.config.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PostLikeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String requestURI = request.getRequestURI(); // => /post/{id}/like
//        String remoteAddr = request.getRemoteAddr();
//
//        log.info("URI = {}",requestURI);
//        log.info("IP = {}",remoteAddr);
        String localAddr = request.getLocalAddr();
        String remoteUser = request.getRemoteUser();
        String remoteAddr = request.getRemoteAddr();

        log.info("local = {} ",localAddr);
        log.info("user = {} ",remoteUser);
        log.info("addr = {} ",remoteAddr);


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
