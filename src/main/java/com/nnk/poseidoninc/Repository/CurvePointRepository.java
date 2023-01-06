package com.nnk.poseidoninc.Repository;

import com.nnk.poseidoninc.Model.CurvePoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends CrudRepository<CurvePoint,Integer> {
}
