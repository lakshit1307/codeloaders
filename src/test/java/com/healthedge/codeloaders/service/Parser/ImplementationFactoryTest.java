package com.healthedge.codeloaders.service.Parser;

import com.healthedge.codeloaders.service.Transformer.Transformer;
import com.healthedge.codeloaders.service.Transformer.ZipCodeTransformer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImplementationFactoryTest {

    @Autowired
    private ImplementationFactory implementationFactory;

    @Test
    public void getParser() throws Exception {
        Transformer transformer = implementationFactory.getTransformer("zip");
        Assert.assertTrue(transformer instanceof ZipCodeTransformer);

        try {
            implementationFactory.getTransformer("somerandomfileType");
            Assert.fail("The class should not have been initialized");
        } catch (Exception ignored) {}
    }
}