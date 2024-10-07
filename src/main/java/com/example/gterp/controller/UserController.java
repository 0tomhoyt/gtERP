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
import java.util.Iterator;

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
        redirectAttributes.addFlashAttribute("message", "员工创建成功!");
        return "redirect:/user/manage";
    }

    // 创建客户的表单
    @GetMapping("/new/client")
    public String createClientForm(Model model) {
        model.addAttribute("client", new Client());

        // 获取所有的 Staff 列表
        List<Staff> staffList = staffRepository.findAll();
        model.addAttribute("staffList", staffList);

        return "user/create-client";
    }


    // 提交客户创建请求
    @PostMapping("/new/client")
    public String createClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        // 获取用户选择的 Staff ID
        Long staffId = client.getStaff().getId();

        // 根据 ID 从数据库中查找 Staff
        Staff staff = staffRepository.findById(staffId).orElse(null);
        if (staff == null) {
            // 如果没有找到对应的 Staff，返回错误信息
            redirectAttributes.addFlashAttribute("message", "所选员工不存在!");
            return "redirect:/user/new/client";
        }

        // 设置 Staff 到 Client 对象中
        client.setStaff(staff);

        // 保存 Client 对象
        clientRepository.save(client);

        redirectAttributes.addFlashAttribute("message", "客户创建成功!");
        return "redirect:/user/manage";
    }


    // 显示所有员工和客户的信息
    @GetMapping("/manage")
    public String showStaffAndClients(Model model) {
        List<Staff> staffList = staffRepository.findAll();
        List<Client> clientList = clientRepository.findAll();

        StaffListWrapper staffListWrapper = new StaffListWrapper();
        ClientListWrapper clientListWrapper = new ClientListWrapper();
        // 确保 staffList 和 clientList 不为空
        if (staffList.isEmpty()) {
            staffListWrapper.setStaffList(new ArrayList<Staff>());
        } else {
            staffListWrapper.setStaffList(staffList);
        }

        if (clientList.isEmpty()) {
            clientListWrapper.setClientList(new ArrayList<Client>());
        } else {
            clientListWrapper.setClientList(clientList);
        }

        model.addAttribute("staffListWrapper", staffListWrapper);
        model.addAttribute("clientListWrapper", clientListWrapper);


        return "user/manage-users"; // 返回管理页面

    }

    @PostMapping("/edit/staff")
    public String editStaff(@ModelAttribute("staffListWrapper") StaffListWrapper staffListWrapper, RedirectAttributes redirectAttributes) {
        List<Staff> staffList = staffListWrapper.getStaffList();

        Iterator<Staff> iterator = staffList.iterator();
        while (iterator.hasNext()) {
            Staff staff = iterator.next();
            if (staff.isDelete()) {
                staffRepository.deleteById(staff.getId());
                iterator.remove();
            } else {
                // Retrieve the existing staff from the database
                Staff existingStaff = staffRepository.findById(staff.getId()).orElse(null);
                if (existingStaff != null) {
                    // Preserve the password
                    staff.setPassword(existingStaff.getPassword());
                    // Save the updated staff
                    staffRepository.save(staff);
                } else {
                    // Handle the case where the staff does not exist
                    // You might want to log a warning or throw an exception
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "员工列表更新成功！");
        return "redirect:/user/manage";
    }


    @PostMapping("/edit/client")
    public String editClient(@ModelAttribute("clientListWrapper") ClientListWrapper clientListWrapper, RedirectAttributes redirectAttributes) {
        List<Client> clientList = clientListWrapper.getClientList();

        Iterator<Client> iterator = clientList.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.isDelete()) {
                clientRepository.deleteById(client.getId());
                iterator.remove();
            } else {
                // Retrieve the existing client from the database
                Client existingClient = clientRepository.findById(client.getId()).orElse(null);
                if (existingClient != null) {
                    // Preserve the password or other non-editable fields
                    client.setPassword(existingClient.getPassword());
                    // Save the updated client
                    clientRepository.save(client);
                } else {
                    // Handle the case where the client does not exist
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "客户列表更新成功！");
        return "redirect:/user/manage";
    }

    //    删除员工和客户
    @PostMapping("/delete/staff/{id}")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        staffRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "员工删除成功！");
        return "redirect:/user/manage";
    }
    @PostMapping("/delete/client/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clientRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "客户删除成功！");
        return "redirect:/user/manage";
    }

}
