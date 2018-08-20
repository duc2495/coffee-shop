package coffeeshop.service;

import java.util.Date;

import coffeeshop.resource.dashboard.DashboardResource;

public interface DashboardService {
	public DashboardResource getDashboardResource(Date dayFrom, Date dayTo);
}
