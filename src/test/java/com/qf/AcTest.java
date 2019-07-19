package com.qf;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-15 19:39
 **/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContext-dao.xml",
        "classpath:applicationContext-service.xml"
})
public class AcTest {


}
