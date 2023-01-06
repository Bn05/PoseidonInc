package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.BidListDto;
import com.nnk.poseidoninc.Model.Dto.CurvePointDto;
import com.nnk.poseidoninc.Model.Dto.TradeDto;
import com.nnk.poseidoninc.Service.Implementation.BidListListServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import com.nnk.poseidoninc.Service.Interface.ICurvePointService;
import com.nnk.poseidoninc.Service.Interface.ITradeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    private BidListListServiceImpl bidListListService;
    private ITradeService tradeService;
    private ICurvePointService curvePointService;

    public PoseidonIncApplication(BidListListServiceImpl bidListListService, TradeServiceImpl tradeService, ICurvePointService curvePointService) {
        this.bidListListService = bidListListService;
        this.tradeService = tradeService;
        this.curvePointService = curvePointService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PoseidonIncApplication.class, args);
    }


    @Override
    public void run(String... args) {

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

        CurvePointDto curvePointDto = new CurvePointDto(
                25,
                27
        );

        curvePointService.create(curvePointDto);


    }
}
