package com.nnk.poseidoninc.UT.ControllerWebApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.Controller.ControllerWebApp.BidListControllerWebApp;
import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "userTEst", authorities = {"USER"})
class BidListControllerWebAppTest {

    @InjectMocks
    BidListControllerWebApp bidListControllerWebApp;

    @MockBean
    BidListServiceImpl bidListServiceMock;

    @Mock
    BidListServiceImpl bidListService;

    @Mock
    Model model;

    @Autowired
    MockMvc mockMvc;

    BidListDto bidListDto1 = new BidListDto();
    BidListDto bidListDto2 = new BidListDto();
    BidListDto bidListDtoUpdate = new BidListDto();
    BidList bidList1 = new BidList();
    BidList bidList2 = new BidList();
    BidList bidListUpdate = new BidList();


    Optional<BidList> bidListOptional0 = Optional.empty();
    Optional<BidList> bidListOptional1;
    Optional<BidList> bidListOptional2;
    List<BidList> bidListIterable = new ArrayList<>();
    List<BidListDto> bidListDtoList = new ArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();
    String bidListDto1Json;
    String bidListDtoListJson;

    @BeforeAll
    void buildTest() throws JsonProcessingException {

        bidList1.setBidId(1);
        bidList1.setAccount("accountTest1");
        bidList1.setType("typeTest1");
        bidList1.setBidQuantity(45d);
        bidListOptional1 = Optional.of(bidList1);

        bidList2.setBidId(2);
        bidList2.setAccount("accountTest2");
        bidList2.setType("typeTest2");
        bidList2.setBidQuantity(50d);
        bidListOptional2 = Optional.of(bidList2);

        bidListIterable.add(bidList1);
        bidListIterable.add(bidList2);


        bidListDto1.setBidListId(1);
        bidListDto1.setAccount("accountTest1");
        bidListDto1.setType("typeTest1");
        bidListDto1.setBidQuantity(45d);

        bidListDto2.setBidListId(2);
        bidListDto2.setAccount("accountTest2");
        bidListDto2.setType("typeTest2");
        bidListDto2.setBidQuantity(50d);


        bidListDtoList.add(bidListDto1);
        bidListDtoList.add(bidListDto2);


        bidListDtoUpdate.setBidListId(1);
        bidListDtoUpdate.setAccount("accountTestUpdate");
        bidListDtoUpdate.setType("typeTestUpdate");
        bidListDtoUpdate.setBidQuantity(50d);

        bidListUpdate.setBidId(1);
        bidListUpdate.setAccount("accountTestUpdate");
        bidListUpdate.setType("typeTestUpdate");
        bidListUpdate.setBidQuantity(50d);

        bidListDto1Json = objectMapper.writeValueAsString(bidListDto1);
        bidListDtoListJson = objectMapper.writeValueAsString(bidListDtoList);
    }


    @Test
    void home() throws Exception {
        when(bidListServiceMock.findAll()).thenReturn(bidListDtoList);

        mockMvc.perform(get("/BidList"))
                .andExpect(model().attribute("user", "userTESTA"))
                .andExpect(model().attribute("bidListDtoList", bidListDtoList))
                .andExpect(status().isOk());

    }

    @Test
    void addBidListPage() throws Exception {
        mockMvc.perform(get("/BidList/add"))
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    void addBidList() throws Exception {

        when(bidListServiceMock.create(any())).thenReturn(bidListDto1);

        mockMvc.perform(post("/BidList/add")
                        .param("account", "accountTest1")
                        .param("type", "typeTest1")
                        .param("bidQuantity", "45"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updatePage() throws Exception {

        when(bidListService.findById(anyInt())).thenReturn(bidListDto1);

        model.addAttribute("bidListDto", bidListDto1);

        String test = bidListControllerWebApp.updatePage(1, model);

        assertEquals("bidList/update", test);


    }

    @Test
    void update() throws Exception {

        when(bidListServiceMock.update(any(), anyInt())).thenReturn(bidListDtoUpdate);

        mockMvc.perform(post("/BidList/update/1")
                        .param("account", "accountTest1")
                        .param("type", "typeTest1")
                        .param("bidQuantity", "45"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete() throws Exception {

        doNothing().when(bidListServiceMock).delete(1);

        mockMvc.perform(get("/BidList/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}