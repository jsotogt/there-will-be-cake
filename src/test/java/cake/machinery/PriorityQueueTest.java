package cake.machinery;

import cake.models.WorkOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test for PriorityQueue.
 */
public class PriorityQueueTest {

    // class under test
    PriorityQueue priorityQueue;

    // list of mocks
    WorkOrderRequest mockRquest;

    @Before
    public void setUp() throws Exception {
        mockRquest = mock(WorkOrderRequest.class);
        priorityQueue = new PriorityQueue();
    }

    @After
    public void tearDown() throws Exception {

    }

    public long id() {
        return 1234l;
    }

    public Date today() {
        return new Date();
    }

    public Date tomorrow() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    @Test
    public void testDequeue() throws Exception {
        // given:
        priorityQueue.enqueue(mockRquest);

        // when:
        WorkOrderRequest dequeuedRequest = priorityQueue.dequeue();

        // then:
        assertEquals(mockRquest, dequeuedRequest);
    }

    @Test
    public void testEnqueue() throws Exception {
        // given:
        WorkOrderRequest w = mock(WorkOrderRequest.class);

        // when:
        boolean result = priorityQueue.enqueue(w);

        // then:
        assertTrue(result);
    }

    @Test
    public void testQueueIsEmptyOnStart() throws  Exception {
        // given:
        PriorityQueue priorityQueue = new PriorityQueue();

        // when:
        WorkOrderRequest w = priorityQueue.dequeue();

        // then:
        assertNull(w);
    }

    @Test
    public void testQueueOnlyAcceptsOneOrderAtATimeForTheSameUser() throws Exception {
        // given:
        WorkOrderRequest firstRequest = mock(WorkOrderRequest.class);
        when(firstRequest.id()).thenReturn(id());
        when(firstRequest.date()).thenReturn(today());

        WorkOrderRequest secondRequest = mock(WorkOrderRequest.class);
        when(secondRequest.id()).thenReturn(id());
        when(secondRequest.date()).thenReturn(tomorrow());

        priorityQueue.enqueue(firstRequest);

        // when:
        boolean result = priorityQueue.enqueue(secondRequest);

        // then:
        assertFalse(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQueueDoesNotAcceptNulls() {
        // given:
        WorkOrderRequest request = null;

        // when:
        priorityQueue.enqueue(null);
    }

}