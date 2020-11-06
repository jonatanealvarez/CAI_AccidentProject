package elasticsearch.accident.repository.impl;


import elasticsearch.accident.dto.CommonReason;
import elasticsearch.accident.dto.DangerousPoint;
import elasticsearch.accident.model.ModeReason;
import elasticsearch.accident.model.Accident;
import elasticsearch.accident.repository.IAccidentRepository;
import elasticsearch.accident.repository.IAccidentRepositoryElasticSearch;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
public class AccidentRepositoryElasticSearch implements IAccidentRepository {

    @Autowired
    private final ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private final IAccidentRepositoryElasticSearch iAccidentRepositoryElasticSearch;

    public AccidentRepositoryElasticSearch(ElasticsearchRestTemplate elasticsearchTemplate, IAccidentRepositoryElasticSearch iAccidentRepositoryElasticSearch) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.iAccidentRepositoryElasticSearch = iAccidentRepositoryElasticSearch;
    }


    @Override
    public Collection<Accident> findAccidentByDateBetween(String from, String to) {
       return iAccidentRepositoryElasticSearch.findAccidentsByTimestampBetween(from,to);
    }



    public CommonReason getCommonReasons() {

        CommonReason commonReason = new CommonReason();

        commonReason.setTemperature_F(getModeReason("Temperature(F).keyword"));
        commonReason.setHumidity_AVG(getModeReason("Humidity(%).keyword"));
        commonReason.setPressure_IN(getModeReason("Pressure(in).keyword"));
        commonReason.setVisibility_MI(getModeReason("Visibility(mi).keyword"));
        commonReason.setWind_Speed_MPH(getModeReason("Wind_Speed(mph).keyword"));
        commonReason.setWind_Direction(getModeReason("Wind_Direction.keyword"));

        return commonReason;
    }



    /*
    @Override
    public Collection<Accidents> getAccidentsInPointAndRadius(double longitude, double latitude, double radius_km) {
       return iAccidentRepositoryElasticSearch.getCountPointInRadius(longitude,latitude,radius_km);
    }
    */

    @Override
    public Collection<Accident> getAccidentsInPointAndRadius(double lon, double lat, double pRadius) {

        try {
            GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("start_location").point(lat, lon).distance(pRadius, DistanceUnit.KILOMETERS);
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()).filter(geoDistanceQueryBuilder);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
            SearchHitsIterator<Accident> searchHits = elasticsearchTemplate.searchForStream(searchQuery, Accident.class, IndexCoordinates.of("accident"));
            List<Accident> result = new ArrayList();
            while(searchHits.hasNext()){
                result.add(searchHits.next().getContent());
            }
            return  result;
        } catch (Exception e) {
            return new ArrayList<Accident>();
        }
    }


    public Collection<DangerousPoint> getTopDangerousPoints(double radiusInKm) {

        int top = 5;


        AbstractAggregationBuilder aggregation = AggregationBuilders.terms("Severity").field("location.keyword").size(3000000);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).addAggregation(aggregation).build();

        SearchHits<Accident> searchHits = elasticsearchTemplate.search(searchQuery, Accident.class, IndexCoordinates.of("accident"));
        ParsedStringTerms buck = (ParsedStringTerms) searchHits.getAggregations().asMap().get("Severity");

        List<DangerousPoint> listDangerousPoints = new ArrayList<>();
        buck.getBuckets().forEach(aBucket -> {
            String[] points = aBucket.getKeyAsString().split(",");

            DangerousPoint dp = new DangerousPoint();
            dp.setStartLocation(new Point(Double.parseDouble(points[0]),Double.parseDouble(points[1])));
            listDangerousPoints.add(dp);
        });

        ArrayList<DangerousPoint> dangerousPointsList = new ArrayList<DangerousPoint>();


        for (DangerousPoint dp:listDangerousPoints) {

            dp.setAmount(
                    getAccidentsInPointAndRadius(dp.getStartLocation().getY(),
                    dp.getStartLocation().getX(),radiusInKm).size());

            dangerousPointsList.add(dp);
            dangerousPointsList.removeIf(
                    vDP -> vDP.equals(dangerousPointsList.stream().
                            min(Comparator.comparing(DangerousPoint::getAmount)).get())
                            && dangerousPointsList.size()>top);
        }


        dangerousPointsList.sort(Comparator.comparing(DangerousPoint::getAmount).reversed());
        return dangerousPointsList;
    }


    private Integer getCountPointInRadius(double radiusInKm, Point point)
    {
        GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("start_location").point(point.getX(),point.getY()).distance(radiusInKm, DistanceUnit.KILOMETERS);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()).filter(geoDistanceQueryBuilder);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        SearchHitsIterator<Accident> searchHits = elasticsearchTemplate.searchForStream(searchQuery, Accident.class, IndexCoordinates.of("accident"));
        List<Accident> result = new ArrayList();
        while(searchHits.hasNext()){
            result.add(searchHits.next().getContent());
        }
        return  result.size();
    }

    @Autowired
    public double getAverageDistance(){
        double distance;


        Stream<Accident> listDistanceBetweenPoints= iAccidentRepositoryElasticSearch.getEndLocationNotNull();

        distance=listDistanceBetweenPoints.mapToDouble(
                dBP->{
                    return distanceInKM(dBP.getStartLocation(), dBP.getEndLocation());
                })
                .average()
                .orElse(Double.valueOf(0));
        return distance;
    }


    private double distanceInKM(Point start_location, Point end_location) {
        if ((start_location.getY() == end_location.getY()) && (start_location.getX() == end_location.getX())) {
            return 0;
        } else {
            double theta = start_location.getY() - end_location.getY();
            double dist = Math.sin(Math.toRadians(start_location.getX()))
                    * Math.sin(Math.toRadians(end_location.getX()))
                    + Math.cos(Math.toRadians(start_location.getX()))
                    * Math.cos(Math.toRadians(end_location.getX()))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;

            return (dist);
        }

    }

    private ModeReason getModeReason(String reason)
    {

        AbstractAggregationBuilder aggregation = AggregationBuilders.terms("Severity").field(reason);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).addAggregation(aggregation).build();

        SearchHits<Accident> searchHits = elasticsearchTemplate.search(searchQuery, Accident.class, IndexCoordinates.of("accidents"));
        ParsedStringTerms buck = (ParsedStringTerms) searchHits.getAggregations().asMap().get("Severity");

        List<ModeReason> result = new ArrayList<>();
        buck.getBuckets().forEach(aBucket -> {
            result.add(new ModeReason(aBucket.getKeyAsString(),(double) aBucket.getDocCount()));
        });

        return result.get(0);
    }

}
