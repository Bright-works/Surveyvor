package com.surveyvor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.surveyvor.exception.GaleShapleyException;
import com.surveyvor.exception.QuotatException;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

/**
 * 
 * @author Sarah Boukris, David Sebban
 * Algorithme de répartion Gale-Shapley
 *
 */
public class GaleShapley implements IResultGeneratorStrategy<List<User>> {

	/**
	 * main function
	 * @return the result of repartition
	 * @throws QuotatException 
	 */
	@Override
	public Map<Long, List<User>> generateResult(Survey survey, List<Answer> answers) throws GaleShapleyException, QuotatException {

		if (answers == null || survey == null || survey.getEndDate().after(new Date()))
			throw new GaleShapleyException();

		Map<Long, List<User>> resultIdChoice = new HashMap<>();
		if (answers.isEmpty())
			return resultIdChoice;
			
		List<Choice> choices = answers.get(0).getChoices();

		if (choices == null || choices.isEmpty())
			throw new GaleShapleyException();
		
		int sum=0;
		for(int j=0; j<choices.size(); j++)
			sum = choices.get(j).getQuotat()+sum;
		if (sum < answers.size())
			throw new QuotatException("les quotats sont depassé");
			
		
		Map<User, List<Choice>> userPreferChoice = buidPreferUser(answers);
		Map<Choice, List<User>> choicePreferUser = buildPreferGroup2(choices, answers);

		Map<Choice, List<User>> resultChoice = buildResult(answers, choicePreferUser, userPreferChoice );
		
		for(Entry<Choice, List<User>> entry : resultChoice.entrySet()) {
			Choice cle = entry.getKey();
			List<User> valeur = entry.getValue();
			resultIdChoice.put(cle.getId(), valeur);
		}
		
		if (resultIdChoice.size() != choices.size())
			throw new GaleShapleyException("verifiez les réponses");
		
		return resultIdChoice;

	}

	/**
	 * 
	 * @param answers
	 * @param choicePreferUser
	 * @param userPreferChoice
	 * @return result of repartion (sous la forme) Map <Choice, List<User>>
	 */
	
	private Map<Choice, List<User>> buildResult(List<Answer> answers, Map<Choice, List<User>> choicePreferUser, Map<User, List<Choice>> userPreferChoice ){

		Map<Choice, List<User>> groupFormed = new HashMap<>();
		Map<User, List<Choice>> userProposed = new HashMap<>();
		List<User> userSingle = new ArrayList<>();
		
		for(Entry<Choice, List<User>> entry : choicePreferUser.entrySet()) {
			  Choice cle = entry.getKey();
			  groupFormed.put(cle, new ArrayList<User>());
		}
		
		for(int i = 0; i < answers.size(); i++)
		{
			userSingle.add(answers.get(i).getAnswerer());
			userProposed.put(userSingle.get(i), new ArrayList<Choice>());
		}
		
		while(!userSingle.isEmpty())
		{
			User userCurrent = userSingle.get(0);
			Choice choiceCurrent = recupChoicePrefNoProp(userCurrent,userProposed,userPreferChoice);
			userProposed.get(userCurrent).add(choiceCurrent);

			if (groupFormed.get(choiceCurrent) == null)
				groupFormed.put(choiceCurrent, new ArrayList<User>());
			
			if(groupFormed.get(choiceCurrent).size() < choiceCurrent.getQuotat())
			{
				groupFormed.get(choiceCurrent).add(userCurrent);
				userSingle.remove(userCurrent);
			}

			else
			{
				int indexLastPref = checkPref(userCurrent, groupFormed, choicePreferUser, choiceCurrent);
				if(indexLastPref < groupFormed.get(choiceCurrent).size())
				{
					User tmp = groupFormed.get(choiceCurrent).get(indexLastPref);
					groupFormed.get(choiceCurrent).set(indexLastPref, userCurrent);
					userSingle.remove(userCurrent);
					userSingle.add(tmp);
				}
			}
		}
		return groupFormed;
	}

