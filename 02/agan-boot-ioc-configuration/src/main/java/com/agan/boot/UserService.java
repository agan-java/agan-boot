package com.agan.boot;




public class UserService {

    public UserBean findUser(){
        UserBean userBean= new UserBean();
        userBean.setUsername("agan");
        userBean.setPassword("123456");
        return userBean;
    }

}
