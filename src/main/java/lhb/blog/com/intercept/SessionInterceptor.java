/*
package lhb.blog.com.intercept;

import lhb.blog.com.entity.User;
import lhb.blog.com.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @author lhb
 * @date 2019/10/12 15:19
 *//*

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies
        ) {
            System.out.println(c.getName());
            if (c.getName().equals("token")){
                String token = c.getValue();
                User user = userMapper.selectUserByPass(token);
                if (user != null) {
                    System.out.println(user.toString());
                    request.getSession().setAttribute("user",user);
                }
                break;
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
*/
