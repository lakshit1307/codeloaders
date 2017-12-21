package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.dao.BaseDao;
import com.healthedge.codeloaders.dao.ServiceDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImplementationFactoryTest {

    @Autowired
    private ImplementationFactory implementationFactory;

    @Test
    public void getParser() throws Exception {
        BaseDao baseDao = implementationFactory.getDao("cpt");
        Assert.assertTrue(baseDao instanceof ServiceDao);

        try {
            implementationFactory.getTransformer("somerandomfileType");
            Assert.fail("The class should not have been initialized");
        } catch (Exception ignored) {}
    }
}