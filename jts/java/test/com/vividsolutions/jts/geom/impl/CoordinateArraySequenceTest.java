package jts.geom.impl;

import jts.geom.CoordinateSequenceFactory;

import junit.textui.TestRunner;

/**
 * Test {@link CoordinateArraySequence}
 *
 * @version 1.7
 */
public class CoordinateArraySequenceTest
    extends CoordinateSequenceTestBase
{
  public static void main(String args[]) {
    TestRunner.run(CoordinateArraySequenceTest.class);
  }

  public CoordinateArraySequenceTest(String name)
  {
    super(name);
  }

  @Override
  CoordinateSequenceFactory getCSFactory() {
    return CoordinateArraySequenceFactory.instance();
  }
}