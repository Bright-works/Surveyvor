package com.surveyvor.manager;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.surveyvor.model.Answer;
import com.surveyvor.model.Comment;
import com.surveyvor.model.Survey;

@Service
@EnableTransactionManagement
@Transactional
public class SurveyManager {

	@Autowired
	@PersistenceContext(unitName = "myBase")
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public SurveyManager() {
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT SurveyManager = " + this);
	}

	@PreDestroy
	public void close() {
		System.out.println("CLOSE SurveyManager = " + this);
	}

	public void addSurvey(Survey survey) {
		em.persist(survey);
	}

	public void updateSurvey(Survey survey) {
		em.merge(survey);
	}
	
	public Collection<Survey> findSurveys() {
		return em.createQuery("Select s From Survey s", Survey.class).getResultList();
	}

	public Collection<Survey> findPrivateSurveys() {
		return em.createQuery("Select s From Survey s where s.parametres.privateSurvey = true", Survey.class)
				.getResultList();
	}

	public Collection<Survey> findPublicSurveys() {
		return em.createQuery("Select s From Survey s where s.parametres.privateSurvey = false", Survey.class)
				.getResultList();
	}
	
	public void commentSurvey(Comment comment){
		em.persist(comment);
	}
	
	
	public List<Comment> getallCommentBySurvey(long id_survey){
		return em.createQuery("Select c from Comment c where c.survey.Id=:id_S",Comment.class)
				.setParameter("id_S",id_survey).getResultList();
	}
	public Survey findSurvey(long id) {
		return em.find(Survey.class, id);
	}

	public void removeSurvey(long id) {
		Survey survey = em.find(Survey.class, id);
		em.remove(survey);
	}
	
	public void repondreSondage(Answer answer){
		em.persist(answer);
	}

}
