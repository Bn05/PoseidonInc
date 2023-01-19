package com.nnk.poseidoninc.Repository;

import com.nnk.poseidoninc.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findUserByUserName(String userName);
}
