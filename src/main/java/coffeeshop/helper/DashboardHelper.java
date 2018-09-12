package coffeeshop.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import coffeeshop.model.dashboard.Dashboard;
import coffeeshop.resource.dashboard.DashboardResource;

@Component
public class DashboardHelper {
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * model→resource変換
	 * 
	 * @param models
	 * @return
	 */
	public DashboardResource createDashboardResource(Dashboard model) {
		// 商品情報をマッピング
		return modelMapper.map(model, DashboardResource.class);

	}
}
