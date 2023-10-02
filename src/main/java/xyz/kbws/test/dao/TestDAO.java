package xyz.kbws.test.dao;

import xyz.kbws.annotation.core.Repository;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description:
 */
@Repository
public class TestDAO {
    public String echo(){
        return "This is TestDAO!";
    }
}
