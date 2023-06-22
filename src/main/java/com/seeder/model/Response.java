package com.seeder.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	public boolean sucesss;
	public Object data;
	public Timestamp time;
	public String error;

	public Response(boolean sucesss, Object data, String error, Timestamp time) {
		this.sucesss = sucesss;
		this.data = data;
		this.time = time;
		this.error = error;
	}

	public boolean isSucesss() {
		return sucesss;
	}

	public void setSucesss(boolean sucesss) {
		this.sucesss = sucesss;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
