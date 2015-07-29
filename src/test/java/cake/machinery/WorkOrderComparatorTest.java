package cake.machinery;

import cake.models.WorkOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for WorkOrderComparator.
 */
public class WorkOrderComparatorTest {

    // class under test
    WorkOrderComparator workOrderComparator;

    @Before
    public void setUp() throws Exception {
        workOrderComparator = new WorkOrderComparator(now());
    }

    @After
    public void tearDown() throws Exception {

    }

    public Date now() { return new Date(); }

    public long id() {
        return 9223372036854775807l;
    }

    public double rank() {
        return 0;
    }

    public Date yesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    @Test
    public void testCompareOverrideNormal() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE);
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.NORMAL);

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result > 0);
    }

    @Test
    public void testCompareNormalOverride() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), now(), ClassId.NORMAL);
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE);

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result < 0);
    }

    @Test
    public void testCompareOverrideOverride() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), yesterday(), ClassId.OVERRIDE);
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE);

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result > 0);
    }

    @Test
    public void testCompareNormalNormal() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), yesterday(), ClassId.NORMAL);
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.NORMAL);

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result > 0);
    }

    @Test
    public void testRankNormal() {
        // given:
        ClassId classId = ClassId.NORMAL;
        Date date = mock(Date.class);
        Double n = 10.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertTrue(10.0 == rank);
    }

    @Test
    public void testRankOverride() {
        // given:
        ClassId classId = ClassId.OVERRIDE;
        Date date = mock(Date.class);
        Double n = 10.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertTrue(10.0 == rank);
    }

    @Test
    public void testRankPriorityMin() {
        // given:
        ClassId classId = ClassId.PRIORITY;
        Date date = mock(Date.class);
        Double n = 1.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertTrue(3.0 == rank);
    }

    @Test
    public void testRankPriorityLog() {
        // given:
        ClassId classId = ClassId.PRIORITY;
        Date date = mock(Date.class);
        Double n = 10.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertEquals(23, rank, 0.1);
    }

    @Test
    public void testRankVIPMin() {
        // given:
        ClassId classId = ClassId.VIP;
        Date date = mock(Date.class);
        Double n = 1.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertTrue(4.0 == rank);
    }

    @Test
    public void testRankVIPLog() {
        // given:
        ClassId classId = ClassId.VIP;
        Date date = mock(Date.class);
        Double n = 10.0;

        // when:
        double rank = workOrderComparator.rank(classId, n);

        // then:
        assertEquals(46, rank, 0.1);
    }
}