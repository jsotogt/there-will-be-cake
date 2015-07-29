package cake.services;

import cake.machinery.ClassId;
import cake.machinery.PriorityQueue;
import cake.models.WorkOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PriorityQueueService {

    @Autowired
    PriorityQueue priorityQueue;

    @Value("${time.format}")
    String timeformat;

    /**
     * Parses the given time-string using ISO8601: yyyyMMddThhmmssZ.
     * @param dateTimeString the ISO8601 time-string to be parsed
     * @return a Date instance set to the given time-string
     */
    public Date parse(String dateTimeString) throws ParseException {
        return new SimpleDateFormat(timeformat).parse(dateTimeString);
    }

    /**
     * Determines the class for the given ID based on the following rules:
     *     (1) IDs that are evenly divisible by 3 are priority IDs.
     *     (2) IDs that are evenly divisible by 5 are VIP IDs.
     *     (3) IDs that are evenly divisible by both 3 and 5 are management override.
     *     (4) IDs that are not evenly divisible by 3 or 5 are normal IDs.
     * @param id the id to be examined
     * @return the class for the given id
     */
    public ClassId getClassId(long id) {

        if(id % 3 == 0 && id % 5 == 0) {
            return ClassId.OVERRIDE;
        }

        if(id % 3 == 0) {
            return ClassId.PRIORITY;
        }

        if(id % 5 == 0) {
            return ClassId.VIP;
        }

        return ClassId.NORMAL;

    }

    /**
     * Ranks a ClassId according to the time it has been in the queue.
     * @param classId
     * @param date
     * @return
     */
    public double getRank(ClassId classId, Date date) {

        double n = date.getTime() / 1000; // in seconds

        if(classId == ClassId.PRIORITY) {
            return Math.max(3, n * Math.log10(n)); // max(3, n log n)
        }

        if(classId == ClassId.VIP) {
            return Math.max(4, 2 * n * Math.log10(n)); // max(4, 2n log n)
        }

        return n; // NORMAL & OVERRIDE

    }

    public WorkOrderRequest workOrderRequestForIdAndTime(Long id, String time) throws Exception {

        Date date = parse(time);

        ClassId classId = getClassId(id);

        double rank = getRank(classId, date);

        return new WorkOrderRequest(id, date, classId, rank);
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
