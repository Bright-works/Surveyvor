package com.surveyvor.manager;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.surveyvor.model.Answer;
import com.surveyvor.model.Comment;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

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
		User creator = survey.getCreator();
		List<Survey> ownedSurveys = creator.getOwnedSurveys();
		ownedSurveys.add(survey);
		creator.setOwnedSurveys(ownedSurveys);
		em.merge(creator);
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
	public void removeCommentAllOfSurvey(Survey survey){
		em.createQuery("delete Comment c where c.survey.Id=:id").setParameter("id", survey.getId()).executeUpdate();
	}
	
	public List<Comment> getallCommentBySurvey(long id_survey){
		return em.createQuery("Select c from Comment c where c.survey.Id=:id_S",Comment.class)
				.setParameter("id_S",id_survey).getResultList();
	}

	public Collection<Answer> allAnswers(Survey survey) {
		TypedQuery<Answer> a = em.createQuery("SELECT DISTINCT a FROM Answer a WHERE a.question.survey.Id = :id",
				Answer.class);
		return a.setParameter("id", survey.getId()).getResultList();
	}

	public Collection<Survey> findJustEndedSurveys() {
		System.out.println("Checking if there are ended surveys");

		Calendar now = new GregorianCalendar();
		Calendar hourBefore = new GregorianCalendar();
		if (hourBefore.get(Calendar.HOUR_OF_DAY) < 1) {
			if (hourBefore.get(Calendar.DAY_OF_MONTH) < 2) {
				if (hourBefore.get(Calendar.MONTH) < 1) {
					hourBefore.roll(Calendar.YEAR, false);
				}
				hourBefore.roll(Calendar.MONTH, false);
			}
			hourBefore.roll(Calendar.DAY_OF_MONTH, false);
		}
		hourBefore.roll(Calendar.HOUR_OF_DAY, false);

		return em.createQuery("Select s From Survey s "
				+ "where UNIX_TIMESTAMP(endDate) >= UNIX_TIMESTAMP(:hourbefore) "
				+ "and UNIX_TIMESTAMP(endDate) <= UNIX_TIMESTAMP(now())",
						Survey.class)
				.setParameter("hourbefore", hourBefore).getResultList();
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
