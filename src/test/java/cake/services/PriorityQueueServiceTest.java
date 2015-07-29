package cake.services;

import cake.machinery.ClassId;
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
        priorityQueueService.timeformat = "yyyyMMdd'T'hhmmss'Z'";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParse() throws Exception {
        // given:
        String dateString = "20150820T080000Z";
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
    public void testGetClassVIP() throws Exception {
        // given:
        Long id = 10l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.VIP, classId);
    }

    @Test
    public void testGetClassPriority() throws Exception {
        // given:
        Long id = 9l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.PRIORITY, classId);
    }

    @Test
    public void testGetClassOverrride() throws Exception {
        // given:
        Long id = 15l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.OVERRIDE, classId);
    }

    @Test
    public void testGetClassNormal() throws Exception {
        // given:
        Long id = 7l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.NORMAL, classId);
    }

    @Test
    public void testGetClassOne() throws Exception {
        // given:
        Long id = 1l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.NORMAL, classId);
    }

    @Test
    public void testGetClassMax() throws Exception {
        // given:
        Long id = 9223372036854775807l;

        // when:
        ClassId classId = priorityQueueService.getClassId(id);

        // then:
        assertEquals(ClassId.NORMAL, classId);
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

    @Test
    public void testList() throws Exception {
        // when:
        priorityQueueService.list();

        // then:
        verify(priorityQueue).getListOfIds();
    }

    @Test
    public void testDelete() throws Exception {
        // given:
        Long id = 1234l;

        // when:
        priorityQueueService.delete(id);

        // then:
        verify(priorityQueue).delete(id);
    }

    @Test
    public void testPosition() throws Exception {
        // given:
        Long id = 1234l;

        // when:
        priorityQueueService.position(id);

        // then:
        verify(priorityQueue).position(id);
    }

    @Test
    public void testRankNormal() {
        // given:
        ClassId classId = ClassId.NORMAL;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(10.0 == rank);
    }

    @Test
    public void testRankOverride() {
        // given:
        ClassId classId = ClassId.OVERRIDE;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(10.0 == rank);
    }

    @Test
    public void testRankPriorityMin() {
        // given:
        ClassId classId = ClassId.PRIORITY;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(1000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(3.0 == rank);
    }


    @Test
    public void testRankPriorityLog() {
        // given:
        ClassId classId = ClassId.PRIORITY;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(10.0 == rank);
    }

    @Test
    public void testRankVIPMin() {
        // given:
        ClassId classId = ClassId.VIP;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(1000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(4.0 == rank);
    }

    @Test
    public void testRankVIPLog() {
        // given:
        ClassId classId = ClassId.VIP;
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(10000l);

        // when:
        double rank = priorityQueueService.getRank(classId, date);

        // then:
        assertTrue(20.0 == rank);
    }

}