package com.memorynotfound.spring.security.web;

import com.memorynotfound.spring.security.model.User;
import com.memorynotfound.spring.security.service.RoleService;
import com.memorynotfound.spring.security.service.UserService;
import com.memorynotfound.spring.security.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {


    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, WebRequest request){
        // , @RequestParam("role") String role
        String role = "ROLE_USER";
        System.out.println("\n"+role+"\n");
        System.out.println("imie to: " + userDto.getFirstName());
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        roleService.addNewRole("ROLE_USER");
        roleService.addNewRole("ROLE_ADMIN");
        User registered = userService.save(userDto,roleService.findByName(role));
        /*try{
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (registered, request.getLocale(), appUrl));

        } catch (Exception me){

        }*/


        return "redirect:/registration?success";
    }

}
