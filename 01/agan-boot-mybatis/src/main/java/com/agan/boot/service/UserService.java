package com.agan.boot.service;





import com.agan.boot.entity.User;
import com.agan.boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createUser(User obj){
        userMapper.insertSelective(obj);
    }

    public void updateUser(User obj){
        this.userMapper.updateByPrimaryKeySelective(obj);
    }

    public void findExample(){
        log.info("----------------按主键查询： where id=100----------------");
        User user=this.userMapper.selectByPrimaryKey(100);
        log.info(user.toString());

        log.info("----------------查询： where sex=1----------------");
        User sex=new User();
        sex.setSex((byte)1);
        List<User> list=this.userMapper.select(sex);
        log.info("查询sex=1的条数，{}",list.size());

        log.info("----------------查询： where username=? and password=?----------------");
        User user1=new User();
        user1.setUsername("update100");
        user1.setPassword("update100");
        User obj=this.userMapper.selectOne(user1);
        log.info(obj.toString());

        /**
         * 复杂查询用Example.Criteria
         */

        log.info("----------------Example.Criteria查询： where username=? and password=?----------------");
        Example example=new Example(User.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("username","update100");
        criteria.andEqualTo("password","update100");
        List<User> objs=this.userMapper.selectByExample(example);
        log.info("Example.Criteria查询结果，{}",objs.toString());


        log.info("----------------Example.Criteria 模糊查询： where username like ? ----------------");
        example=new Example(User.class);
        criteria=example.createCriteria();
        criteria.andLike("username","%100%");
        objs=this.userMapper.selectByExample(example);
        log.info("Example.Criteria查询结果，{}",objs.toString());


        log.info("----------------Example.Criteria 排序： where username like ? order by id desc ----------------");
        example=new Example(User.class);
        example.setOrderByClause("id desc ");
        criteria=example.createCriteria();
        criteria.andLike("username","%100%");
        objs=this.userMapper.selectByExample(example);
        log.info("Example.Criteria查询结果，{}",objs.toString());


        log.info("----------------Example.Criteria in 查询： where id  in (1,2) ----------------");
        example=new Example(User.class);
        criteria=example.createCriteria();
        List ids=new ArrayList();
        ids.add(1);
        ids.add(2);
        criteria.andIn("id",ids);
        objs=this.userMapper.selectByExample(example);
        log.info("Example.Criteria查询结果，{}",objs.toString());




        log.info("----------------分页查询1----------------");
        User obj2=new User();
        obj2.setSex((byte)1);
        int count=this.userMapper.selectCount(obj2);
        log.info("分页例子：总条数{}",count);
        objs=this.userMapper.selectByRowBounds(obj2,new RowBounds(0,10));
        for (User u:objs){
            log.info("分页例子：第一页{}",u.toString());
        }

        log.info("----------------Example.Criteria分页查询2----------------");
        example=new Example(User.class);
        criteria=example.createCriteria();
        criteria.andEqualTo("sex",1);
        count=this.userMapper.selectCountByExample(example);
        log.info("分页例子：总条数{}",count);

        objs=this.userMapper.selectByExampleAndRowBounds(example,new RowBounds(0,10));
        for (User u:objs){
            log.info("分页例子：第一页{}",u.toString());
        }


    }


}
