package org.banking.mbankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.banking.mbankingbackend.dtos.CustomerDTO;
import org.banking.mbankingbackend.entities.Customer;
import org.banking.mbankingbackend.services.BankAccountService;
import org.banking.mbankingbackend.services.exception.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/customers")
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
     public List<CustomerDTO> customers()
     {
         return bankAccountService.listCustomers();
     }

     @GetMapping("/customers/search")
     public List<CustomerDTO> serachCustomers(
             @RequestParam(name = "keyword",
                     defaultValue = "") String keyword)
     {
         return bankAccountService.searchCustomers("%"+keyword+"%");
     }
     @GetMapping("/Customers/{id}]")
     public CustomerDTO getCustommer(@PathVariable(name = "id") String customerId) throws CustomerNotFoundException {
        CustomerDTO customer= bankAccountService.getCustomer(customerId);
         return customer;
     }

     @PostMapping("/customers")
     public CustomerDTO saveCustomer(@RequestBody CustomerDTO request)
     {
        return bankAccountService.saveCustomer(request);
     }

     @PutMapping("/customers/{id}")
     public CustomerDTO updateCustomer(@PathVariable(name = "id") String customerId,@RequestBody CustomerDTO customerDTO)
     {
         customerDTO.setId(customerId);
         return bankAccountService.updateCustomer(customerDTO);
     }

     @DeleteMapping("/customers/{id}")
     public void deleteCostomer(@PathVariable(name = "id") String customerId)
     {
         bankAccountService.deleteCustomer(customerId);
     }


}
