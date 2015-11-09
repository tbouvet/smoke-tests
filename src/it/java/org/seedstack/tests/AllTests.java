/*
 * Creation : 26 juin 2015
 */
package org.seedstack.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(JsonWriterSuite.class)
@SuiteClasses({ BasicTests.class, JpaTests.class, JmsTests.class })
public class AllTests {

}
