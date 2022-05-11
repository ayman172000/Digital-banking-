package org.banking.mbankingbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.banking.mbankingbackend.dtos.CustomerDTO;
import org.banking.mbankingbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        //pour mapper les object vous pouvez utilisez les getters ou les setteres ou BeansUtilis.copyProperties
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
        //mapstruct gmapper
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }

}
