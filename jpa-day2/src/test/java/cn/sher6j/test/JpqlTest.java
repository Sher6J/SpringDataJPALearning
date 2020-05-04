package cn.sher6j.test;

import cn.sher6j.dao.CustomerDao;
import cn.sher6j.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author sher6j
 * @create 2020-05-04-17:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testFindByName() {
        Customer customer = customerDao.findByName("James");
        System.out.println(customer);
    }

    @Test
    public void testFindByNameAndId() {
//        Customer customer = customerDao.findByNameAndId("James", 2l);
        Customer customer = customerDao.findByNameAndId(2l, "James");
        System.out.println(customer);
    }

    /**
     * SpringDataJpa中使用jpql完成 更新/删除操作时：
     *     需要手动添加事务的支持
     *     默认会执行结束后，回滚事务
     */
    @Test
    @Transactional//添加事务支持，否则TransactionRequiredException
    @Rollback(value = false)//不自动回滚
    public void testUpadateCustomer() {
        customerDao.updateCustomer(4l, "sherlock");
    }

    @Test
    public void testFindAllUseSql() {
        List<Object[]> list = customerDao.findAllUseSql();
        list.forEach(object -> {
            System.out.println(Arrays.toString(object));
        });
    }

    @Test
    public void testFindAllUseSqlByCondition() {
        List<Object[]> list = customerDao.findAllUseSqlByCondition("James");
        list.forEach(object -> {
            System.out.println(Arrays.toString(object));
        });
    }

    /**
     * 测试
     */
    @Test
    public void testFindByCustName() {
        Customer customer = customerDao.findByCustName("James");
        System.out.println(customer);
    }

    @Test
    public void testFindByCustNameLike() {
        List<Customer> customers = customerDao.findByCustNameLike("J%");
        customers.forEach(System.out::println);
    }

    @Test
    public void testFindByCustNameLikeAndCustIndustry() {
        List<Customer> customers = customerDao.findByCustNameLikeAndCustIndustry("J%", "运动员");
        customers.forEach(System.out::println);
    }


}
