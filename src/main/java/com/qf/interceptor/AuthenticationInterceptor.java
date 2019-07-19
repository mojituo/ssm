package com.qf.interceptor;

import com.qf.constant.SsmConstant;
import com.qf.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-18 10:14
 **/


public class AuthenticationInterceptor implements HandlerInterceptor {

    //return false 默认代表拦截  true->放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取session信息
        HttpSession session = request.getSession();
        //2.通过session获取用户信息
        User user = (User) session.getAttribute(SsmConstant.USER);
        //3.判断是否为null
        if (user==null){
            //为null
            response.sendRedirect(request.getContextPath()+"/user/login-ui");;
            return false;
        }else {
            return true;
        }

    }

    //执行完controller,返回modelandview之后执行
    //拦截器的post处理中,修改modelandview
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    //响应数据之前,  查看一个请求执行了多长时间,记录日志,  大多说时候比较鸡肋
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
