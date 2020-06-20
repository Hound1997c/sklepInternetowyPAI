package com.memorynotfound.spring.security.web;

import com.memorynotfound.spring.security.model.Bucket;
import com.memorynotfound.spring.security.model.Order;
import com.memorynotfound.spring.security.model.Product;
import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.service.BucketService;
import com.memorynotfound.spring.security.service.OrderService;
import com.memorynotfound.spring.security.service.ProductService;
import com.memorynotfound.spring.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private OrderService orderService;

    //@Autowired
    //private OrderService orderService;



    @GetMapping("user/userIndex")
    public ModelAndView goIndex(){
        System.out.println("jestesmy w user goIndex");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByEmail(currentPrincipalName);

        /*if(user.isEnabled()==false){
            System.out.println("nie ma jeszcze potwierdzenia email");
            ModelAndView model = new ModelAndView("redirect:/login");
            return model;
        }*/

        List<Product> productList = productService.findAll();
        //Collections.sort(productList, AnnotationAwareOrderComparator.INSTANCE);

        Map<String,List<Product>> groupByCategory = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
        System.out.println("typ tego to: " + groupByCategory.getClass());
        System.out.println(groupByCategory);


        System.out.println("enabled: " + user.isEnabled());
        List<Bucket> userBucket = bucketService.getUserBucket(user);
        long sumCost = bucketService.sumElements(user);

        String toTryMySqlFunctionResult = userService.tryMySqlFunction();
        String toTryMySqlFunction = toTryMySqlFunctionResult;

        ModelAndView model = new ModelAndView("user/userIndex");
        model.addObject("userBucket",userBucket);
        model.addObject("productList", productList);//groupByCategory
        model.addObject("sumCost",sumCost);
        model.addObject("categoryProductList",groupByCategory);
        model.addObject("tryMySqlFunction", toTryMySqlFunction);
        model.addObject("loggedUserMoney",user.getMoney());
        //System.out.println("jego bucket to: " + userBucket.size());
        //System.out.println("jestesmy w user goIndex");

        return model;
    }

    //@PostMapping("user/userIndex")
    @GetMapping("/addToBucket/{pname}")
    public ModelAndView addToBucket(@PathVariable String pname){ //@RequestParam("name") String name
        //System.out.println("to ten product " + product.getName());
        //String name = "xd";
        System.out.println("jestesmy w user addToBucket: " + pname);
        Product product = productService.findByName(pname);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByEmail(currentPrincipalName);
        Bucket bucket = bucketService.findByUserAndProduct(user,product);
        if(bucket==null){
            System.out.println("nie ma jeszcze takiego produktu");
            Bucket newBucket = new Bucket();
            bucketService.addProductToBucket(newBucket,user,product);
        }else{
            System.out.println("jest juz");
            bucket = bucketService.inkrementBucket(bucket);
            System.out.println("juz ma: " + bucket.getAmount());
        }
        ModelAndView model = new ModelAndView("redirect:/user/userIndex"); //redirect:
        System.out.println("taki rozmiar koszyka: " + user.getBucket().size());
        //System.out.println("jestesmy w user addToBucket: ");
        return model;
    }

    @GetMapping("/deleteFromBucket/{prodName}")
    public ModelAndView deleteFromBucket(@PathVariable String prodName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.findByEmail(currentPrincipalName);
        Product product = productService.findByName(prodName);
        Bucket bucket = bucketService.findByUserAndProduct(user,product);
        bucketService.delete(bucket);
        ModelAndView model = new ModelAndView("redirect:/user/userIndex");
        return model;
    }

    @Transactional
    @GetMapping("/buyBucket")
    public ModelAndView buyBucket(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedUser = userService.findByEmail(currentPrincipalName);


        /*boolean wasBuy = userService.buyBucket(loggedUser);
        if (wasBuy == true){

            bucketService.deleteAllByUser(loggedUser);
        }
        else System.out.println("not enought money"); */



        ModelAndView model = new ModelAndView("redirect:/user/userIndex");
        return model;
    }

    @Transactional
    @GetMapping("/user/userIndex/makeOrder")
    public ModelAndView makeOrder(){

        System.out.println("Jestesmy w makeOrder");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedUser = userService.findByEmail(currentPrincipalName);
        long price = bucketService.sumElements(loggedUser);
        long userMoney = loggedUser.getMoney();
        if(userMoney - price >= 0){
            List<Bucket> userBucket = bucketService.findAllByUser(loggedUser);
            for(Bucket bucket : userBucket){
                Order existOrder = orderService.findOrderByUserAndProductAndState(loggedUser,bucket.getProduct(),"waiting");
                if(existOrder==null){
                    Order order = new Order();
                    //order.setActive(1);
                    order.setUser(loggedUser);
                    order.setProduct(bucket.getProduct());
                    order.setAmount(bucket.getAmount());
                    orderService.save(order);
                }
                else{
                    long lastAmount = existOrder.getAmount();
                    existOrder.setAmount(lastAmount+bucket.getAmount());
                }
            }
            bucketService.deleteAllByUser(loggedUser);
        }
        else{
            System.out.println("not enought money");
        }
        ModelAndView model = new ModelAndView("redirect:/user/userIndex");
        return model;
    }


}
