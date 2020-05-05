package cn.sher6j.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sher6j
 * @create 2020-05-05-11:08
 */
@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "age")
    private Integer age;

    /**
     * 配置用户到角色的多对多关系
     *     配置多对多的映射关系
     *      1.声明表关系的配置
     *          注解@ManyToMany
     *      2.配置中间表（包含两个外键）
     *          注解@JoinTable
     */
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "sys_user_role", //中间表的名称
            //joinColumns：当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "sys_user_id", referencedColumnName = "user_id")},
            //inverseJoinColumns：对方对象在中间表中的外键
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
