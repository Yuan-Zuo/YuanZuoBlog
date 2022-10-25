package com.Myblog.demo.controller;

import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.NotificationDTO;
import com.Myblog.demo.dto.PaginationDTO;
import com.Myblog.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Notification;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/yuanzuoBlog")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String toProfile(@PathVariable(name = "id") Long id, User user){
        NotificationDTO notificationDTO= notificationService.read(id, user);

        return "redirect:/yuanzuoBlog/blogs/"+notificationDTO.getOuterId();
    }
}
