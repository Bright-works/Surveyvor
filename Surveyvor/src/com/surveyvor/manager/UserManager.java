package com.surveyvor.manager;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.surveyvor.model.Comment;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

/**
 * 
 * @author Brightworks
 * all methods related to a user
 *
 */

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

	/**
	 * add user to databases
	 * @param user
	 */
	public void add(User user) {
		em.persist(user);
	}

	/**
	 * update user in databases
	 * @param user
	 */
	public void update(User user) {
		em.merge(user);
	}

	/**
	 * find all users in databases
	 * @return
	 */
	public Collection<User> findUsers() {
		return em.createQuery("Select u From User u", User.class).getResultList();
	}

	/**
	 * find a specific user in databases (by the id)
	 * @param id
	 * @return
	 */
	public User findUser(long id) {
		return em.find(User.class, id);
	}

	/**
	 * remove user from databases
	 * @param id
	 */
	public void removeUser(long id) {
		User u = em.find(User.class, id);
		em.remove(u);
	}

	/**
	 * find a specific user in databases (by the mail)
	 * @param mail
	 * @return
	 */
	public User findByMail(String mail) {
		TypedQuery<User> p = em.createQuery("SELECT u FROM User u WHERE u.mail = :mail", User.class);
		return p.setParameter("mail", mail).getSingleResult();

	}

	/**
	 * find all surveys created by a specific user
	 * @param id
	 * @return
	 */
	public Collection<Survey> allSurveysCreated(long id) {

		TypedQuery<Survey> p = em.createQuery("SELECT s FROM Survey s WHERE s.creator.Id_User = :id", Survey.class);

		return p.setParameter("id", id).getResultList();
	}

	/**
	 * find all surveys answered by a specific user
	 * @param user
	 * @return
	 */
	
	public Collection<Survey> allSurveysAnswered(User user) {

		TypedQuery<Survey> p = em
				.createQuery("SELECT DISTINCT a.question.survey FROM Answer a WHERE a.answerer.id = :id", Survey.class);
		return p.setParameter("id", user.getId()).getResultList();
	}

	/**
	 * find all surveys invited by a specific user
	 * @param user
	 * @return
	 */
	
	public Collection<Survey> allSurveysInvited(User user) {

		TypedQuery<Survey> p = em.createQuery("SELECT s FROM Survey s WHERE :mail MEMBER OF s.diffusion", Survey.class);
		return p.setParameter("mail", user.getMail()).getResultList();
	}
	
	/**
	 * add one comment
	 * @param comment
	 */
	public void addComment(Comment comment) {
		em.persist(comment);
	}

}
