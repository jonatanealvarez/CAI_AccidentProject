package com.example.demo.repository.impl;

import com.example.demo.dto.*;
import com.example.demo.model.Accident;
import com.example.demo.model.ModeReason;
import com.example.demo.repository.IAccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import  org.springframework.data.mongodb.core.aggregation.*;

import javax.inject.Inject;
import java.util.*;


import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class CAccidentRepository implements IAccidentRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Inject
    IAccidentRepositoryMongo IAccidentRepositoryMongo;

    @Override
    public Collection<Accident> findAccidentByDateBetween(String from, String to){
        return IAccidentRepositoryMongo.findAccidentByDateBetween(from,to);
    }

    public AggregateAccidentCombined getCommonReasonsCombined() {

        GroupOperation groupByTemperature = group("Temperature(F)","Visibility(mi)").count().as("count");
        SortOperation sortByTemperatureDesc = sort(Sort.by(Sort.Direction.DESC,"count"));
        LimitOperation limitToOnlyFirstDoc = limit(1);

        Aggregation aggregation = newAggregation(groupByTemperature,sortByTemperatureDesc,limitToOnlyFirstDoc);
        AggregationResults<AggregateAccidentCombined> listResult = mongoTemplate.aggregate(aggregation,"accident", AggregateAccidentCombined.class);
        AggregateAccidentCombined result = listResult.getUniqueMappedResult();
        return result;
    }

    @Override
    public CommonReason getCommonReasons() {


        CommonReason commonReason = new CommonReason();

        commonReason.setTemperature_F(getModeReason("Temperature(F)"));
        commonReason.setHumidity_AVG(getModeReason("Humidity(%)"));
        commonReason.setPressure_IN(getModeReason("Pressure(in)"));
        commonReason.setVisibility_MI(getModeReason("Visibility(mi)"));
        commonReason.setWind_Speed_MPH(getModeReason("Wind_Speed(mph)"));
        commonReason.setWind_Direction(getModeReason("Wind_Direction"));

        return commonReason;

    }

    private ModeReason getModeReason(String reason)
    {
        MatchOperation filterNullValues = match(new Criteria(reason).ne(null));
        GroupOperation groupByTemperature = group(reason).count().as("count");
        SortOperation sortByTemperatureDesc = sort(Sort.by(Sort.Direction.DESC,"count"));
        LimitOperation limitToOnlyFirstDoc = limit(1);

        Aggregation aggregation = newAggregation(filterNullValues,groupByTemperature,sortByTemperatureDesc,limitToOnlyFirstDoc);

        AggregationResults<ModeReason> listResult = mongoTemplate.aggregate(aggregation,"accident", ModeReason.class);

        return listResult.getUniqueMappedResult();
    }


    @Override
    public Collection<Accident> getAccidentsInRadius(float longitude, float latitude, float radiusInKm) {

        Collection<Accident> listAccidents = new ArrayList<Accident>();

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radiusInKm, Metrics.KILOMETERS);
        Circle circle = new Circle(point, distance);

        Criteria geoCriteria = Criteria.where("start_location").withinSphere(circle);

        Query query = Query.query(geoCriteria);

        listAccidents = mongoTemplate.find(query, Accident.class);

        return listAccidents;
    }

    @Override
    public Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm) {

        int top =5;
        ArrayList<DangerousPoint> dangerousPointsList = new ArrayList<DangerousPoint>();

        MatchOperation filterNullValues = match(new Criteria("start_location").ne(null));
        LimitOperation limitToOnlyFirstDoc = limit(100);
        GroupOperation groupOperation = group("start_location").first("start_location").as("start_location");
        Aggregation aggregation = newAggregation(filterNullValues,limitToOnlyFirstDoc,groupOperation).withOptions(newAggregationOptions().allowDiskUse(true).build());
        AggregationResults<DangerousPoint> listDangerousPoints = mongoTemplate.aggregate(aggregation, "accident", DangerousPoint.class);


        for (DangerousPoint dp:listDangerousPoints) {

            dp.setAmount(getCountPointInRadius(radiusInKm,dp.getStartLocation()));
            dangerousPointsList.add(dp);
            dangerousPointsList.removeIf(
                    vDP -> vDP.equals(dangerousPointsList.stream().
                            min(Comparator.comparing(DangerousPoint::getAmount)).get())
                    && dangerousPointsList.size()>top);
        }

        dangerousPointsList.sort(Comparator.comparing(DangerousPoint::getAmount).reversed());
        return dangerousPointsList;

    }

    @Override
    public double getAverageDistance(){

        double distance = 0;


        Criteria geoCriteria = Criteria.where("end_location.coordinates.0").ne(0);
        geoCriteria.where("end_location").ne(null);
        Query query = Query.query(geoCriteria);
        //query.limit(100);
        Collection<DistanceBetweenPoints> listDistanceBetweenPoints= mongoTemplate.find(query,DistanceBetweenPoints.class,"accident");

       distance=listDistanceBetweenPoints.parallelStream().map(
                    dBP->{
                        dBP.setDistance(distanceInKM(dBP.getStartLocation(), dBP.getEndLocation()));
                        return dBP;
                    })
                    .mapToDouble(DistanceBetweenPoints::getDistance)
                    .average()
                    .orElse(Double.valueOf(0));

        return distance;
    }



    private Integer getCountPointInRadius(float radiusInKm, Point point)
    {
        Distance distance = new Distance(radiusInKm, Metrics.KILOMETERS);
        Circle circle = new Circle(point, distance);
        Criteria geoCriteria = Criteria.where("start_location").withinSphere(circle);
        Query query = Query.query(geoCriteria);
        return Math.toIntExact(mongoTemplate.count(query, Accident.class));
    }

    private double distanceInKM(Point start_location, Point end_location) {

        double dist =0;

        try {
            if ((start_location.getY() == end_location.getY())
                    && (start_location.getX() == end_location.getX())
            ) {
                return dist;
            } else {
                double theta = start_location.getX() - end_location.getX();
                dist = Math.sin(Math.toRadians(start_location.getY()))
                        * Math.sin(Math.toRadians(end_location.getY()))
                        + Math.cos(Math.toRadians(start_location.getY()))
                        * Math.cos(Math.toRadians(end_location.getY()))
                        * Math.cos(Math.toRadians(theta));
                dist = Math.acos(dist);
                dist = Math.toDegrees(dist);
                dist = dist * 60 * 1.1515;
                dist = dist * 1.609344;

            }
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }

        return (dist);
    }
}
