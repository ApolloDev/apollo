package edu.pitt.apollo.apollotranslator;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestFred.class, TestFlute.class, TestEbola.class, TestSeir.class, TestAnthrax.class, TestClara.class, TestOpenMalaria.class })
public class GlobalTestSuite extends TestSuite {
	
}
