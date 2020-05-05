package cn.sher6j.dao;

import cn.sher6j.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author sher6j
 * @create 2020-05-05-11:12
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
