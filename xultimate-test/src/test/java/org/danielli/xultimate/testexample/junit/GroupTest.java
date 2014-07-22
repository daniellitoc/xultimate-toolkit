package org.danielli.xultimate.testexample.junit;

import org.danielli.xultimate.testexample.junit.support.ConstructorTest;
import org.danielli.xultimate.testexample.junit.support.ExampleTest;
import org.danielli.xultimate.testexample.junit.support.ExceptionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ExampleTest.class, ExceptionTest.class, ConstructorTest.class })
public class GroupTest {

}
