package cake.machinery;

import cake.models.WorkOrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
        priorityQueue.enqueue(mockRquest);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDequeue() throws Exception {
        // given:
        WorkOrderRequest enqueuedRequest = mockRquest;

        // when:
        WorkOrderRequest dequeuedRequest = priorityQueue.dequeue();

        // then:
        assertEquals(enqueuedRequest, dequeuedRequest);
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
}