package com.memorynotfound.spring.security.web;

import com.memorynotfound.spring.security.model.DBFile;
import com.memorynotfound.spring.security.model.Order;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.service.DBFileStorageServiceImpl;
import com.memorynotfound.spring.security.service.OrderService;
import com.memorynotfound.spring.security.service.ProductService;
import com.memorynotfound.spring.security.service.UserService;
import com.memorynotfound.spring.security.web.dto.ProductDto;
import com.memorynotfound.spring.security.web.dto.UserEditDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;


    @ModelAttribute("modUser")
    public UserEditDto userEditDto() {return new UserEditDto();}

    @ModelAttribute("prod")
    public  ProductDto productDto() {return new ProductDto();}


    @GetMapping("admin/adminIndex")
    public ModelAndView goIndex(){
        System.out.println("jestesmy w adminindex");
        ModelAndView model = new ModelAndView("admin/adminIndex");

        List<User> list = userService.findAll();
        model.addObject("userList",list);

        List<Product> productList = productService.findAll();
        model.addObject("productList",productList);

        List<Order> orderList = orderService.findAllActive();
        System.out.println("orderlist: " + orderList.size());
        model.addObject("orderList",orderList);

        return model;
    }

    @PostMapping("admin/adminIndex/editUser/{email}") //
    public ModelAndView editUser(@ModelAttribute(value="modUser") @Valid UserEditDto tempUser,
                                 BindingResult result, @PathVariable String email){
        System.out.println("jestesmy w edituser");
        System.out.println("emailvariable: " + email);
        System.out.println("takie imnie " + tempUser.getFirstName());
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        if(result.hasErrors()){
            System.out.println("error");
            //return model;
        }

        User userToEdit = userService.findByEmail(email);
        userService.changeInfo(userToEdit,tempUser);

        System.out.println("takie imnie " + tempUser.getFirstName());
        //System.out.println("takie email " + tempUser.getEmail());
        return model;
    }


    @PostMapping("/admin/adminIndex/addProduct/")
    public ModelAndView addProduct(@ModelAttribute(value="prod") @Valid ProductDto tempProd,
                                    @RequestParam("file") MultipartFile file){
        System.out.println("Jestesmy w addproduct");
        System.out.println("ktegoria to: " + tempProd.getCategory());
        System.out.println("nazwa prod: " + tempProd.getName());
        System.out.println("koszt prod: " + tempProd.getCost());
        Product isProduct = productService.addNewProduct(tempProd);

        DBFile dbFile = DBFileStorageService.storeFile(file);

        if(isProduct==null) System.out.println("cos nie ta z dodawaniem");
        else System.out.println("udalo sie nowy product");
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }


    @GetMapping("/admin/adminIndex/deleteProduct/{prodName}")
    public ModelAndView deleteProduct(@PathVariable String prodName){
        System.out.println("Jestesmy w deleteProduct");
        System.out.println("nazwa prod to: " + prodName);
        Product prodToDel = productService.findByName(prodName);
        productService.deleteProduct(prodToDel);
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }

    @GetMapping("/admin/adminIndex/deleteUser/{userEmail}")
    public ModelAndView deleteUser(@PathVariable String userEmail){
        System.out.println("Jestesmy w deleteUser");
        User userToDel =userService.findByEmail(userEmail);
        userService.deleteUser(userToDel);
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }

    @Transactional
    @GetMapping("/admin/adminIndex/denyOrder/{userEmail}/{productName}")
    public ModelAndView denyOrder(@PathVariable String userEmail, @PathVariable String productName){
        System.out.println("Jestesmy w denyOrder");
        User user = userService.findByEmail(userEmail);
        Product product = productService.findByName(productName);
        Order order = orderService.findOrderByUserAndProductAndState(user,product,"waiting");
        //order.setActive(0);
        //orderService.save(order);
        //orderService.deleteOrder(order);
        order.setState("denied");
        orderService.saveAndFlush(order);
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }

    @Transactional
    @GetMapping("/admin/adminIndex/acceptOrder/{userEmail}/{productName}")
    public ModelAndView acceptOrder(@PathVariable String userEmail, @PathVariable String productName){
        System.out.println("Jestesmy w acceptOrder");
        User user = userService.findByEmail(userEmail);
        Product product = productService.findByName(productName);
        Order order = orderService.findOrderByUserAndProductAndState(user,product,"waiting");
        long moneyToPay = product.getCost()*order.getAmount();
        long userMoney = user.getMoney();
        user.setMoney(userMoney-moneyToPay);
        userService.save(user);
        order.setState("accepted");
        //orderService.deleteOrder(order);
        orderService.saveAndFlush(order);
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }

    @Autowired
    private DBFileStorageServiceImpl DBFileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    //@Transactional
    @PostMapping("/admin/adminIndex/uploadPhotoQQQ")
    public @ResponseBody ModelAndView  uploadProductPhoto(@RequestParam("file") MultipartFile file){
        System.out.println("Jestesmy w uploadPhoto");
        System.out.println("Jestesmy w uploadPhoto");
        DBFile dbFile = DBFileStorageService.storeFile(file);
        ModelAndView model = new ModelAndView("redirect:/admin/adminIndex");
        return model;
    }
}
