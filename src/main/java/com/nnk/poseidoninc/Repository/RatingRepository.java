package com.nnk.poseidoninc.Repository;

import com.nnk.poseidoninc.Model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {
}
