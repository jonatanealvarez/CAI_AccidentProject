package com.example.demo.repository.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Accident;

import java.util.Collection;

@Repository
public interface IAccidentRepositoryMongo extends MongoRepository<Accident, String> {

    @Query("{ '@timestamp' : { $gt: ?0, $lt: ?1 } }")
    Collection<Accident> findAccidentByDateBetween(String pFrom,String pTo);

    float  countDistinctByStartLocationAndEndLocation();
}
