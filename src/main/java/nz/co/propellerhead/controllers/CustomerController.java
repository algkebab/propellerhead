package nz.co.propellerhead.controllers;

import nz.co.propellerhead.dao.CustomerDAO;
import nz.co.propellerhead.dao.CustomerNoteDAO;
import nz.co.propellerhead.domains.Customer;
import nz.co.propellerhead.domains.CustomerNote;
import nz.co.propellerhead.domains.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by proper on 16.11.17.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired private CustomerDAO customerDAO;
    @Autowired private CustomerNoteDAO customerNoteDAO;

    @RequestMapping("/list")
    public String list(ModelMap model, HttpSession session) {

        Map<String, String> sort = (Map<String, String>) session.getAttribute("sort");
        Map<String, Object> filters = (Map<String, Object>) session.getAttribute("filters");
        model.put("customers", customerDAO.filter(filters, sort));

        if (filters != null)
            model.putAll(filters);

        if (sort != null)
            model.putAll(sort);

        model.put("statuses", CustomerStatus.values());

        return "customers/list";
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public String sort(@RequestParam String sort,
                       @RequestParam String order,
                       HttpSession session) {

        session.removeAttribute("sort");
        Map<String, String> sortCriteria = new HashMap<>();
        sortCriteria.put("sort", sort);
        sortCriteria.put("order", order);
        session.setAttribute("sort", sortCriteria);

        return "redirect:/customers/list";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String list(@RequestParam(required = false) String status,
                       @RequestParam(required = false) @DateTimeFormat(pattern="yy-MM-dd") Date createdFrom,
                       @RequestParam(required = false) @DateTimeFormat(pattern="yy-MM-dd") Date createdTo,
                       @RequestParam(required = false) Boolean hasNotes,
                       @RequestParam(required = false) String name,
                       HttpSession session) {

        session.removeAttribute("filters");
        Map<String, Object> filters = new HashMap<>();
        if (!StringUtils.isEmpty(status))
            filters.put("status", status);

        if (createdFrom != null)
            filters.put("createdFrom", createdFrom);

        if (createdTo != null)
            filters.put("createdTo", createdTo);

        if (hasNotes != null)
            filters.put("hasNotes", hasNotes);

        if (!StringUtils.isEmpty(name))
            filters.put("name", name);

        if (!filters.isEmpty())
            session.setAttribute("filters", filters);

        return "redirect:/customers/list";
    }

    @RequestMapping("/filter/reset")
    public String resetFilter(HttpSession session) {
        session.removeAttribute("filters");
        return "redirect:/customers/list";
    }

    @RequestMapping("/details/{idCustomer}")
    public String list(@PathVariable String idCustomer, ModelMap model) {

        Customer customer = customerDAO.get(idCustomer);
        customer.setNotes(customerNoteDAO.getByCustomerId(customer.getId().toString()));

        model.put("customer", customer);

        return "customers/details";
    }

    @RequestMapping(value = "/change/status", method = RequestMethod.POST)
    public @ResponseBody String statusChange(@RequestParam String idCustomer, String status) {
        Customer customer = customerDAO.get(idCustomer);
        customer.setStatus(CustomerStatus.valueOf(status));
        customerDAO.update(customer);
        return "success";
    }

}
