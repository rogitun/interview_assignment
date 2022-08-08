package nts.assignment.config.handler;

import nts.assignment.repository.AnonymousLikeRepository;
import nts.assignment.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AnonymousLikeRepository repository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PostLikeInterceptor(repository,postRepository))
                .order(1)
                .addPathPatterns("/post/*/like");
    }
}
