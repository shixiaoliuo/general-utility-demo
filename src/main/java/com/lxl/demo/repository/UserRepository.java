package com.lxl.demo.repository;

import com.lxl.demo.domain.User;
import com.lxl.utility.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import jooq.tables.daos.UserDao;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static jooq.Tables.USER;
import static org.jooq.impl.DSL.*;

/**
 * @program: General-Utility-Demo
 * @description: UserRepository
 * @author: LiuXL
 * @create: 2024-09-11 16:57
 **/
@Repository
@RequiredArgsConstructor
public class UserRepository extends UserDao {

    private final DSLContext dsl;

    /**
     * @param user
     * @return
     */
    public List<jooq.tables.pojos.User> list(User user) {
        return dsl
                .select(asterisk())
                .from(USER)
                .where(
                        StringUtils.isNoneBlank(user.getPassword()) ?
                                USER.PASSWORD.eq(user.getPassword()) : noCondition(),
                        StringUtils.isNoneBlank(user.getUsername()) ?
                                USER.PASSWORD.eq(user.getUsername()) : noCondition(),
                        Objects.nonNull(user.getAmount()) ?
                                USER.AMOUNT.eq(user.getAmount()) : noCondition()
                )
                .fetchInto(jooq.tables.pojos.User.class);
    }
}
