package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import com.example.demo.dto.*;
import com.example.demo.model.Accident;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.services.IAccidentService;

@RestController
public class AccidentController {

	@Inject
	private IAccidentService accidentsService;



	@GetMapping(value = "/api/accidentByDateBetween")
	@ResponseBody
	public ResponseEntity<?> findAccidentByDateBetween(@RequestParam String pFrom,@RequestParam String pTo) {

		ResponseEntity<?> response = null;
		Collection<AccidentDateBetween> result = new ArrayList<AccidentDateBetween>();

		result.addAll(this.getAccidentsService().findAccidentByDateBetween(pFrom,pTo));

		response = ResponseEntity.ok(result);

		return response;
	}
/*
	@GetMapping(value = "/api/commonReasonsCombined")
	public ResponseEntity<?> getCommonReasonsCombined() {

		ResponseEntity<?> response = null;
		AggregateAccidentCombined result = new AggregateAccidentCombined();

		result = this.getAccidentsService().getCommonReasonsCombined();

		response = ResponseEntity.ok(result);

		return response;
	}

 */

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

	/*
	@GetMapping(value = "/api/countDistinctByStartLocationAndEndLocation")
	public ResponseEntity<?> countDistinctByStartLocationAndEndLocation() {

		ResponseEntity<?> response = null;
		float result = this.getAccidentsService().countDistinctByStartLocationAndEndLocation();


		response = ResponseEntity.ok(result);

		return response;
	}

	 */

	@GetMapping(value = "/api/DangerousPoints")
	@ResponseBody
	public ResponseEntity<?> TopDangerousPoints(@RequestParam float radiusInKm) {

		ResponseEntity<?> response = null;
		Collection<DangerousPoint> result = this.getAccidentsService().getTopDangerousPoints(radiusInKm);

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

	public IAccidentService getAccidentsService() {
		return this.accidentsService;
	}

}