	/**
	 * 
	 * @param userCurrent
	 * @param groupFormed
	 * @param choicePreferUser
	 * @param choiceCurrent
	 * @return vérifie si l'userCurrent est préféré a un autre utilisateur se trouvant dans la liste du choiceCurrent
	 * retourne la place de cet utilisateur
	 */
	private int checkPref(User userCurrent, Map<Choice, List<User>> groupFormed, Map<Choice, List<User>> choicePreferUser, Choice choiceCurrent )
	{
		//System.out.println("user Courant "+ userCurrent.getName());
		User userTmp = userCurrent.clone();
		List<User> usersGroupFormed = groupFormed.get(choiceCurrent);
		List<User> usersPreferedByChoice = choicePreferUser.get(choiceCurrent);

		int indexLastPref = usersGroupFormed.size();
		int indexPrefUserCurrent = getIndex(usersPreferedByChoice, userTmp);
		
		for (int i = 0; i < usersGroupFormed.size(); i++)
		{
			int indexPrefUserChosen = usersPreferedByChoice.indexOf(usersGroupFormed.get(i));
			
			if (indexPrefUserChosen > indexPrefUserCurrent  )
			{
				indexLastPref = i;
				userTmp = usersGroupFormed.get(indexLastPref);
				indexPrefUserCurrent = indexPrefUserChosen; 
			}
		}
		return indexLastPref;
	}

	/**
	 * 
	 * @param usersPreferedByChoice
	 * @param userCurrent
	 * @return copie dun objet dans la liste usp
	 */
	private int getIndex (List<User> usersPreferedByChoice, User userCurrent)
	{
		int indexPrefUserCurrent = usersPreferedByChoice.size();
		for (int j=0; j < usersPreferedByChoice.size(); j++)
		{
			if ((usersPreferedByChoice.get(j).getMail()).equals(userCurrent.getMail()))
			{
				indexPrefUserCurrent = j;
				break;
			}
		}
		return indexPrefUserCurrent;
	}
	
	/**
	 * 
	 * @param user
	 * @param userProposed
	 * @param userPreferChoice
	 * @return un choix qui ne se trouve pas dans userProposed,
	 * c'est-à-dire un choix qui n'a pas encore était demandé par le user
	 */
	private Choice recupChoicePrefNoProp(User user, Map<User, List<Choice>> userProposed, Map<User, List<Choice>> userPreferChoice )
	{		
		int i = 0;
		List <Choice> choices = userPreferChoice.get(user);
		Choice choice = choices.get(i);
		while (!userProposed.isEmpty() && userProposed.get(user).contains(choice) && (choices.get(i)!=null))
		{
			i ++;
			choice = choices.get(i);
		}		
		return choice;
	}

	/**
	 * 
	 * @param answers
	 * @return Map des preférence pour les utilisateur
	 */
	private Map<User, List<Choice>> buidPreferUser(List<Answer> answers)throws GaleShapleyException{

		if (answers== null || answers.isEmpty())
			throw new GaleShapleyException();
		
		Map<User, List<Choice>> userPreferChoice = new HashMap<User, List<Choice>>();

		for (int i= 0; i<answers.size(); i++)
		{
			if (answers.get(i).getChoices()==null || answers.get(i).getChoices().contains(null))
				throw new GaleShapleyException();
			
			if((answers.get(i).getChoices().size() < answers.get(i).getQuestion().getMinChoice()) || (answers.get(i).getChoices().size() > answers.get(i).getQuestion().getMaxChoice()))
				throw new GaleShapleyException();
			
			userPreferChoice.put(answers.get(i).getAnswerer(), answers.get(i).getChoices());
		}
		
		for(Entry<User, List<Choice>> entry : userPreferChoice.entrySet()) {
		    User cle = entry.getKey();
		    List<Choice> valeur = entry.getValue();
		   // System.out.println("pour le user : " + cle.getLastName());
		    //for (int i=0; i<valeur.size(); i++)
		    //	System.out.print("choix "+ valeur.get(i).getLabel());
		    //System.out.println();
		}
		
		if (userPreferChoice.size() != answers.size())
				throw new GaleShapleyException("revisez vos answer");

		return userPreferChoice;
	}

