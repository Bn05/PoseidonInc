package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Service.Implementation.BidListListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }

    @Autowired
    BidListListServiceImpl bidListListService;
    @Override
    public void run(String... args) throws Exception {

        BidListDto bidListDto = new BidListDto(
                "accountTest",
                "typeTest",
                45d
        );

        bidListListService.addBid(bidListDto);

    }
}
