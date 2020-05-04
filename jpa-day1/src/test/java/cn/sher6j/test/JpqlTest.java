package cn.sher6j.test;

import cn.sher6j.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * 测试jpql查询
 *     jpql不支持 select * 的写法，但支持select的其他写法
 * @author cn.sher6j
 * @create 2020-05-04-8:42
 */
public class JpqlTest {

    /**
     * 查询全部
     *     jpgl：from Customer
     *     sql：SELECT * FROM cst_customer
     */
    @Test
    public void testFindAll() {
        //1.获取EntityManager对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3.查询全部
//        String jpql = "from cn.cn.sher6j.domain.Customer";
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象
        //发送查询，并封装结果集
        List list = query.getResultList();
        list.forEach(System.out::println);

        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 排序查询，倒序查询全部客户（根据id）
     *     jpql：from Customer order by custId desc
     *     sql：SELECT * FROM cst_customer ORDER BY cust_id DESC
     */
    @Test
    public void testFindByOrder() {
        //1.获取EntityManager对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3.倒叙查询
        String jpql = "from Customer order by custId desc";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象
        //发送查询，并封装结果集
        List list = query.getResultList();
        list.forEach(System.out::println);

        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 查询统计客户总数
     *     jpql：select count(custId) from Customer
     *     sql：SELECT COUNT(cust_id) FROM cst_customer
     */
    @Test
    public void testCount() {
        //1.获取EntityManager对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3.查询总数
        //i.根据jpql语句创建Query查询对象
        String jpql = "select count(custId) from Customer";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象
        //ii.对参数赋值
        //iii.发送查询，并封装结果集
        Object result = query.getSingleResult();
        System.out.println(result);

        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 分页查询
     *     jpql：from Customer
     *     sql：select * from cst_customer limit ?,?      从0开始查第一个参数可以省略
     */
    @Test
    public void testPaged() {
        //1.获取EntityManager对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3.分页查询
        //i.根据jpql语句创建Query查询对象
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象
        //ii.对参数赋值 -- 对分页参数进行赋值
        //起始索引
        query.setFirstResult(0);
        //每页查询条数
        query.setMaxResults(2);
        //iii.发送查询，并封装结果集
        List result = query.getResultList();
        result.forEach(System.out::println);

        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 条件查询
     *     案例：查询客户名称以"J"开头的客户
     *     jpql：from Customer where custName like ?
     *     sql：SELECT * FROM cst_customer WHERE cust_name LIKE ?   --- ?:'J%'
     */
    @Test
    public void testCondition() {
        //1.获取EntityManager对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3.分页查询
        //i.根据jpql语句创建Query查询对象
        String jpql = "from Customer where custName like ?";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象
        //ii.对参数赋值 -- 对查询条件占位符进行赋值
        //第一个参数，占位符索引位置（从1开始），第二个参数，取值
        query.setParameter(1, "J%");
        //iii.发送查询，并封装结果集
        List result = query.getResultList();
        result.forEach(System.out::println);

        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

}
