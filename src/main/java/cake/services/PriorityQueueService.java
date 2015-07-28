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

    public Long rank(Long id, ClassId classId) {
        return 0l; // TODO implement
    }

    public WorkOrderRequest workOrderRequestForIdAndTime(Long id, String time) throws Exception {

        Date date = parse(time);

        ClassId classId = getClassId(id);

        Long rank = rank(id, classId);

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
