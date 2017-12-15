package com.healthedge.codeloaders.service.Parser;

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
        Parser zipParser = implementationFactory.getParser("zip");
        Assert.assertTrue(zipParser instanceof ZipParser);
        Parser zipToCarrierLocality = implementationFactory.getParser("zipToCarrierLocality");
        Assert.assertTrue(zipToCarrierLocality instanceof ZipToCarrierLocalityParser);

        try {
            implementationFactory.getParser("somerandomfileType");
            Assert.fail("The class should not have been initialized");
        } catch (Exception ignored) {}
    }
}