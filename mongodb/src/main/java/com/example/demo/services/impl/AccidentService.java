package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.example.demo.dto.*;
import com.example.demo.repository.IAccidentRepository;
import com.example.demo.repository.impl.CAccidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.impl.IAccidentRepositoryMongo;
import com.example.demo.services.IAccidentService;

@Service
@Transactional
public class AccidentService implements IAccidentService {

	@Inject
	public IAccidentRepository IAccidentRepository;

	/*
	@Override
	public AggregateAccidentCombined getCommonReasonsCombined() {
		AggregateAccidentCombined result = new AggregateAccidentCombined();
		result = getAccidentRepository().getCommonReasonsCombined();
		return result;
	}

	 */

	@Override
	public Collection<AccidentDateBetween> findAccidentByDateBetween(String pFrom, String pTo) {
		Collection<AccidentDateBetween> result = new ArrayList<AccidentDateBetween>();


		this.getAccidentRepository().findAccidentByDateBetween(pFrom,pTo).stream().map(a -> new AccidentDateBetween(a))
				.collect(Collectors.toCollection(() ->result));

		return result;
	}

	@Override
	public CommonReason getCommonReasons() {
		CommonReason result = new CommonReason();
		result = getAccidentRepository().getCommonReasons();
		return result;
	}

	@Override
	public Collection<AccidentInRadius> getAccidentsInRadius(float longitude, float latitude, float radius_km) {
		Collection<AccidentInRadius> result = new ArrayList<AccidentInRadius>();

	//	result = this.getcAccidentRepository().getAccidentsInRadius(longitude,latitude,radius_km);

		this.getAccidentRepository().getAccidentsInRadius(longitude,latitude,radius_km).stream().map(a -> new AccidentInRadius(a))
				.collect(Collectors.toCollection(() ->result));

		return result;
	}

	/*
	@Override
	public float countDistinctByStartLocationAndEndLocation() {
		return this.getAccidentRepository().countDistinctByStartLocationAndEndLocation();
	}

	 */

	@Override
	public Collection<DangerousPoint> getTopDangerousPoints(float radiusInKm) {
		return this.getAccidentRepository().getTopDangerousPoints(radiusInKm);
	}

	@Override
	public double getAverageDistanceInAccident() {
		return this.getAccidentRepository().getAverageDistance();
	}

	public IAccidentRepository getAccidentRepository() {
		return this.IAccidentRepository;
	}

	public void setAccidentRepository(IAccidentRepository aRepository) {
		this.IAccidentRepository = aRepository;
	}
}
