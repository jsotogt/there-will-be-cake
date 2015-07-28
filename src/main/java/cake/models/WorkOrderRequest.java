package cake.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Date time() {
        return time;
    }
}
