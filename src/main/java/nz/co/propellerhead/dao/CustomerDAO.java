package nz.co.propellerhead.dao;

import nz.co.propellerhead.domains.Customer;
import nz.co.propellerhead.domains.CustomerNote;
import nz.co.propellerhead.mappers.CustomerMapper;
import nz.co.propellerhead.mappers.CustomerNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by proper on 16.11.17.
 */
@Repository
public class CustomerDAO {

    private JdbcTemplate template;
    private DateFormat dateFormat;

    @Autowired CustomerNoteDAO customerNoteDAO;

    @Autowired
    public CustomerDAO(DataSource dataSource, DateFormat dateFormat) {
        this.template = new JdbcTemplate(dataSource);
        this.dateFormat = dateFormat;
    }

    public Customer get(String idCustomer) {
        return template.queryForObject("SELECT * FROM customers WHERE id=?", new Object[] {idCustomer}, new CustomerMapper(dateFormat));
    }

    public List<Customer> filter(Map<String, Object> filters, Map<String, String> sort) {
        String sql = "SELECT * FROM customers WHERE 1=1";
        List<Object> args = new LinkedList<>();

        if (filters != null) {

            if (filters.get("status") != null) {
                sql += " AND status=?";
                args.add(filters.get("status"));
            }

            if (filters.get("createdFrom") != null) {
                sql += " AND created>=?";
                args.add(dateFormat.format(filters.get("createdFrom")));
            }

            if (filters.get("createdTo") != null) {
                sql += " AND created<=?";
                args.add(dateFormat.format(filters.get("createdTo")));
            }

            if (filters.get("name") != null) {
                sql += " AND name LIKE ?";
                args.add("%" + filters.get("name") + "%");
            }

            if (filters.get("hasNotes") != null) {
                boolean hasNotes = (boolean) filters.get("hasNotes");
                sql += " AND id " + (!hasNotes ? "NOT " : "") + "IN (SELECT customer_notes.id_customer FROM customer_notes INNER JOIN customers ON customer_notes.id_customer==customers.id)";
            }
        }

        if (sort != null && !StringUtils.isEmpty(sort.get("sort"))) {
            sql += " ORDER BY " + sort.get("sort") + " " + (!StringUtils.isEmpty(sort.get("order")) ? sort.get("order") : "ASC");
        }

        return new LinkedList<>(template.query(sql, args.toArray(), new CustomerMapper(dateFormat)));

    }

    public void update(Customer customer) {
        template.update("UPDATE customers SET name=?,phone=?,status=?,modified=? WHERE id=?", new Object[] {
                customer.getName(),
                customer.getPhone(),
                customer.getStatus().name(),
                dateFormat.format(new Date()),
                customer.getId().toString()});
    }

}
