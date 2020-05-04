package cn.sher6j.dao;

import cn.sher6j.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 符合SpringDataJpa的dao层接口规范，需要继承两个接口
 *     JpaRepository<操作的实体类泛型，实体类中主键属性的类型>
 *         该接口封装了基本的CRUD操作
 *     JpaSpecificationExecutor<操作的实体类类型>
 *         该接口封装了复杂查询，如分页等
 * @author cn.sher6j
 * @create 2020-05-04-10:59
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 根据客户名称查询客户，使用jpql形式查询
     *      jpql：from Customer where custName = ?
     *      配置jpql语句，使用注解@Query
     * @param custName
     * @return
     */
    @Query(value = "from Customer where custName = ?")
    public Customer findByName(String custName);

    /**
     * 根据客户名称和Id进行查询
     *      jpql：from Customer where custName = ? and custId = ?
     *      对于多个占位符，赋值时候，默认情况下，占位符位置需要和方法参数位置保持一致
     *
     *      也可以指定占位符参数的位置
     *          ? 索引的方式，指定此占位符的取值来源
     * @param name
     * @param id
     * @return
     */
    @Query(value = "from Customer where custName = ?2 and custId = ?1")
//    public Customer findByNameAndId(String name, Long id);
    public Customer findByNameAndId(Long id, String name);

    /**
     * 根据id更新客户的名称
     *      sql: update cst_customer set cust_name = ? where cust_id = ?
     *      jpql：update Customer set custName = ? where custId = ?
     * 注解@Query：代表的是进行查询
     *      声明此方法时用来更新操作需要加另一个注解@Modifying
     * @param id
     * @param name
     */
    @Query(value = "update Customer set custName = ?2 where custId = ?1")
    @Modifying
    public void updateCustomer(Long id, String name);

    /**
     * 使用SQL形式查询全部客户
     *      sql：select * from cst_customer;
     * 注解@Query：配置SQL查询
     *      value：SQL语句
     *      nativeQuery：查询方式
     *          true：SQL查询
     *          false：spql查询
     * @return
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Object[]> findAllUseSql();

    /**
     * 条件查询
     * @param name
     * @return
     */
    @Query(value = "select * from cst_customer where cust_name like ?1", nativeQuery = true)
    public List<Object[]> findAllUseSqlByCondition(String name);

    /**
     * 方法命名规则的查询
     * 方法名的约定：
     *     findBy开头代表查询
     *          对象中的属性名（首字母大写）：查询的条件
     *     findByCustName -- 根据客户名称查询
     *     在SpringDataJpa的运行阶段，会根据方法名称进行解析，翻译成
     *          from xxx(实体类) where custName =
     *
     *     1.findBy + 属性名称（根据属性名称进行完成匹配的查询=）
     *     2.findBy + 属性名称 + "查询方式（Like/isnull）"
     *     3.findBy + 属性名称 + "查询方式" + "多条件查询的连接符（and/or）" + 属性名称 + "其他查询方式"
     *          精准匹配查询方式可以不写
     */
    public Customer findByCustName(String name);

    /**
     * 模糊查询
     * @param name
     * @return
     */
    public List<Customer> findByCustNameLike(String name);

    /**
     * 使用客户名称模糊查询和客户所属行业精准匹配的查询，形参的参数顺序不能搞反了，必须和函数名顺序一致
     * @return
     */
    public List<Customer> findByCustNameLikeAndCustIndustry(String name, String industry);
}