	/**
	 * 
	 * @param choices
	 * @param answers
	 * @return Map des préférances pour les choix 
	 * complétements aléatoirement
	 */
	private Map<Choice, List<User>> buildPreferGroup(List<Choice> choices, List<Answer> answers) {

		Random rand = new Random();
		List <User> users = new ArrayList<User>();
		for(int i = 0; i < answers.size(); i++)
			users.add(answers.get(i).getAnswerer());

		Map<Choice, List<User>> choicePreferUser = new HashMap<Choice, List<User>>();

		for (int i=0; i< choices.size(); i++)
		{
			List <User> usersClone = new ArrayList<User>();
			usersClone.addAll(users);

			List <User> usersChoice = new ArrayList<User>();

			while (!usersClone.isEmpty())
			{
				int alea = (int)(Math.random() * ((usersClone.size())));
				usersChoice.add(usersClone.get(alea));
				usersClone.remove(alea);
			}

			choicePreferUser.put(choices.get(i), usersChoice);
		}
		
		for(Entry<Choice, List<User>> entry : choicePreferUser.entrySet()) {
		    Choice cle = entry.getKey();
		    List<User> valeur = entry.getValue();
//		    System.out.println("préférence choix : " + cle.getLabel());
//		    for (int i=0; i<valeur.size(); i++)
//		    	System.out.println("user "+ valeur.get(i).getName());
		}

		return choicePreferUser;
	}
	
	/**
	 * 
	 * @param choices
	 * @param answers
	 * @return Map des préférances pour les choix 
	 * selon l'ordre des préférences des user
	 */
	private Map<Choice, List<User>> buildPreferGroup2(List<Choice> choices, List<Answer> answers) throws GaleShapleyException{
		if (choices == null ||choices.isEmpty() || answers == null ||answers.isEmpty())
			throw new GaleShapleyException();
		
		Map<Choice, List<User>> choicePreferUser = new HashMap<Choice, List<User>>();

		for (int i=0; i< choices.size(); i++)
		{
			List <User> usersChoice = buildPreferForOneGroup(choices.get(i), answers);
			choicePreferUser.put(choices.get(i), usersChoice);
		}
		
		for(Entry<Choice, List<User>> entry : choicePreferUser.entrySet()) {
		    Choice cle = entry.getKey();
		    List<User> valeur = entry.getValue();
//		    System.out.println("préférence choix : " + cle.getLabel());
//		    for (int i=0; i<valeur.size(); i++)
//		    	System.out.print("user "+ valeur.get(i).getName());
//		    System.out.println();
		}
		if (choicePreferUser.size() != choices.size())
			throw new GaleShapleyException();
		
		return choicePreferUser;
	}
	
	/**
	 * 
	 * @param choice
	 * @param answers
	 * @return ordre de préférences pour un choice
	 * @throws GaleShapleyException 
	 */
	private List<User> buildPreferForOneGroup(Choice choice, List<Answer> answers) throws GaleShapleyException {
		
		//System.out.println("choix : "+ choice.getLabel());
		
		if (choice==null || answers == null || answers.isEmpty())
			throw new GaleShapleyException();
		
		int maxChoice = answers.get(0).getQuestion().getMaxChoice();
		List<Answer> answersClone = new ArrayList<>();
		answersClone.addAll(answers);
		List <User> usersPrefer = new ArrayList<>();
				
		for(int i= 0; i< maxChoice && !answersClone.isEmpty(); i++)
		{
			List <User> users = new ArrayList<User>();
			for (int j=0; j < answersClone.size() && j>=0; j++)
			{
			//	System.out.println("choix de : "+ answersClone.get(j).getAnswerer().getName());
				if (answersClone.get(j).getChoices().get(i).equals(choice))
				{
					User user = answersClone.get(j).getAnswerer();
					users.add(user);
					//System.out.println("user : "+ user.getName());
					answersClone.remove(j);
					j--;
				}
			}
			while (!users.isEmpty())
			{
				int alea = (int)(Math.random() * ((users.size())));
				usersPrefer.add(users.get(alea));
				users.remove(alea);
			}
		}			
		
		for(int i=0; i < answersClone.size(); i++)
			usersPrefer.add(answersClone.get(i).getAnswerer());
			
		if (usersPrefer.size() != answers.size())
			throw new GaleShapleyException();
		
		return usersPrefer;
	}
	

}