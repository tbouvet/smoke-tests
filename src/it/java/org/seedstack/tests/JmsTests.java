/*
 * Creation : 29 juin 2015
 */
package org.seedstack.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.seedstack.tests.jms.SendReceiveIT;

@RunWith(Suite.class)
@SuiteClasses({ SendReceiveIT.class })
public class JmsTests {

}
