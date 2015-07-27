package cake.services;

import cake.machinery.PriorityQueue;
import cake.models.WorkOrderRequest;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test for PriorityQueueService.
 */
public class PriorityQueueServiceTest {

    // class under test
    PriorityQueueService priorityQueueService;

    // lis of mocks
    PriorityQueue priorityQueue;

    @Before
    public void setUp() throws Exception {
        priorityQueueService = new PriorityQueueService();
        priorityQueue =  mock(PriorityQueue.class);
        priorityQueueService.priorityQueue = priorityQueue;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParse() throws Exception {
        // given:
        String dateString = "20150820T080000";
        Date expectedDate = new DateTime(2015,8,20,8,0,0).toDate();

        // when:
        Date parsedDate = priorityQueueService.parse(dateString);

        // then:
        assertEquals(expectedDate, parsedDate);
    }

    @Test(expected = ParseException.class)
    public void testParseWithException() throws Exception {
        // given:
        String dateString = "2015-08-20 08:00:00";

        // when:
        priorityQueueService.parse(dateString);
    }

    @Test
    public void testEnqueue() throws Exception {
        // given:
        WorkOrderRequest workRequest = mock(WorkOrderRequest.class);

        // when:
        priorityQueue.enqueue(workRequest);

        // then:
        verify(priorityQueue).enqueue(workRequest);
    }

    @Test
    public void testDequeue() throws Exception {
        // when:
        priorityQueue.dequeue();

        // then:
        verify(priorityQueue).dequeue();
    }
}