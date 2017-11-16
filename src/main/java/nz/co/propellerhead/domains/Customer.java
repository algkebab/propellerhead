package nz.co.propellerhead.domains;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by proper on 16.11.17.
 */
public class Customer {

    private UUID id;
    private CustomerStatus status;
    private Date created;
    private Date modified;
    private String name;
    private String phone;
    private List<CustomerNote> notes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<CustomerNote> getNotes() {
        return notes;
    }

    public void setNotes(List<CustomerNote> notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
