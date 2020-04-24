//package com.uzdz.study.tk;
//
//import com.uzdz.study.tk.dao.Customer;
//import com.uzdz.study.tk.mapper.CustomerMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class BaseCustomerServiceImpl {
//
//    @Autowired
//    private CustomerMapper customerMapper;
//
//    @GetMapping("/tk")
//    public int tk() {
//        return customerMapper.insertSelective(Customer.builder().code("001").name("uzdz").build());
//    }
//}
