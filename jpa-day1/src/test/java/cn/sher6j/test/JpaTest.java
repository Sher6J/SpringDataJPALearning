package cn.sher6j.test;

import cn.sher6j.domain.Customer;
import cn.sher6j.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 注意：真正和数据库打交道的对象都是EntityManager
 * @author sher6j
 * @create 2020-05-03-19:33
 */
public class JpaTest {
    /**
     * 测试jpa的保存
     *     保存一个客户到数据库中
     *   Jpa的操作步骤
     *     1.加载配置文件创建工厂（实体管理器工厂）对象
     *     2.通过实体管理器工厂获取实体管理器
     *     3.获取事务对象，开启事务！！！
     *     4.完成增删改查操作
     *     5.提交事务（回滚事务）
     *     6.释放资源
     */
    @Test
    public void testSave() {
//        //1.加载配置文件创建工厂（实体管理器工厂）对象
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
//        //2.通过实体管理器工厂获取实体管理器
//        EntityManager entityManager = factory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        //3.1.获取事务对象
        EntityTransaction tx = entityManager.getTransaction();
        //3.2.开启事务
        tx.begin(); //开启事务
        //4.完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("James");
        customer.setCustIndustry("篮球");
        //保存
        entityManager.persist(customer); //保存操作
        //5.提交事务
        tx.commit();
        //6.释放资源
        entityManager.close();
//        factory.close();
        //不再关闭工厂，应为工厂封装到工具类中，是公共资源
    }

    /**
     * 根据id查询客户
     * 使用find方法查询：
     *     1.查询的对象就是当前客户对象本身
     *     2.在调用find方法的时候，就会发送SQL语句查询数据
     *   此方式称之为立即加载
     */
    @Test
    public void testFind(){
        //1.通过工具类获取entityManager
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删改查
        /**
         * find：根据id查询数据
         *     class:查询数据的结果需要包装的实体类类型的字节码
         *     id:查询的主键的取值
         */
        Customer customer = em.find(Customer.class, 1L);
        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 根据id查询客户
     * 使用getReference方法
     *     1.获取的对象是一个动态代理对象
     *     2.调用getReference方法不会立即发送SQL语句查询数据库
     *       而是在查询结果对象的时候才会发送查询数据库语句，即什么时候用什么时候查
     *   此方式称之为延迟加载（懒加载）
     *     1.得到的是一个动态代理对象
     *     2.什么时候用什么时候才查询
     */
    @Test
    public void testReference(){
        //1.通过工具类获取entityManager
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删改查
        /**
         * getReference：根据id查询数据
         *     class:查询数据的结果需要包装的实体类类型的字节码
         *     id:查询的主键的取值
         */
        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 删除客户的案例
     * remove(Object)
     */
    @Test
    public void testRemove(){
        //1.通过工具类获取entityManager
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.删除操作
        //3.1.根据id查询客户
        Customer customer = em.find(Customer.class, 1l);
        //3.2.调用remove方法完成删除操作
        em.remove(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 更新客户
     * merge(Object)
     */
    @Test
    public void testUpdate(){
        //1.通过工具类获取entityManager
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.更新操作
        //3.1.根据id查询客户
        Customer customer = em.find(Customer.class, 2l);
        //3.2.更新行业
        customer.setCustIndustry("运动员");
        em.merge(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }
}
