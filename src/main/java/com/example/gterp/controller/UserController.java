package com.example.gterp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.gterp.repository.*;
import com.example.gterp.entity.user.*;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ClientRepository clientRepository;

    // 显示选择创建Staff还是Client的页面
    @GetMapping("/new")
    public String selectUserType(Model model) {
        return "user/select-type";
    }

    // 创建员工的表单
    @GetMapping("/new/staff")
    public String createStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "user/create-staff";
    }

    // 提交员工创建请求
    @PostMapping("/new/staff")
    public String createStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        staffRepository.save(staff);
        redirectAttributes.addFlashAttribute("message", "Staff created successfully!");
        return "redirect:/user";
    }

    // 创建客户的表单
    @GetMapping("/new/client")
    public String createClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "user/create-client";
    }

    // 提交客户创建请求
    @PostMapping("/new/client")
    public String createClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        clientRepository.save(client);
        redirectAttributes.addFlashAttribute("message", "Client created successfully!");
        return "redirect:/user";
    }

    // 显示所有员工和客户的信息
    @GetMapping("/manage")
    public String showStaffAndClients(Model model) {
        List<Staff> staffList = staffRepository.findAll();
        List<Client> clientList = clientRepository.findAll();

        // 确保 staffList 和 clientList 不为空
        if (staffList.isEmpty()) {
            model.addAttribute("staffList", new ArrayList<Staff>());
        } else {
            model.addAttribute("staffList", staffList);
        }

        if (clientList.isEmpty()) {
            model.addAttribute("clientList", new ArrayList<Client>());
        } else {
            model.addAttribute("clientList", clientList);
        }

        return "user/manage-users"; // 返回管理页面
    }
    // 处理员工信息的编辑请求
    @PostMapping("/edit/staff")
    public String editStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        staffRepository.save(staff);  // 保存员工的修改
        redirectAttributes.addFlashAttribute("message", "Staff updated successfully!");
        return "redirect:/user/manage"; // 编辑后重定向到管理页面
    }

    // 处理客户信息的编辑请求
    @PostMapping("/edit/client")
    public String editClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        clientRepository.save(client);  // 保存客户的修改
        redirectAttributes.addFlashAttribute("message", "Client updated successfully!");
        return "redirect:/user/manage"; // 编辑后重定向到管理页面
    }
}
