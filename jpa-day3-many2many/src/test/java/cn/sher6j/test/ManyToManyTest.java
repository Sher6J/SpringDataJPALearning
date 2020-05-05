package cn.sher6j.test;

import cn.sher6j.dao.RoleDao;
import cn.sher6j.dao.UserDao;
import cn.sher6j.domain.Role;
import cn.sher6j.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sher6j
 * @create 2020-05-05-11:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一个用户并保存一个角色
     *     多对多放弃维护权，被动的一方放弃，角色被选，所以放弃维护权
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        User user = new User();
        user.setUserName("james");

        Role role = new Role();
        role.setRoleName("篮球运动员");

        user.getRoles().add(role);
        role.getUsers().add(user);

        userDao.save(user);
        roleDao.save(role);
    }

    /**
     * 测试级联添加，保存一个用户的同时保存用户的关联角色
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        User user = new User();
        user.setUserName("james");

        Role role = new Role();
        role.setRoleName("篮球运动员");

        user.getRoles().add(role);
        role.getUsers().add(user);

        userDao.save(user);
    }

    /**
     * 级联删除，删除id为1的用户，同时删除关联对象
     * 级联删除一定要慎用！！！！！！
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove() {
        User user = userDao.findOne(1l);
        userDao.delete(user);
    }
}
