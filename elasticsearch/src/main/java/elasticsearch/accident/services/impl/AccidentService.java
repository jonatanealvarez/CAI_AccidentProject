package elasticsearch.accident.services.impl;

import elasticsearch.accident.dto.AccidentDateBetween;
import elasticsearch.accident.dto.AccidentInRadius;
import elasticsearch.accident.dto.CommonReason;
import elasticsearch.accident.dto.DangerousPoint;
import elasticsearch.accident.repository.impl.AccidentRepositoryElasticSearch;
import elasticsearch.accident.services.IAccidentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccidentService implements IAccidentService {

    @Inject
    public AccidentRepositoryElasticSearch accidentRepository;



    @Override
    public Collection<AccidentDateBetween> findAccidentByDateBetween(String pFrom, String pTo) {
        Collection<AccidentDateBetween> result = new ArrayList<AccidentDateBetween>();

        this.accidentRepository.findAccidentByDateBetween(pFrom,pTo).stream().map(a -> new AccidentDateBetween(a))
                .collect(Collectors.toCollection(() ->result));

        return result;

    }

    @Override
    public CommonReason getCommonReasons() {
        CommonReason result = new CommonReason();
        result = accidentRepository.getCommonReasons();
        return result;
    }

    @Override
    public Collection<AccidentInRadius> getAccidentsInRadius(float longitude, float latitude, float radius_km) {
        Collection<AccidentInRadius> result = new ArrayList<AccidentInRadius>();

       // result = this.accidentRepository.getAccidentsInPointAndRadius(longitude,latitude,radius_km);

        this.accidentRepository.getAccidentsInPointAndRadius(longitude,latitude,radius_km).stream().map(a -> new AccidentInRadius(a))
                .collect(Collectors.toCollection(() ->result));

        return result;
    }

    @Override
    public double getAverageDistanceInAccident() {
        return this.accidentRepository.getAverageDistance();
    }

    @Override
    public Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm) {
        return this.accidentRepository.getTopDangerousPoints(radiusInKm);
    }
}
