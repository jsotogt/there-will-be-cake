package cake.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test for WorkOrderRequest.
 */
public class WorkOrderRequestTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    private Date now() {
        return new Date();
    }

    public long id() {
        return 9223372036854775807l;
    }

    @Test
    public void testGetId() throws Exception {
        // given:
        Long id = id();
        Date date = now();

        // when:
        WorkOrderRequest w = new WorkOrderRequest(id, date);


        // then:
        assertEquals(id, w.getId());
    }

    @Test
    public void testGetDate() throws Exception {
        // given:
        Long id = id();
        Date date = now();

        // when:
        WorkOrderRequest w = new WorkOrderRequest(id, date);


        // then:
        assertEquals(date, w.getDate());

    }
}