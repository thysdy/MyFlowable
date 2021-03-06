package com.frgk.flowable;

import com.frgk.flowable.common.HttpUtil4;
import com.frgk.flowable.common.MyException;
import com.frgk.flowable.dao.ProcessInstanceDao;
import com.frgk.flowable.service.InstanceService;
import liquibase.pro.packaged.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class ZhfrApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    ProcessInstanceDao instanceDao;
    @Autowired
    InstanceService instanceService;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void deleteAllInstance() throws MyException {
        List<String> ids = instanceDao.getAllInstanceIds();
        instanceService.deleteInstanceCompletely(ids);
    }

    @Test
    void testHttp() {
        String result = HttpUtil4.doGet("http://123.56.53.154:8208/ADVERTISEMENTDICTIONARY/getStationNatures");
        System.out.println(result);
    }
}
