package com.Myblog.demo.controller;

import com.Myblog.demo.domain.Notification;
import com.Myblog.demo.domain.User;
import com.Myblog.demo.dto.PaginationDTO;
import com.Myblog.demo.service.BlogService;
import com.Myblog.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/yuanzuoBlog")
public class profileController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String toProfile(@PathVariable(name = "action") String action,
                            Model model,
                            User user,
                            HttpServletRequest request,
                            @RequestParam(name = "page", defaultValue = "1") Integer page){
        if("blogs".equals(action)){
            model.addAttribute("section","blogs");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = blogService.list(user.getId(), page, IndexController.size);
            model.addAttribute("pagination", paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, IndexController.size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
