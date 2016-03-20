package com.surveyvor.manager;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.surveyvor.model.Comment;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

@Service
@EnableTransactionManagement
@Transactional
public class UserManager// implements IUser{
{

	@PersistenceContext(unitName = "myBase")
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public UserManager() {
	}

	@PostConstruct
	public void init() {
		System.out.println("INIT UserManager = " + this);
	}

	@PreDestroy
	public void close() {
		System.out.println("CLOSE UserManager = " + this);

	}

	public void add(User user) {
		em.persist(user);
	}

	public void update(User user) {
		em.merge(user);
	}

	public Collection<User> findUsers() {
		return em.createQuery("Select u From User u", User.class).getResultList();
	}

	public User findUser(long id) {
		return em.find(User.class, id);
	}

	public void removeUser(long id) {
		User u = em.find(User.class, id);
		em.remove(u);
	}

	public User findByMail(String mail) {
		TypedQuery<User> p = em.createQuery("SELECT u FROM User u WHERE u.mail = :mail", User.class);
		return p.setParameter("mail", mail).getSingleResult();

	}

	public Collection<Survey> allSurveysCreated(long id) {

		TypedQuery<Survey> p = em.createQuery("SELECT s FROM Survey s WHERE s.creator.Id_User = :id", Survey.class);
		return p.setParameter("id", id).getResultList();
	}

	/*
	 * public Collection<Survey> allSurveysAnswered(User user) {
	 * 
	 * TypedQuery<Survey> p = em.createQuery(
	 * "SELECT s FROM Survey s WHERE :user MEMBER OF s.answerers",
	 * Survey.class); return p.setParameter("user", user).getResultList(); }
	 */

	public Collection<Survey> allSurveysAnswered(User user) {

		TypedQuery<Survey> p = em
				.createQuery("SELECT DISTINCT a.question.survey FROM Answer a WHERE a.answerer.id = :id", Survey.class);
		return p.setParameter("id", user.getId()).getResultList();
	}

	public Collection<Survey> allSurveysInvited(User user) {

		TypedQuery<Survey> p = em.createQuery("SELECT s FROM Survey s WHERE :mail MEMBER OF s.diffusion", Survey.class);
		return p.setParameter("mail", user.getMail()).getResultList();
	}
	
	public void addComment(Comment comment) {
		em.persist(comment);
	}

}
