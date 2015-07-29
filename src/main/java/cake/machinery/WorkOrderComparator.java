package cake.machinery;

import cake.models.WorkOrderRequest;

import java.util.Comparator;
import java.util.Date;

/**
 * Comparator for WorkOrderRequest.
 */
public class WorkOrderComparator implements Comparator<WorkOrderRequest> {

    private Date reference; // used to know how long a request has been on the queue

    public WorkOrderComparator(Date reference) {
        this.reference = reference;
    }

    /**
     * Ranks the given ClassId based on the amount of seconds it has been in the queue.
     * The ranking process is done using the following rules:
     * @param classId the ClassId to be ranked
     * @param n the amount of seconds the id with the given class has been in the queue
     * @return a rank that can be used to sort a class id in the queue
     */
    public double rank(ClassId classId, Double n) {

        if(classId == ClassId.PRIORITY) {
            return Math.max(3, n * Math.log10(n)); // max(3, n log n)
        }

        if(classId == ClassId.VIP) {
            return Math.max(4, 2 * n * Math.log10(n)); // max(4, 2n log n)
        }

        return n; // NORMAL & OVERRIDE

    }

    @Override
    public int compare(WorkOrderRequest o1, WorkOrderRequest o2) {

        if(o1.classId() == ClassId.OVERRIDE && o2.classId() != ClassId.OVERRIDE) {
            return 1;
        }

        if(o1.classId() != ClassId.OVERRIDE && o2.classId() == ClassId.OVERRIDE) {
            return -1;
        }

        Double o1Rank = rank(o1.classId(), (reference.getTime() - o1.time().getTime()) / 1000.0);
        Double o2Rank = rank(o2.classId(), (reference.getTime() - o2.time().getTime()) / 1000.0);

        return o1Rank.compareTo(o2Rank);
    }

}
