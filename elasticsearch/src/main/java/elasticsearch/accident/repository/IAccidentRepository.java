package elasticsearch.accident.repository;

import elasticsearch.accident.dto.CommonReason;
import elasticsearch.accident.dto.DangerousPoint;
import elasticsearch.accident.model.Accident;

import java.util.Collection;

public interface IAccidentRepository {
    Collection<Accident> findAccidentByDateBetween(String from, String to);
    CommonReason getCommonReasons();
    Collection<Accident> getAccidentsInPointAndRadius(double longitude, double latitude, double radius_km);
    Collection<DangerousPoint> getTopDangerousPoints(double radiusInKm);
    double getAverageDistance();

}
