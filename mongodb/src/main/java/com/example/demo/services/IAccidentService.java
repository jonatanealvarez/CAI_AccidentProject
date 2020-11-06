package com.example.demo.services;

import java.util.Collection;

import com.example.demo.dto.*;
import com.example.demo.model.Accident;

public interface IAccidentService {



    // AggregateAccidentCombined getCommonReasonsCombined();

    Collection<AccidentDateBetween> findAccidentByDateBetween(String pFrom, String pTo);

    CommonReason getCommonReasons();

    Collection<AccidentInRadius> getAccidentsInRadius(float longitude, float latitude, float radius_km);

    //float countDistinctByStartLocationAndEndLocation();

    Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm);

    double getAverageDistanceInAccident();
}