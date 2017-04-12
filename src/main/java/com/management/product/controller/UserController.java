package com.management.product.controller;

import com.management.product.entity.User;
import com.management.product.enums.UserRole;
import com.management.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The class provides a set of methods for operations with User entity
 *
 * @author Slava Makhinich
 */

@Controller
@ComponentScan(basePackages = "com.management.product.service")
public class UserController {

    /**
     * An instance of implementation UserService interface
     */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param userService An instance of implementation UserService interface.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method creates a page to add a new user
     *
     * @return a page to add a new user
     */
    @RequestMapping(
            value = "/user/new",
            method = RequestMethod.GET
    )
    public ModelAndView getNewUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roles", UserRole.values());
        modelAndView.addObject("is_admin", this.userService.isAuthenticatedAdmin());
        modelAndView.setViewName("add_user");
        return modelAndView;
    }

    /**
     * Method to add a new user
     *
     * @param username user's name
     * @param password user's password
     * @param role     user's role
     * @param isLocked information about locking user's account
     * @return an address of users page
     */
    @RequestMapping(
            value = "/user/add",
            method = RequestMethod.POST
    )
    public String addNewUser(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "role", defaultValue = "USER") UserRole role,
            @RequestParam(value = "locked", defaultValue = "false") boolean isLocked
    ) {
        User userToAdd = new User(username, password, role);
        userToAdd.setLocked(isLocked);
        userService.add(userToAdd);
        return "redirect:/users";
    }

    /**
     * Method creates a page to update a new user
     *
     * @param id user's id
     * @return a page to update a new user
     */
    @RequestMapping(
            value = "/admin/user/edit/{id}",
            method = RequestMethod.GET
    )
    public ModelAndView getPageForUpdatingUser(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", this.userService.get(id));
        modelAndView.addObject("roles", UserRole.values());
        modelAndView.addObject("is_admin", true);
        modelAndView.setViewName("edit_user");
        return modelAndView;
    }

    /**
     * Method to update user
     *
     * @param id       user's id
     * @param username user's name
     * @param password user's password
     * @param role     user's role
     * @param isLocked information about locking user's account
     * @return an address of users page
     */
    @RequestMapping(
            value = "/admin/user/update/{id}",
            method = RequestMethod.POST
    )
    public String updateUser(
            @PathVariable(name = "id") long id,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "role", defaultValue = "USER") UserRole role,
            @RequestParam(value = "locked", defaultValue = "false") boolean isLocked
    ) {
        User user = userService.get(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setLocked(isLocked);
        userService.update(user);
        return "redirect:/users";
    }

    /**
     * Method to remove user by id
     *
     * @param id users's id
     * @return an address of users page
     */
    @RequestMapping(
            value = "/admin/user/delete/{id}",
            method = RequestMethod.GET
    )
    public String deleteUser(@PathVariable(name = "id") long id) {
        userService.remove(id);
        return "redirect:/users";
    }

    /**
     * Method to remove all users
     *
     * @return an address of users page
     */
    @RequestMapping(
            value = "/admin/user/delete/all",
            method = RequestMethod.GET
    )
    public String deleteAllUsers() {
        userService.removeAll();
        return "redirect:/users";
    }

}
