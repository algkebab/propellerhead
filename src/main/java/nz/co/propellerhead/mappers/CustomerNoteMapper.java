package nz.co.propellerhead.mappers;

import nz.co.propellerhead.domains.CustomerNote;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.UUID;

/**
 * Created by proper on 16.11.17.
 */
public class CustomerNoteMapper implements RowMapper<CustomerNote> {

    private DateFormat dateFormat;

    public CustomerNoteMapper(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public CustomerNote mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerNote note = new CustomerNote();
        note.setId(!StringUtils.isEmpty(rs.getString("id")) ? UUID.fromString(rs.getString("id")) : null);
        note.setCustomerId(!StringUtils.isEmpty(rs.getString("id_customer")) ? UUID.fromString(rs.getString("id_customer")) : null);
        try {
            note.setCreated(dateFormat.parse(rs.getString("created")));
            note.setModified(dateFormat.parse(rs.getString("modified")));
        } catch (ParseException e) {
        }
        note.setNote(rs.getString("note"));

        return note;
    }
}
