package nz.co.propellerhead.controllers;

import nz.co.propellerhead.dao.CustomerNoteDAO;
import nz.co.propellerhead.domains.CustomerNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by proper on 16.11.17.
 */
@Controller
@RequestMapping("/notes")
public class CustomerNoteController {

    @Autowired private CustomerNoteDAO customerNoteDAO;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam String note,
                      @RequestParam String idCustomer) {

        customerNoteDAO.save(idCustomer, note);
        return "redirect:/customers/details/" + idCustomer;
    }

    @RequestMapping("/edit/{idCustomer}/{idNote}")
    public String note(@PathVariable String idNote,
                       @PathVariable String idCustomer,
                       ModelMap model) {
        CustomerNote note = customerNoteDAO.get(idNote);
        model.put("note", note);
        model.put("idCustomer", idCustomer);
        return "notes/edit";
    }

    @RequestMapping("/delete/{idNote}")
    public String delete(@PathVariable String idNote) {
        CustomerNote note = customerNoteDAO.get(idNote);
        customerNoteDAO.delete(idNote);
        return "redirect:/customers/details/" + note.getCustomerId().toString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String save(@RequestParam String idNote,
                       @RequestParam String idCustomer,
                       @RequestParam String note) {
        customerNoteDAO.update(idNote, note);
        return "redirect:/customers/details/" + idCustomer;
    }

}
