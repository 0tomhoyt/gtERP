import com.example.gterp.controller.ContractController;
import com.example.gterp.entity.contract.Contract;
import com.example.gterp.entity.user.Client;
import com.example.gterp.entity.user.Staff;
import com.example.gterp.repository.ClientRepository;
import com.example.gterp.repository.ContractRepository;
import com.example.gterp.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContractControllerTest {

    @InjectMocks
    private ContractController contractController;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ContractRepository contractRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 如果需要使用 MockMvc 进行测试，可以在这里初始化它
        // mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();
    }

    @Test
    public void testShowCreateContractPage() {
        List<Staff> staffList = new ArrayList<>();
        when(staffRepository.findAll()).thenReturn(staffList);

        Model model = mock(Model.class);
        String viewName = contractController.showCreateContractPage(model);

        verify(model).addAttribute("staffList", staffList);
        verify(model).addAttribute("clientList", new ArrayList<Client>());
        verify(model).addAttribute("contract", any(Contract.class));
        assert viewName.equals("contract/contract");
    }

    @Test
    public void testShowEditContractPage_ExistingContract() {
        Long id = 1L;
        Staff staff = new Staff();
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        when(staffRepository.findAll()).thenReturn(staffList);

        Contract contract = new Contract();
        when(contractRepository.findById(id)).thenReturn(Optional.of(contract));
        List<Client> clientList = new ArrayList<>();
        when(clientRepository.findByStaffId(staff.getId())).thenReturn(clientList);

        Model model = mock(Model.class);
        String viewName = contractController.showEditContractPage(id, model);

        verify(model).addAttribute("staffList", staffList);
        verify(model).addAttribute("contract", contract);
        verify(model).addAttribute("selectedStaff", staff);
        verify(model).addAttribute("clientList", clientList);
        assert viewName.equals("contract/contract");
    }

    @Test
    public void testShowEditContractPage_NonExistingContract() {
        Long id = 1L;
        when(contractRepository.findById(id)).thenReturn(Optional.empty());

        RedirectView redirectView = (RedirectView) contractController.showEditContractPage(id, mock(Model.class));

        assert redirectView.getUrl().equals("redirect:/contracts/create");
    }

    @Test
    public void testGetClientsByStaff() throws Exception {
        Long staffId = 1L;
        List<Client> clients = new ArrayList<>();
        when(clientRepository.findByStaffId(staffId)).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/contracts/clients/{staffId}", staffId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void testSaveContract() {
        Contract contract = new Contract();
        RedirectView redirectView = (RedirectView) contractController.saveContract(contract);

        verify(contractRepository).save(contract);
        assert redirectView.getUrl().equals("redirect:/contracts");
    }
}