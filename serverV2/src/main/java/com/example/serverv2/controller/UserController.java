package com.example.serverv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.User;
import com.example.serverv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    //service注入
    private UserService userService;
//    private ServerConfig serverConfig;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
//    @Autowired
//    public void setServerConfig(ServerConfig serverConfig){this.serverConfig = serverConfig;}


    @PostMapping("/login")
    public Result<?> userLogin(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper  = new LambdaQueryWrapper<>();
        //LamdaQueryWrapper 是为了避免wrapper.eq("uid",user.getId()) 里面的"uid"的硬操作
        wrapper.eq(User::getUname,user.getUname()).eq(User::getPassword,user.getPassword());
// localHost.getHostName() 一般是返回电脑用户名
        System.out.println(user);
        User res = userService.getOne(wrapper);
        if(res==null)
        {
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> userRegister(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUname,user.getUname());
        User res = userService.getOne(wrapper);
        if(res==null){
            //用户名不得重复
            boolean effect = userService.saveOrUpdate(user);
            if(effect){
                return Result.success();
            }
            else {
                return Result.error("-2","用户名无重复但注册失败");
            }
        }
        else {
            return Result.error("-1","用户名重复");
        }
    }
}
