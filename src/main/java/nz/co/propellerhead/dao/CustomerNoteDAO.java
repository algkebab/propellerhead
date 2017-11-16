package nz.co.propellerhead.dao;

import nz.co.propellerhead.domains.CustomerNote;
import nz.co.propellerhead.mappers.CustomerNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by proper on 16.11.17.
 */
@Repository
public class CustomerNoteDAO {

    private JdbcTemplate template;
    private DateFormat dateFormat;

    @Autowired
    public CustomerNoteDAO(DataSource dataSource, DateFormat dateFormat) {
        this.template = new JdbcTemplate(dataSource);
        this.dateFormat = dateFormat;
    }

    public List<CustomerNote> getByCustomerId(String id) {
        return template.query("SELECT * FROM customer_notes WHERE id_customer=?", new Object[] {id}, new CustomerNoteMapper(dateFormat));
    }

    public void save(String idCustomer, String note) {
        template.update("INSERT INTO customer_notes (id, id_customer, created, modified, note) VALUES (?,?,?,?,?)", new Object[] {
                UUID.randomUUID().toString(),
                idCustomer.toString(),
                dateFormat.format(new Date()),
                dateFormat.format(new Date()),
                note
        });
    }

    public CustomerNote get(String id) {
        return template.queryForObject("SELECT * FROM customer_notes WHERE id=?", new Object[] {id}, new CustomerNoteMapper(dateFormat));
    }

    public void update(String id, String note) {
        template.update("UPDATE customer_notes SET note=?, modified=? WHERE id=?", new Object[] {
                note,
                dateFormat.format(new Date()),
                id
        });
    }

    public void delete(String id) {
        template.update("DELETE FROM customer_notes WHERE id=?", new Object[] {id});
    }
}
