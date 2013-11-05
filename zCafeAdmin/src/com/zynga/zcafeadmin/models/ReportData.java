package com.zynga.zcafeadmin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportData {
	private int totalCount;
	private String[] names;
	private int[] counts;
	
	public ReportData(int size){
		names = new String[size];
		counts = new int[size];
		totalCount = 0;
	}
	
	
	public static ReportData parseReportData(JSONObject obj){
		ReportData data = null;
		if(obj == null){
			return null;
		}
		try{
			JSONArray items = obj.getJSONArray("items");
			data = new ReportData(items.length());
			if(obj.has("totalCount")){
				data.setTotalCount(obj.getInt("totalCount"));
			}
			for(int i = 0; i < items.length(); i++){
				JSONObject item = items.getJSONObject(i);
				data.addName(i, item.getString("name"));
				data.addCount(i, item.getInt("count"));
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return data;
		
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public String[] getNames() {
		return names;
	}


	public void setNames(String[] names) {
		this.names = names;
	}
	
	public void addName(int index, String value) {
		this.names[index] = value;
	}


	public int[] getCounts() {
		return counts;
	}


	public void setCounts(int[] counts) {
		this.counts = counts;
	}
	
	public void addCount(int index, int value) {
		this.counts[index] = value;
	}
}
