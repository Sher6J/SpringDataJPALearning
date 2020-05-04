package cn.sher6j.test;

import cn.sher6j.dao.CustomerDao;
import cn.sher6j.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author cn.sher6j
 * @create 2020-05-04-11:04
 */
@RunWith(SpringJUnit4ClassRunner.class)  //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 测试根据id查询
     */
    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(2l);
        System.out.println(customer);
    }

    /**
     * save：保存或者更新
     *   根据传递的对象是否存在主键id，如果没有id主键属性，就是保存
     *   存在id主键属性，根据id查询数据，更新数据
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustName("Holmes");
        customer.setCustLevel("vip");
        customer.setCustIndustry("侦探");
        customerDao.save(customer);
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer();
        customer.setCustId(5l);
        customer.setCustName("Sherlock");
        customer.setCustLevel("vip");
        customer.setCustIndustry("侦探");
        customerDao.save(customer);
    }

    /**
     * 根据id删除
     */
    @Test
    public void testDelete() {
        customerDao.delete(5l);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> list = customerDao.findAll();
        list.forEach(System.out::println);
    }

    /**
     * 测试统计查询
     */
    @Test
    public void testCount() {
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 测试：判断id为4的客户是否存在
     */
    @Test
    public void textExits() {
        boolean exists = customerDao.exists(4l);
        System.out.println(exists);
    }

    /**
     * 根据id从数据库查询
     *
     *     findOne和getOne区别
     *
     *     findOne:
     *         底层em.find()           立即加载
     *     getOne：
     *         底层em.getReference()   延迟加载 和事务有关
     *         返回的是一个客户的动态代理对象，什么时候用什么时候查
     */
    @Test
    @Transactional//不加事务会报错org.hibernate.LazyInitializationException: could not initialize proxy - no Session
    public void testGetOne() {
        Customer customer = customerDao.getOne(4l);
        System.out.println(customer);
    }
}
