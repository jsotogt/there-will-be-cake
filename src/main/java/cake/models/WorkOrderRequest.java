package cake.models;

import java.util.Date;

/**
 * Represents a work order for Tim to process.
 */
public class WorkOrderRequest {

    private Long id;
    private Date time;

    public WorkOrderRequest(Long id, Date time) {
        this.id = id;
        this.time = time;
    }

    public Long id() {
        return id;
    }

    public Date time() {
        return time;
    }
}
