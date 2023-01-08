package com.nnk.poseidoninc;

import com.nnk.poseidoninc.Model.Dto.*;
import com.nnk.poseidoninc.Model.RuleName;
import com.nnk.poseidoninc.Service.Implementation.BidListListServiceImpl;
import com.nnk.poseidoninc.Service.Implementation.TradeServiceImpl;
import com.nnk.poseidoninc.Service.Interface.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoseidonIncApplication implements CommandLineRunner {

    private IBidListService bidListListService;
    private ITradeService tradeService;
    private ICurvePointService curvePointService;
    private IRatingService ratingService;
    private IRuleNameService ruleNameService;

    public PoseidonIncApplication(BidListListServiceImpl bidListListService, TradeServiceImpl tradeService, ICurvePointService curvePointService, IRatingService ratingService, IRuleNameService ruleNameService) {
        this.bidListListService = bidListListService;
        this.tradeService = tradeService;
        this.curvePointService = curvePointService;
        this.ratingService = ratingService;
        this.ruleNameService = ruleNameService;
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

        RatingDto ratingDto = new RatingDto(
                "moodysTest",
                "sandPRTEst",
                "fitchTest",
                2
        );
        ratingService.create(ratingDto);

        RuleNameDto ruleNameDto = new RuleNameDto(
                "nameTest",
                "descriptionTest",
                "JsonTest",
                "templateTest",
                "sqlStrTest",
                "sqlPartTest"
        );
        ruleNameService.create(ruleNameDto);


    }
}
