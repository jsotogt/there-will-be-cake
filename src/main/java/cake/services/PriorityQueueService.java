package cake.services;

import cake.machinery.PriorityQueue;
import cake.models.WorkOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PriorityQueueService {

    @Autowired
    PriorityQueue priorityQueue;

    /**
     * Parses the given date-string using the slightly modified ISO8601 parse: yyyyMMdd'T'hhmmss.
     * @param dateString the ISO8601 date-string to be parsed
     * @return a Date instance set to the given date-string
     */
    public Date parse(String dateString) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd'T'hhmmss").parse(dateString);
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
}
