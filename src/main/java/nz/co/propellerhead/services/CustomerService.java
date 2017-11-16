package nz.co.propellerhead.services;

import nz.co.propellerhead.dao.CustomerDAO;
import nz.co.propellerhead.dao.CustomerNoteDAO;
import nz.co.propellerhead.domains.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by proper on 16.11.17.
 */
@Component
public class CustomerService {

    @Autowired CustomerNoteDAO customerNoteDAO;
    @Autowired CustomerDAO customerDAO;

    public List<Customer> getAll() {
        return null;
    }
}
