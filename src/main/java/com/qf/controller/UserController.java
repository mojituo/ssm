package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.SendSMSUtil;
import com.qf.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.qf.constant.SsmConstant.*;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-15 20:24
 **/

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private SendSMSUtil sendSMSUtil;

    @Autowired
    private UserService userService;

//跳转到注册页面
    @GetMapping("/register-ui")
    public String register(){

        //转发到注册页面
        return "user/register";
    }

    //user/check-username
    //传统接受参数的方式,不能用来接受json数据
    @PostMapping("/check-username")
    @ResponseBody    //不走视图解析器直接响应  ,如果返回结果为map/pojo类,自动序列化成json
    public ResultVo checkUsername(@RequestBody User user){
        //1.json数据需要反序列化城pojo对象或者map数据
        //1.调用service,判断用户名是否可用

        Integer count = userService.checkUsername(user.getUsername());


        return new ResultVo(0,"成功",count);
        //xhr   ajax引擎
        /*//2.响应数据的map
        Map<String ,Object> result = new HashMap<>();
        result.put("code",0);
        result.put("msg","成功");
        result.put("data",count);

        //3.返回
        return result;*/
    }



    //发送短信
   /* Request URL: http://localhost/user/send-sms
    Request Method: POST*/
    @PostMapping(value = "/send-sms",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String sendSMS(@RequestParam String phone, HttpSession session){
       //1.调用工具发送短信
       String result = sendSMSUtil.sendSMS(session, phone);
       //2.将result信息响应给ajax引擎
       return result;
   }

        //执行注册
//    Request URL: http://localhost/user/register
//    Request Method: POST
    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, String registerCode, HttpSession session, RedirectAttributes redirectAttributes){

        //1.校验验证码
        if (!StringUtils.isEmpty(registerCode)){
            //验证码不为空
            String code = (String) session.getAttribute(CODE);
            if (!code.equals(registerCode)){
                //验证码不正确
                redirectAttributes.addAttribute("registerInfo" ,"验证码不正确");
                return REDIRECT + REGISTER_UI;
            }
        }

        //2.校验参数是否合法
            if (bindingResult.hasErrors() || StringUtils.isEmpty(registerCode)){
            //参数不合法
                String message = bindingResult.getFieldError().getDefaultMessage();
                if (StringUtils.isEmpty(message)){
                    message="验证码为必填项,岂能不填";
                }
                redirectAttributes.addAttribute("registerInfo" ,message);
                return REDIRECT + REGISTER_UI;
            }

        //3.调用service执行注册功能
        // 4.根据service返回的结果跳转到指定的页面

            Integer count = userService.register(user);
        if (count==1){
            //注册成功
            return REDIRECT + LOGIN_UI;
        }else {
            //注册失败
            redirectAttributes.addAttribute("registerInfo","服务器爆炸了");
            return REDIRECT + REGISTER_UI;
        }

    }

//跳转到登录页面
    //get  user/login-ui

    @GetMapping("/login-ui")
    public String lgoinUI(){
        return "/user/login";
    }



//    Request URL: http://localhost/user/login
//    Request Method: POST
    //执行登录功能
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(String username,String password,HttpSession session){
        //1.校验参数是否合法
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

            //参数不合法
            return new ResultVo(1,"用户名和密码为必填项,岂能不填",null);
        }
        //2.调用service执行登录
        User user = userService.login(username,password);

        //3.根据service返回结构判断是否登录成功
        if (user!=null) {
            //4.如果成功就把信息放到session域中
            session.setAttribute(USER,user);
            return new ResultVo(0,"成功",null);
        }else {
//        5.如果失败,响应错误信息给ajax
            return new ResultVo(2,"用户名或密码错误!",null);
        }
    }


































}

