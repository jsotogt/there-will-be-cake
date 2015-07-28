package cake.services;

import cake.machinery.PriorityQueue;
import cake.models.WorkOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PriorityQueueService {

    @Autowired
    PriorityQueue priorityQueue;

    /**
     * Parses the given time-string using the slightly modified ISO8601 parse: yyyyMMdd'T'hhmmss.
     * @param dateTimeString the ISO8601 time-string to be parsed
     * @return a Date instance set to the given time-string
     */
    public Date parse(String dateTimeString) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd'T'hhmmss").parse(dateTimeString);
    }

    /**
     * @see PriorityQueue#enqueue(WorkOrderRequest)
     */
    public boolean enqueue(WorkOrderRequest newRequest) {
        return priorityQueue.enqueue(newRequest);
    }

    /**
     * @see PriorityQueueService#dequeue()
     */
    public WorkOrderRequest dequeue() {
        return priorityQueue.dequeue();
    }

    /**
     * @see PriorityQueue#getListOfIds()
     */
    public List<Long> list() {
        return priorityQueue.getListOfIds();
    }

    /**
     * @see PriorityQueue#delete(Long)
     */
    public WorkOrderRequest delete(Long id) {
        return priorityQueue.delete(id);
    }

    /**
     * @see PriorityQueue#position(Long)
     */
    public Integer position(Long id) {
        return priorityQueue.position(id);
    }
}
