package com.nnk.poseidoninc.UT.ControllerAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidoninc.ControllerAPI.BidListControllerAPI;
import com.nnk.poseidoninc.Exception.NotFoundException;
import com.nnk.poseidoninc.Model.BidList;
import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BidListControllerAPITest {

    @InjectMocks
    BidListControllerAPI bidListControllerAPI;

    @MockBean
    BidListServiceImpl bidListServiceMock;

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
    void findAll() throws Exception {
        when(bidListServiceMock.findAll()).thenReturn(bidListDtoList);

        mockMvc.perform(get("/bidLists"))
                .andExpect(content().json(bidListDtoListJson))
                .andExpect(status().isOk());


    }

    @Test
    void createBid() throws Exception {
        when(bidListServiceMock.create(bidListDto1)).thenReturn(bidListDto1);


        mockMvc.perform(post("/bidList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto1Json))
                .andExpect(content().json(bidListDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void createBidBadRequest() throws Exception {
        when(bidListServiceMock.create(bidListDto1)).thenReturn(bidListDto1);

        mockMvc.perform(post("/bidList"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById() throws Exception {
        when(bidListServiceMock.findById(1)).thenReturn(bidListDto1);

        mockMvc.perform(get("/bidList")
                        .param("bidListId", "1"))
                .andExpect(content().json(bidListDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdBadRequest() throws Exception {

        mockMvc.perform(get("/bidList")
                        .param("idBidList", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotFoundException() throws Exception {
        when(bidListServiceMock.findById(1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/bidList")
                        .param("bidListId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateById() throws Exception {
        when(bidListServiceMock.update(bidListDto1, 1)).thenReturn(bidListDto1);

        mockMvc.perform(put("/bidList")
                        .param("bidListId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto1Json))
                .andExpect(content().json(bidListDto1Json))
                .andExpect(status().isOk());
    }

    @Test
    void updateByIdBadParam() throws Exception {
        when(bidListServiceMock.update(bidListDto1, 1)).thenReturn(bidListDto1);

        mockMvc.perform(put("/bidList")
                        .param("bidListId", "A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto1Json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateByIdBadBody() throws Exception {
        when(bidListServiceMock.update(bidListDto1, 1)).thenReturn(bidListDto1);


        mockMvc.perform(put("/bidList")
                        .param("bidListId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateByIdNotFound() throws Exception {
        when(bidListServiceMock.update(bidListDto1, 1)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/bidList")
                        .param("bidListId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidListDto1Json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(bidListServiceMock).delete(1);

        mockMvc.perform(delete("/bidList")
                        .param("bidListId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdBadRequest() throws Exception {
        doNothing().when(bidListServiceMock).delete(1);

        mockMvc.perform(delete("/bidList")
                        .param("bidListId", "A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(bidListServiceMock).delete(1);

        mockMvc.perform(delete("/bidList")
                        .param("bidListId", "1"))
                .andExpect(status().isNotFound());
    }
}