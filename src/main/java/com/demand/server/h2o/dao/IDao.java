package com.demand.server.h2o.dao;

import java.util.ArrayList;

import com.demand.server.h2o.dto.Test;
import com.demand.server.h2o.dto.User;

public interface IDao {

	ArrayList<User> login_check(String email, String password);
	ArrayList<Test> show_list();
	
}
