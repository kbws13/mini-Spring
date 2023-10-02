package xyz.kbws.test.service;

import xyz.kbws.annotation.beans.Autowired;
import xyz.kbws.annotation.core.Service;
import xyz.kbws.test.dao.TestDAO;

/**
 * @Author kbws
 * @Date 2023/9/29
 * @Description:
 */
@Service
public class TestService implements IService {
    @Autowired
    TestDAO testDAO;

    @Override
    public void echo(){
        System.out.println(testDAO.echo());
    }
}
