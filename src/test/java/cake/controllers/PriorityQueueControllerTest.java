package cake.controllers;

import cake.models.WorkOrderRequest;
import cake.services.PriorityQueueService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jsoto on 7/29/15.
 */
public class PriorityQueueControllerTest {

    //  class under test
    PriorityQueueController priorityQueueController;

    // list of mocks
    PriorityQueueService priorityQueueService;

    @Before
    public void setUp() throws Exception {
        priorityQueueController = new PriorityQueueController();
        priorityQueueService = mock(PriorityQueueService.class);
        priorityQueueController.priorityQueueService = priorityQueueService;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEnqueue() throws Exception {
        // given:
        Long id = 9223372036854775807l;
        String time = "20100820T080000Z";

        WorkOrderRequest workOrderRequest = mock(WorkOrderRequest.class);

        when(priorityQueueService.workOrderRequestForIdAndTime(id, time)).thenReturn(workOrderRequest);

        when(priorityQueueService.enqueue(workOrderRequest)).thenReturn(true);

        // when:
        ResponseEntity responseEntity = priorityQueueController.enqueue(id, time);

        // then:
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDequeue() throws Exception {
        // given:
        WorkOrderRequest workOrderRequest = mock(WorkOrderRequest.class);

        when(priorityQueueService.dequeue()).thenReturn(workOrderRequest);

        // when:
        ResponseEntity responseEntity = priorityQueueController.dequeue();

        // then:
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testList() throws Exception {
        // when:
        ResponseEntity responseEntity = priorityQueueController.list();

        // then:
        verify(priorityQueueService).list();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRemove() throws Exception {
        // given:
        Long id = 9223372036854775807l;

        WorkOrderRequest workOrderRequest = mock(WorkOrderRequest.class);

        when(priorityQueueService.delete(id)).thenReturn(workOrderRequest);

        // when:
        ResponseEntity responseEntity = priorityQueueController.remove(id);

        // then:
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void testPosition() throws Exception {
        // given:
        Long id = 9223372036854775807l;

        when(priorityQueueService.position(id)).thenReturn(0);

        // when:
        ResponseEntity<Integer> responseEntity = priorityQueueController.position(id);

        // then:
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new Integer(0), responseEntity.getBody());
    }

    @Test
    public void testAverage() throws Exception {
        // given:
        String time = "20100820T080000Z";
        Date date = mock(Date.class);

        when(priorityQueueService.parse(time)).thenReturn(date);

        when(priorityQueueService.average(date)).thenReturn(20.0);

        // when:
        ResponseEntity responseEntity = priorityQueueController.average(time);

        // then:
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(20.0, responseEntity.getBody());
    }
}