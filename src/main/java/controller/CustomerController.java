package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import java.util.List;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ICustomerService;

@Controller
public class CustomerController {

  @Autowired
  private ICustomerService customerService;

  @GetMapping("/create-customer")
  public ModelAndView showCustomer() {
    ModelAndView modelAndView = new ModelAndView("/customer/create");
    modelAndView.addObject("customer", new Customer());
    return modelAndView;
  }

  @PostMapping("/create-customer")
  public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
    customerService.save(customer);
    ModelAndView modelAndView = new ModelAndView("/customer/create");
    modelAndView.addObject("customer", new Customer());
    modelAndView.addObject("message", "New customer created successfully");
    return modelAndView;
  }

  @GetMapping("/customers")
  public ModelAndView listCustomers() {
    List<Customer> customers = customerService.findAll();
    ModelAndView modelAndView = new ModelAndView("/customer/list");
    modelAndView.addObject("customers", customers);
    return modelAndView;
  }

  @GetMapping("/edit-customer/{id}")
  public ModelAndView showEditForm(@PathVariable Long id) {
    Customer customer = customerService.findById(id);
    if (customer != null) {
      ModelAndView modelAndView = new ModelAndView("/customer/edit");
      modelAndView.addObject("customer", customer);
      return modelAndView;
    } else {
      ModelAndView modelAndView = new ModelAndView("/error.404");
      return modelAndView;
    }
  }

  @PostMapping("/edit-customer")
  public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
    customerService.save(customer);
    ModelAndView modelAndView = new ModelAndView("/customer/edit");
    modelAndView.addObject("customer", customer);
    modelAndView.addObject("message", "Customer updated succesfully");
    return modelAndView;
  }

  @GetMapping("/delete-customer/{id}")
  public ModelAndView showDeleteForm(@PathVariable Long id){
    Customer customer = customerService.findById(id);
    if (customer != null){
      ModelAndView modelAndView = new ModelAndView("/customer/delete");
      modelAndView.addObject("customer", customer);
      return modelAndView;
    }else {
      ModelAndView modelAndView = new ModelAndView("/error.404");
      return modelAndView;
    }
  }

  @PostMapping("/delete-customer")
  public String deleteCustomer(@ModelAttribute("customer") Customer customer){
    customerService.remove(customer.getId());
    return "redirect:customers";
  }
}
