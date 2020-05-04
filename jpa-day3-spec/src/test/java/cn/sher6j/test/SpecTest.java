package cn.sher6j.test;

import cn.sher6j.dao.CustomerDao;
import cn.sher6j.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author sher6j
 * @create 2020-05-04-18:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据条件查询单个对象
     */
    @Test
    public void testSpec1() {
        //匿名内部类写法
        /**
         * 自定义查询条件
         *     1.实现Specification接口（提供泛型，查询的对象类型）
         *     2.实现toPredicate方法（构造查询条件）
         *     3.需要借助方法参数中的两个参数
         *          Root：获取需要查询对象的参数
         *          CriteriaBuilder：构造查询条件，内部封装了很多的查询条件（模糊匹配，精准匹配）
         * 根据客户名称查询
         *     查询条件
         *      1.查询方式
         *          cb对象
         *      2.比较的属性名称
         *          root对象
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1.获取比较的属性
                Path<Object> custName = root.get("custName");
                //2.构造查询条件
                /**
                 * 第一个参数：需要比较的属性（Path对象）
                 * 第二个参数：需要比较的属性的取值
                 */
                Predicate predicate = cb.equal(custName, "James");//精准匹配(比较的属性名，比较的属性名的取值)
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 多条件查询
     * 根据客户名和客户所属行业查询
     */
    @Test
    public void testSpec2() {
        /**
         * root:获取属性
         *      客户名
         *      所属行业
         * cb:构造查询
         *      1.构造客户名的精准匹配查询
         *      2.构造所属行业的精准匹配查询
         *      3.联合上面两个条件
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");
                Predicate p1 = cb.equal(custName, "James");
                Predicate p2 = cb.equal(custIndustry, "运动员");
                //将多个查询条件组合到一起
                Predicate predicate = cb.and(p1, p2);
                return predicate;
            }
        };

        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    /**
     * 根据客户名称模糊查询，返回客户列表
     *
     *     对于equal：可以用直接得到的Path对象进行比较即可
     *     对于gt, lt, ge, le, like等：得到Path对象，根据Path需要指定比较的参数类型，再去进行比较
     *         指定参数类型：path.as(类型的字节码对象)
     */
    @Test
    public void testSpec3() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = cb.like(custName.as(String.class), "J%");
                return predicate;
            }
        };
        //添加排序
        /*
        创建排序对象，需要调用构造方法实例化sort对象
            第一个参数：排序的顺序（顺序，倒序）
            第二个参数：排序的属性名称
         */
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> customers = customerDao.findAll(spec, sort);
        customers.forEach(System.out::println);
    }

    /**
     * 分页查询
     *      findAll(Specification, Pageable)
     *      Specification:查询条件
     *      Pageable:分页参数
     *    返回Page对象（SpringDataJpa为我们封装好的pageBean对象，包含数据列表，总条数等信息）
     */
    @Test
    public void testSpec4() {
        Specification spec = null;
        /*
        PageRequest是Pageable的实现类，
        创建PageRequest的过程中，需要调用他的构造方法传入两个参数
            第一个参数：当前查询的页数（从0开始）
            第二个参数：每页查询的数量
         */
        Pageable pageable = new PageRequest(0,2);
        Page<Customer> page = customerDao.findAll(null, pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        List<Customer> customers = page.getContent();
        customers.forEach(System.out::println);
    }

}
