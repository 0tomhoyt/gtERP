package com.example.gterp.controller;

import com.example.gterp.entity.contract.Contract;
import com.example.gterp.entity.user.Client;
import com.example.gterp.entity.user.Staff;
import com.example.gterp.repository.ClientRepository;
import com.example.gterp.repository.ContractRepository;
import com.example.gterp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDate;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContractRepository contractRepository;
    @GetMapping("/create")
    public String showCreateContractPage(Model model) {
        List<Staff> staffList = staffRepository.findAll();
        model.addAttribute("staffList", staffList); // 将所有 Staff 传递给前端

        // 初始化空的 clientList
        model.addAttribute("clientList", new ArrayList<Client>());

        // 创建一个新的 Contract 对象，并设置创建日期为今天
        Contract contract = new Contract();
        contract.setCreationDate(LocalDate.now()); // 设置创建日期为今天
        model.addAttribute("contract", contract);

        return "contract/contract"; // 返回合同模板
    }

    @GetMapping("/edit/{id}")
    public String showEditContractPage(@PathVariable("id") Long id, Model model) {
        List<Staff> staffList = staffRepository.findAll();
        model.addAttribute("staffList", staffList); // 将所有 Staff 传递给前端

        Optional<Contract> optionalContract = contractRepository.findById(id);
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            model.addAttribute("contract", contract); // 将找到的合同对象传递给前端

            // 查找与合同相关联的 Client 和 Staff
            Staff selectedStaff = contract.getClient().getStaff();
            model.addAttribute("selectedStaff", selectedStaff); // 设置当前选择的 Staff

            List<Client> clientList = clientRepository.findByStaffId(selectedStaff.getId());
            model.addAttribute("clientList", clientList); // 设置 Staff 对应的 Clients
        } else {
            return "redirect:/contracts/create"; // 如果合同不存在，重定向到创建页面
        }

        return "contract/contract";
    }


    // 根据选择的 Staff 获取其对应的 Clients
    @GetMapping("/clients/{staffId}")
    @ResponseBody
    public List<Client> getClientsByStaff(@PathVariable Long staffId) {
        return clientRepository.findByStaffId(staffId); // 根据 staffId 查找对应的 Client
    }


    // 提交创建或编辑合同表单
    @PostMapping("/save")
    public String saveContract(@ModelAttribute Contract contract) {
        contractRepository.save(contract); // 保存合同（包括创建和修改）
        return "redirect:/contracts"; // 重定向到合同列表页
    }
}
