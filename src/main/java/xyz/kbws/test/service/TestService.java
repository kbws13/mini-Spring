package xyz.kbws.test.service;

import xyz.kbws.annotation.Autowired;
import xyz.kbws.annotation.Service;
import xyz.kbws.test.dao.TestDAO;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description:
 */
@Service
public class TestService {
    @Autowired
    TestDAO testDAO;

    public void echo(){
        System.out.println(testDAO.echo());
    }
}
