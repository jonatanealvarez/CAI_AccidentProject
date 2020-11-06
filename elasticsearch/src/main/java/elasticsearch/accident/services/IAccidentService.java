package elasticsearch.accident.services;

import elasticsearch.accident.dto.AccidentDateBetween;
import elasticsearch.accident.dto.AccidentInRadius;
import elasticsearch.accident.dto.CommonReason;
import elasticsearch.accident.dto.DangerousPoint;

import java.util.Collection;

public interface IAccidentService {


    public Collection<AccidentDateBetween> findAccidentByDateBetween(String pFrom, String pTo);

    public CommonReason getCommonReasons();

    Collection<AccidentInRadius> getAccidentsInRadius(float longitude, float latitude, float radius_km);

    Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm);

    double getAverageDistanceInAccident();
}
