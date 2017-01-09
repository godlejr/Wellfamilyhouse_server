package com.demand.server.ppg.dao;

import java.util.ArrayList;

import com.demand.server.ppg.dto.User;

public interface IDao {

	public User login_check(String email, String password);

	public void insert_result(int user_id, String aa, String sns, String psns, String ans, String hrv, String stress);
}
