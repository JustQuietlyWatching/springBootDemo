//package space.anwenchu.controller;
//
///**
// * Created by an_wch on 2018/4/4.
// */
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import space.anwenchu.service.Register;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//
//    @Autowired
//    Register register;
//
//    @GetMapping("/{name}")
//    public void getBeanByName(@PathVariable(value = "name") String name) {
//        register.getStorageType(name);
//    }
//}