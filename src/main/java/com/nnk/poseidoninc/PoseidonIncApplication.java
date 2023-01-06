package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Service.Implementation.BidListListServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    private BidListListServiceImpl bidListListService;
    private TradeServiceImpl tradeService;
    public PoseidonIncApplication(BidListListServiceImpl bidListListService, TradeServiceImpl tradeService) {
        this.bidListListService = bidListListService;
        this.tradeService = tradeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }



    @Override
    public void run(String... args)  {

        BidListDto bidListDto = new BidListDto(
                "accountTest",
                "typeTest",
                45d
        );
        bidListListService.create(bidListDto);

        TradeDto tradeDto = new TradeDto(
                "accountTest",
                "typeTest",
                33d
        );
        tradeService.create(tradeDto);



    }
}
