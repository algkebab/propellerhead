package nz.co.propellerhead.mappers;

import nz.co.propellerhead.domains.Customer;
import nz.co.propellerhead.domains.CustomerStatus;
import org.springframework.util.StringUtils;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.UUID;

/**
 * Created by proper on 16.11.17.
 */
public class CustomerMapper implements RowMapper<Customer> {

    private DateFormat dateFormat;

    public CustomerMapper(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(!StringUtils.isEmpty(rs.getString("id")) ? UUID.fromString(rs.getString("id")) : null);
        customer.setStatus(!StringUtils.isEmpty(rs.getString("status")) ? CustomerStatus.valueOf(rs.getString("status")) : null);
        try {
            customer.setCreated(dateFormat.parse(rs.getString("created")));
            customer.setModified(dateFormat.parse(rs.getString("modified")));
        } catch (ParseException e) {

        }
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));

        return customer;
    }
}