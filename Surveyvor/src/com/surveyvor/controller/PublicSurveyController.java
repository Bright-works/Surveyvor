package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;

@Component("publicSurveyBean")
@Scope("request")
public class PublicSurveyController extends SurveyController {
		
	public PublicSurveyController() {
		
	}
	


}
