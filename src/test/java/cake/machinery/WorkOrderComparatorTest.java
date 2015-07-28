package cake.machinery;

import cake.models.WorkOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for WorkOrderComparator.
 */
public class WorkOrderComparatorTest {

    // class under test
    WorkOrderComparator workOrderComparator = new WorkOrderComparator();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    private Date now() { return new Date(); }

    public long id() {
        return 9223372036854775807l;
    }

    public double rank() {
        return 0;
    }

    private Date yesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    @Test
    public void testCompareOverrideNormal() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE, rank());
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.NORMAL, rank());

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result > 0);
    }

    @Test
    public void testCompareNormalOverride() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), now(), ClassId.NORMAL, rank());
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE, rank());

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result < 0);
    }

    @Test
    public void testCompareOverrideOverride() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), yesterday(), ClassId.OVERRIDE, rank());
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.OVERRIDE, rank());

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result > 0);
    }

    @Test
    public void testCompareNormalNormal() throws Exception {
        // given:
        WorkOrderRequest o1 = new WorkOrderRequest(id(), yesterday(), ClassId.NORMAL, 10.0);
        WorkOrderRequest o2 = new WorkOrderRequest(id(), now(), ClassId.NORMAL, 20.0);

        // when:
        int result = workOrderComparator.compare(o1, o2);

        // then:
        assertTrue( result < 0);
    }
}