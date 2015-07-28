package cake.machinery;

import cake.models.WorkOrderRequest;

import java.util.Comparator;

/**
 * Comparator for WorkOrderRequest.
 */
public class WorkOrderComparator implements Comparator<WorkOrderRequest> {

    @Override
    public int compare(WorkOrderRequest o1, WorkOrderRequest o2) {

        if(o1.classId() == ClassId.OVERRIDE && o2.classId() != ClassId.OVERRIDE) {
            return 1;
        }

        if(o1.classId() != ClassId.OVERRIDE && o2.classId() == ClassId.OVERRIDE) {
            return -1;
        }

        if(o1.classId() == ClassId.OVERRIDE && o2.classId() == ClassId.OVERRIDE) {
            return o2.time().compareTo(o1.time());
        }

        return o1.rank().compareTo(o2.rank());
    }

}
