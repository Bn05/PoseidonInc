package com.nnk.poseidoninc.Repository;

import com.nnk.poseidoninc.Model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<Bid,Integer> {
}
