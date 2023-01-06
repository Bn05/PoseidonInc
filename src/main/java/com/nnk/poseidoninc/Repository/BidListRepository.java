package com.nnk.poseidoninc.Repository;

import com.nnk.poseidoninc.Model.BidList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends CrudRepository<BidList,Integer> {
}
