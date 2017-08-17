
/*
 * The JTS Topology Suite is a collection of Java classes that
 * implement the fundamental operations required to validate a given
 * geo-spatial data set to a known topological specification.
 *
 * Copyright (C) 2001 Vivid Solutions
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, contact:
 *
 *     Vivid Solutions
 *     Suite #1A
 *     2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www.vividsolutions.com
 */

package test.jts.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import jts.algorithm.RobustLineIntersectionTest;
import jts.geom.AreaLengthTest;
import jts.geom.BidirectionalComparatorTest;
import jts.geom.CoordinateArraysTest;
import jts.geom.EnvelopeTest;
import jts.geom.GeometryCollectionImplTest;
import jts.geom.GeometryImplTest;
import jts.geom.IntersectionMatrixTest;
import jts.geom.IsRectangleTest;
import jts.geom.LineStringImplTest;
import jts.geom.MultiPointImplTest;
import jts.geom.NormalizeTest;
import jts.geom.PointImplTest;
import jts.geom.PrecisionModelTest;
import jts.geom.PredicateShortCircuitTest;
import jts.geom.RectanglePredicateSyntheticTest;
import jts.geom.RectanglePredicateTest;
import jts.geom.impl.BasicCoordinateSequenceTest;
import jts.index.IntervalTest;
import jts.index.QuadtreeTest;
import jts.index.SIRtreeTest;
import jts.index.STRtreeTest;
import jts.io.WKBTest;
import jts.io.WKTReaderTest;
import jts.io.WKTWriterTest;
import jts.linearref.LengthIndexedLineTest;
import jts.linearref.LocationIndexedLineTest;
import jts.operation.buffer.BufferTest;
import jts.operation.distance.DistanceTest;
import jts.operation.linemerge.LineMergerTest;
import jts.operation.polygonize.PolygonizeTest;
import jts.operation.relate.RelateBoundaryNodeRuleTest;
import jts.operation.union.CascadedPolygonUnionTest;
import jts.operation.union.UnaryUnionTest;
import jts.operation.valid.IsValidTest;
import jts.operation.valid.ValidClosedRingTest;
import jts.operation.valid.ValidSelfTouchingRingFormingHoleTest;
import jts.precision.SimpleGeometryPrecisionReducerTest;
import jts.triangulate.ConformingDelaunayTest;
import jts.triangulate.DelaunayTest;
/**
 * A collection of all the tests.
 *
 * @version 1.7
 */
public class MasterTester extends TestCase {

  public MasterTester(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite result = new TestSuite();
    result.addTest(new TestSuite(jts.algorithm.AngleTest.class));
    result.addTest(new TestSuite(AreaLengthTest.class));
    result.addTest(new TestSuite(BasicCoordinateSequenceTest.class));
    result.addTest(new TestSuite(BidirectionalComparatorTest.class));
    result.addTest(new TestSuite(BufferTest.class));
    result.addTest(new TestSuite(CascadedPolygonUnionTest.class));
    result.addTest(new TestSuite(jts.algorithm.OrientationIndexTest.class));
    result.addTest(new TestSuite(ConformingDelaunayTest.class));
    result.addTest(new TestSuite(jts.algorithm.ConvexHullTest.class));
    result.addTest(new TestSuite(CoordinateArraysTest.class));
    result.addTest(new TestSuite(DelaunayTest.class));
    result.addTest(new TestSuite(DistanceTest.class));
    result.addTest(new TestSuite(EnvelopeTest.class));
    result.addTest(new TestSuite(GeometryCollectionImplTest.class));
    result.addTest(new TestSuite(GeometryImplTest.class));
    result.addTest(new TestSuite(IntersectionMatrixTest.class));
    result.addTest(new TestSuite(IntervalTest.class));
    result.addTest(new TestSuite(jts.algorithm.IsCCWTest.class));
    result.addTest(new TestSuite(IsRectangleTest.class));
    result.addTest(new TestSuite(IsValidTest.class));
    result.addTest(new TestSuite(LengthIndexedLineTest.class));
    result.addTest(new TestSuite(LineMergerTest.class));
    result.addTest(new TestSuite(LineStringImplTest.class));
    result.addTest(new TestSuite(LocationIndexedLineTest.class));
    result.addTest(new TestSuite(MiscellaneousTest.class));
    result.addTest(new TestSuite(MiscellaneousTest2.class));
    result.addTest(new TestSuite(MultiPointImplTest.class));
    result.addTest(new TestSuite(jts.algorithm.NonRobustLineIntersectorTest.class));
    result.addTest(new TestSuite(NormalizeTest.class));
    result.addTest(new TestSuite(PointImplTest.class));
    result.addTest(new TestSuite(PolygonizeTest.class));
    result.addTest(new TestSuite(PredicateShortCircuitTest.class));
    result.addTest(new TestSuite(PrecisionModelTest.class));
    result.addTest(new TestSuite(QuadtreeTest.class));
    result.addTest(new TestSuite(RectanglePredicateSyntheticTest.class));
    result.addTest(new TestSuite(RectanglePredicateTest.class));
    result.addTest(new TestSuite(RelateBoundaryNodeRuleTest.class));
    result.addTest(new TestSuite(RobustLineIntersectionTest.class));
    result.addTest(new TestSuite(SimpleGeometryPrecisionReducerTest.class));
    result.addTest(new TestSuite(SimpleTest.class));
    result.addTest(new TestSuite(SIRtreeTest.class));
    result.addTest(new TestSuite(STRtreeTest.class));
    result.addTest(new TestSuite(WKTReaderTest.class));
    result.addTest(new TestSuite(WKTWriterTest.class));
    result.addTest(new TestSuite(WKBTest.class));
    result.addTest(new TestSuite(UnaryUnionTest.class));
    result.addTest(new TestSuite(ValidClosedRingTest.class));
    result.addTest(new TestSuite(ValidSelfTouchingRingFormingHoleTest.class));
    //result.addTest(new TestSuite(VoronoiTest.class));
    
    return result;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
    System.exit(0);
  }

}
