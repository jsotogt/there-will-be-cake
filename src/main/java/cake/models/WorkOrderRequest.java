package cake.models;

import cake.machinery.ClassId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Represents a work order for Tim to process.
 */
public class WorkOrderRequest {

    private Long id;
    private Date time;
    private ClassId classId;

    public WorkOrderRequest(Long id, Date time, ClassId classId) {
        this.id = id;
        this.time = time;
        this.classId = classId;
    }

    @JsonProperty
    public Long id() {
        return id;
    }

    @JsonProperty
    public Date time() {
        return time;
    }

    @JsonIgnore
    public ClassId classId() {
        return classId;
    }
}
