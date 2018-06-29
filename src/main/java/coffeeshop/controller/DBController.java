package coffeeshop.controller;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DBController {

	@Autowired
	DataSource datasource;
	
	@GetMapping("/initDB")
	private void initDataBase() {
		EncodedResource script = new EncodedResource(new ClassPathResource("init_database.sql"));
		try {
			ScriptUtils.executeSqlScript(datasource.getConnection(), script);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
