package com.surveyvor.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.surveyvor.manager.PermissionManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.User;
import com.surveyvor.service.MailSender;

@Controller
@Component("loginBean")
@Scope("request")
@RequestMapping(value="/reset")
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user = new User();

	@Autowired
	private UserController login;

	@Autowired
	UserManager userManager;

	@Autowired
	private PermissionManager permissionManager;
	
	@Autowired
	private MailSender mailSender;
	private String verifPassword="";
	private String oldPassword="";
	private String newPassword="";
	private String email="";
	private String url="";
	public LoginController() {
	}
	
	//-------------------- Action et methodes -------------//
	public void checkUrl(){
		FacesContext context = FacesContext.getCurrentInstance();
		     Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
	        String projectId = paramMap.get("url");
	        url=projectId;
	        
	}
	public String connecter() throws ServletException, IOException{
		try{
			User authen=userManager.findByMail(user.getMail());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(user.getPassword(), authen.getPassword())){
				login.setUser(authen);
				login.setConneted(true);
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			    RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
			                .getRequestDispatcher("/j_spring_security_check");
			    dispatcher.forward((ServletRequest) context.getRequest(),
			                (ServletResponse) context.getResponse());
			    FacesContext.getCurrentInstance().responseComplete(); 
			}
			else{
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalid !",""));
			}
		}
		catch(NoResultException exp){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalid !",""));
		}
		return null;

	}
	

	public String logout() {
		user = new User();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		SecurityContextHolder.clearContext();
		return "/index.xhtml?faces-redirect=true";
	}

	public String register() throws ServletException, IOException {
		if (!this.verifPassword.equals(user.getPassword())) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Les mots de passe ne sont pas identiques !", ""));
		} else {
			try {
				User existed = userManager.findByMail(user.getMail());
				if (existed.getId() > 0) {
					FacesContext facesContext = FacesContext.getCurrentInstance();
					facesContext.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_FATAL, "L'adresse email est d�j� utilis�e !", ""));
					return "register.xhtml";
					
				}
			}
			catch(NoResultException expt){
				user.setAdmin("ROLE_USER");
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(user.getPassword()));
				userManager.add(this.user);
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Vous �tes bien inscrit!",""));	
			    /*try {
			        UserDetails userDetails = permissionManager.loadUserByUsername(user.getMail());
			        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, verifPassword, userDetails.getAuthorities());
			        SecurityContextHolder.getContext().setAuthentication(auth);
			        login.setConneted(true);
			        return "/user/acceuil.xhtml";
			        
			      } catch (Exception e) {
					  facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,e.getMessage(),""));	
					   
			      }*/
			}
		}
		return "login.xhtml?faces-redirect=true";
	}
	
	public String forgetPassword() throws UnknownHostException{
		if(login.verifierEmail(email)){
			User finded=userManager.findByMail(email);
			if(finded.getId()>0){
				email = String.copyValueOf(Hex.encode(email.getBytes()));
				String url;
				try {
					url = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/Surveyvor/url/reset/password/"+email;
					System.out.println(url);
					mailSender.sendResetPassword(finded.getMail(), url);
					email="";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "V�rifier le format d'email",""));
		}
		return "/login.xhtml?faces-redirect=true";
	}
	
	@RequestMapping(path = "/password/{param}", method = RequestMethod.GET)
	public ModelAndView reset(@PathVariable("param") String param){
		this.email=new String(Hex.decode(param),StandardCharsets.UTF_8);
		try{
			login.setUser(userManager.findByMail(email));
		}catch(Exception exception)
		{
			return new ModelAndView("index");
		}
			return new ModelAndView("resetPassword");
	} 
	/**
	 * Dans le cas d'un mot de passe oubli� 
	 * @return
	 */
	public String resetPassword(){
		if(user.getPassword().equals(verifPassword)){
			try{
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				login.getUser().setPassword(encoder.encode(user.getPassword()));
				userManager.update(login.getUser());
				}
			catch(Exception exp){
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur pendant la communication avec la BD!",""));	
			}
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Les deux mots de passe ne sont indentiques!",""));
		}
		return "index.xhtml?faces-redirect=true";
	}
	/**
	 * 
	 * 
	 * */
	public String resetMypassword(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(oldPassword, login.getUser().getPassword())){
			if(newPassword.equals(verifPassword)){
				login.getUser().setPassword(encoder.encode(newPassword));
				userManager.update(login.getUser());
			}
			else{
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les deux nouveaux mots de passe ne sont pas identiques!",""));
			
			}
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'ancien mot de passe est incorrect",""));
		
		}
		return "./edit.xhml?faces-redirect=true";
	}
	
	public void checkMail(AjaxBehaviorEvent e){
		if(userManager.checkMail(user.getMail())){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage("register:mail2", new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'adresse mail existe d�j�",""));
		}
	}
	
	//--------------------- getter and setters -------------//
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getVerifPassword() {
		return verifPassword;
	}

	public void setVerifPassword(String verifPassword) {
		this.verifPassword = verifPassword;
	}

	public UserController getLogin() {
		return login;
	}

	public void setLogin(UserController login) {
		this.login = login;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}



}
