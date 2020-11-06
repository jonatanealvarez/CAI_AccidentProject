package elasticsearch.accident.controller;
import elasticsearch.accident.dto.AccidentDateBetween;
import elasticsearch.accident.dto.AccidentInRadius;
import elasticsearch.accident.dto.CommonReason;
import elasticsearch.accident.dto.DangerousPoint;
import elasticsearch.accident.services.IAccidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class AccidentController {
    @Inject
    private IAccidentService accidentsService;

    public IAccidentService getAccidentsService() {
        return this.accidentsService;
    }

    @GetMapping(value = "/api/accidentByDateBetween")
    @ResponseBody
    public ResponseEntity<?> findAccidentByDateBetween(@RequestParam String pFrom, @RequestParam String pTo) {

        ResponseEntity<?> response = null;
        Collection<AccidentDateBetween> result = new ArrayList<AccidentDateBetween>();

        result.addAll(this.getAccidentsService().findAccidentByDateBetween(pFrom,pTo));

        response = ResponseEntity.ok(result);

        return response;
    }

    @GetMapping(value = "/api/commonReasons")
    public ResponseEntity<?> getCommonReasons() {

        ResponseEntity<?> response = null;
        CommonReason result = new CommonReason();

        result = this.getAccidentsService().getCommonReasons();

        response = ResponseEntity.ok(result);

        return response;
    }

    @GetMapping(value = "/api/getAccidentsInPointAndRadius")
    @ResponseBody
    public ResponseEntity<?> getAccidentsInRadius
            (@RequestParam float longitude,@RequestParam float latitude, float radius_KM) {

        ResponseEntity<?> response = null;
        Collection<AccidentInRadius> result = new ArrayList<AccidentInRadius>();

        result.addAll(this.getAccidentsService().getAccidentsInRadius(longitude,latitude,radius_KM));

        response = ResponseEntity.ok(result);

        return response;
    }

    @GetMapping(value = "/api/averageDistanceInAccident")
    public ResponseEntity<?> getAverageDistanceInAccident() {

        ResponseEntity<?> response = null;
        double result = this.getAccidentsService().getAverageDistanceInAccident();

        response = ResponseEntity.ok(result);

        return response;
    }

    @GetMapping(value = "/api/DangerousPoints")
    @ResponseBody
    public ResponseEntity<?> TopDangerousPoints(@RequestParam float radiusInKm) {

        ResponseEntity<?> response = null;
        Collection<DangerousPoint> result = this.getAccidentsService().getTopDangerousPoints(radiusInKm);

        response = ResponseEntity.ok(result);

        return response;
    }

}
