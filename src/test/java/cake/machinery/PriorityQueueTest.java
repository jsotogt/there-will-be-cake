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

    public ClassId classId() {
        return ClassId.NORMAL;
    }

    private double rank() {
        return 0l;
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
        when(firstRequest.time()).thenReturn(today());

        WorkOrderRequest secondRequest = mock(WorkOrderRequest.class);
        when(secondRequest.id()).thenReturn(id());
        when(secondRequest.time()).thenReturn(tomorrow());

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

    @Test
    public void testDelete() {
        // given:
        WorkOrderRequest workRequest = new WorkOrderRequest(id(), today(), classId(), rank());
        priorityQueue.enqueue(workRequest);

        // when:
        WorkOrderRequest deletedRequest = priorityQueue.delete(id());

        // then:
        assertEquals(workRequest, deletedRequest);
    }

    @Test
    public void testDeleteNotFound() {
        // when:
        WorkOrderRequest deletedRequest = priorityQueue.delete(id());

        // then:
        assertNull(deletedRequest);
    }

    @Test
    public void testPosition() {
        // given:
        WorkOrderRequest one = new WorkOrderRequest(1234l, today(), classId(), rank());
        WorkOrderRequest two = new WorkOrderRequest(1235l, today(), classId(), rank());

        priorityQueue.enqueue(one);
        priorityQueue.enqueue(two);

        // when:
        int position = priorityQueue.position(two.id());

        // then:
        assertEquals(1, position);
    }

    @Test
    public void testPositionNotFound() {
        // when:
        Integer position = priorityQueue.position(id());

        // then:
        assertNull(position);
    }

    @Test
    public void testRank() {
        fail();
    }

}