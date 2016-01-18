package jp.ac.hiroshima_u.sci.math.saito.tinymt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PolynomialTest.class, 
        TinyMT32ParameterTest.class, TinyMT32Test.class,
        })
public class PartialTests {

}
