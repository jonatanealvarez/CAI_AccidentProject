package com.example.demo.repository;

import com.example.demo.dto.CommonReason;
import com.example.demo.dto.DangerousPoint;
import com.example.demo.model.Accident;

import java.util.Collection;

public interface IAccidentRepository {

    Collection<Accident> findAccidentByDateBetween(String from, String to);
    CommonReason getCommonReasons();
    Collection<Accident> getAccidentsInRadius(float longitude, float latitude, float radiusInKm);
    Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm);
    double getAverageDistance();
}
