package elasticsearch.accident.repository;

import elasticsearch.accident.dto.DangerousPoint;
import elasticsearch.accident.model.Accident;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.stream.Stream;


public interface IAccidentRepositoryElasticSearch extends ElasticsearchRepository<Accident, String> {

    Collection<Accident> findAccidentsByTimestampBetween(String from, String to);

    @Query("{\"bool\": {\"must\": {\"match_all\": {}},\"filter\": {\"geo_distance\": {\"distance\": \"?2km\",\"start_location\": {\"lat\": ?1,\"lon\": ?0}}}}}")
    Collection<Accident> getCountPointInRadius(double lat, double lon, double radius);

    @Query( "{\"exists\": {\"field\": \"end_location\"}}")
    Stream<Accident> getEndLocationNotNull();




}
