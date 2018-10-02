package com.example.denis.chat.Gson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ConverterJson 
{
	public RequestContext fromJsonServer(String json)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		RequestContext regStr = gson.fromJson(json, RequestContext.class);
		return regStr;
	}

	public String toGsonClient(RequestContext reqTxt)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return  gson.toJson(reqTxt);
	}
	
	public AnswerContext fromJsonClient(String json)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		AnswerContext regStr = gson.fromJson(json, AnswerContext.class);
		return regStr;
	}

	public String toGsonServer(AnswerContext reqTxt)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return  gson.toJson(reqTxt);
	}
}