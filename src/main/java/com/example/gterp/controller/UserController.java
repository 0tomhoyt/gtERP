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
        List<Staff> staffList = staffRepository.findAllActiveStaff();
        List<Client> clientList = clientRepository.findAllActiveClients();

        StaffListWrapper staffListWrapper = new StaffListWrapper();
        ClientListWrapper clientListWrapper = new ClientListWrapper();
        staffListWrapper.setStaffList(staffList);
        clientListWrapper.setClientList(clientList);

        model.addAttribute("staffListWrapper", staffListWrapper);
        model.addAttribute("clientListWrapper", clientListWrapper);

        // 如果需要 staffList，可以从 staffListWrapper 中获取
        model.addAttribute("staffList", staffListWrapper.getStaffList());

        return "user/manage-users"; // 返回管理页面
    }



    @PostMapping("/edit/staff")
    public String editStaff(@ModelAttribute("staffListWrapper") StaffListWrapper staffListWrapper, RedirectAttributes redirectAttributes) {
        List<Staff> staffList = staffListWrapper.getStaffList();

        for (Staff staff : staffList) {
            if (staff.isDelete()) {
                Staff existingStaff = staffRepository.findById(staff.getId()).orElse(null);
                if (existingStaff != null) {
                    existingStaff.setDeleted(true);
                    staffRepository.save(existingStaff);
                }
            } else {
                // Retrieve the existing staff from the database
                Staff existingStaff = staffRepository.findById(staff.getId()).orElse(null);
                if (existingStaff != null) {
                    // Preserve the password
                    staff.copyNonNullPropertiesFrom(existingStaff);
                    staffRepository.save(staff);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "员工列表更新成功！");
        return "redirect:/user/manage";
    }



    @PostMapping("/edit/client")
    public String editClient(@ModelAttribute("clientListWrapper") ClientListWrapper clientListWrapper, RedirectAttributes redirectAttributes) {
        List<Client> clientList = clientListWrapper.getClientList();

        for (Client client : clientList) {
            if (client.isDelete()) {
                Client existingClient = clientRepository.findById(client.getId()).orElse(null);
                if (existingClient != null) {
                    existingClient.setDeleted(true);
                    clientRepository.save(existingClient);
                }
            } else {
                // Retrieve the existing client from the database
                Client existingClient = clientRepository.findById(client.getId()).orElse(null);
                if (existingClient != null) {
                    // Preserve the password or other non-editable fields
                    client.copyNonNullPropertiesFrom(existingClient);

                    // 设置 Staff 属性
                    if (client.getStaff() != null && client.getStaff().getId() != null) {
                        Staff staff = staffRepository.findById(client.getStaff().getId()).orElse(null);
                        if (staff != null) {
                            client.setStaff(staff);
                        } else {
                            // 如果未找到对应的 Staff，返回错误信息
                            redirectAttributes.addFlashAttribute("message", "未找到 ID 为 " + client.getStaff().getId() + " 的员工。");
                            return "redirect:/user/manage";
                        }
                    } else {
                        // 如果未选择 Staff，保留原有的 Staff
                        client.setStaff(existingClient.getStaff());
                    }

                    // Save the updated client
                    clientRepository.save(client);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "客户列表更新成功！");
        return "redirect:/user/manage";
    }


    @PostMapping("/delete/staff/{id}")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Staff staff = staffRepository.findById(id).orElse(null);
        if (staff != null) {
            staff.setDeleted(true);
            staffRepository.save(staff);
            redirectAttributes.addFlashAttribute("message", "员工删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "未找到指定的员工。");
        }
        return "redirect:/user/manage";
    }

    @PostMapping("/delete/client/{id}")
    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setDeleted(true);
            clientRepository.save(client);
            redirectAttributes.addFlashAttribute("message", "客户删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("error", "未找到指定的客户。");
        }
        return "redirect:/user/manage";
    }


}
