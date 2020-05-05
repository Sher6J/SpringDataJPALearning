package cn.sher6j.test;

import cn.sher6j.dao.CustomerDao;
import cn.sher6j.dao.LinkManDao;
import cn.sher6j.domain.Customer;
import cn.sher6j.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sher6j
 * @create 2020-05-05-10:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 保存一个客户，保存一个联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd1() {
        Customer customer = new Customer();
        customer.setCustName("James");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("paul");

        /**
         * 从客户的角度上，发送两条insert语句，发送一条更新语句更新数据库（更新外键）
         * 由于我们配置了客户到联系人的关系，客户可以对外键进行维护
         */
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAdd2() {
        Customer customer = new Customer();
        customer.setCustName("James");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("paul");

        /**
         * 配置联系人到客户的关系（多对一）
         * 只发送了两条insert语句
         */
        linkMan.setCustomer(customer);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 会有一条多余的update语句
     *     由于一的一方可以维护外键，会发送update语句
     *     解决此问题，只需要在一的一方放弃维护权
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd3() {
        Customer customer = new Customer();
        customer.setCustName("James");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("paul");

        //由于配置了多的一方到一的一方的关联关系，当保存的时候，就已经对外键赋值
        linkMan.setCustomer(customer);
        //由于配置了一的一方到多的一方的关联关系，所以又多发送了一条update语句
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 级联添加，保存一个客户的同时，保存客户的所有联系人
     *     需要在操作主体的实体类上，配置cascade属性
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        Customer customer = new Customer();
        customer.setCustName("James");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("paul");

        linkMan.setCustomer(customer);
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
    }

    /**
     * 级联删除：删除客户同时，删除客户所有联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove() {
        Customer customer = customerDao.findOne(1l);
        customerDao.delete(customer);
    }
}
