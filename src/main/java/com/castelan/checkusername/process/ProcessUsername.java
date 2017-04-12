/*
 * ProcessUsername.java
 *
 * Created on April 2017
 */
package com.castelan.checkusername.process;

import com.castelan.checkusername.dao.FileUserAccess;
import com.castelan.checkusername.dto.Result;
import com.castelan.checkusername.exceptions.DaoException;
import com.castelan.checkusername.exceptions.RestrictedWordException;
import com.castelan.checkusername.exceptions.UsernameLengthException;
import com.castelan.checkusername.util.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the methods to process user names
 * @author Juan Castelan
 */
public class ProcessUsername {

    private static FileUserAccess fileUser;
    /**
     * Number of attempts to generate alternate user names
     */
    private static final int NUM_ATTEMPTS=3;

    public ProcessUsername() {
        fileUser = FileUserAccess.getInstance(); 
    }

    public Result checkUsername(String username) throws RestrictedWordException, UsernameLengthException {
        Result res = new Result();
        String[] resultArray;
        username = username.trim();
        
        //Input validation
        if(username!=null&&!username.isEmpty()&&
                username.length()<6) {
            throw new UsernameLengthException(username+" contains less than 6 characters");
        }
        //Restricted words validation
        if(containsRestrictedWord(username)) {
            throw new RestrictedWordException(username+" contains a restricted word");
        }
        //Looks for existing user name, make suggestions
        res.setExistingUsrname(findString(username));
        if(res.isExistingUsrname()) {
            Set resultSet=generateSuggestions(username, Constants.getInstance().getInteger("NUM_SUGESTIONS"));
            if(resultSet!=null&&resultSet.size()>0) {
                resultArray = (String []) resultSet.toArray(new String[resultSet.size()]);
                res.setSuggestions(resultArray);
            }
        }
        return res;
    }
    
   /**
    * Finds a string in the set returned by readFile
    * @param str
    * @return 
    */ 
    private boolean findString(String str) {
        SortedSet resultSet;
        try {
            resultSet = fileUser.readExistingUsers();
            if (resultSet.contains(str)) {
                return true;
            }
        } catch (DaoException ex) {
            Logger.getLogger(ProcessUsername.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
   /**
    * Finds a string contained in the set returned by readFile
    * @param str
    * @return 
    */ 
    private boolean containsRestrictedWord(String str) {
        if(str==null||str.isEmpty()) {
            return true;
        }
        try {
            SortedSet resultSet= fileUser.readRestrictedWords();
            Object[] restWords = resultSet.toArray();
            for(int i=0;i<restWords.length;i++) {
                if(str.toLowerCase().contains(String.valueOf(restWords[i]).toLowerCase())) {
                    return true;
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(ProcessUsername.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Add a restritect word to the file
     * @param word 
     */
    public void addRestrictedWord(String word) throws DaoException {
        fileUser.addRestrictedWord(word);
    }

    /**
     * Generate suggestions the number of time specified in NUM_ATTEMPTS
     * @param usr
     * @param numSugest
     * @return 
     */
    public SortedSet<String> generateSuggestions(String usr, int numSugest) {
        SortedSet resultSet = new TreeSet();
        int currentSizeSet=0;
        String tmpUsr=null;
        for(int j=0;j<NUM_ATTEMPTS;j++)   {
            for (int i = 0; i < (numSugest-currentSizeSet); i++) {
                int s = generateRandom(3);
                switch (s) {
                    case 0:
                        tmpUsr=generateUsrNumbersRandom(usr);
                        break;
                    case 1:
                        tmpUsr=generateUsrReplaceLetters(usr);
                        break;
                    case 2:
                        tmpUsr=generateUsrAddWord(usr);
                        break;
                    default:
                        break;
                }
                if(!containsRestrictedWord(tmpUsr))  {
                    resultSet.add(tmpUsr);
                }
            }
            currentSizeSet=resultSet.size();
            if(currentSizeSet>=numSugest) {
                j=NUM_ATTEMPTS;
            }
        }
        return resultSet;
    }

    /**
     * Generate random user names with numbers
     * @param str
     * @return 
     */
    private String generateUsrNumbersRandom(String str) {
        int s = generateRandom(100);
        String tmp = str+s;
        while(containsRestrictedWord(str)) {
            
        }
        return str + s;
    }

    /**
     * Generate random user names  replacing letters with numbers
     * @param str
     * @return 
     */
    private String generateUsrReplaceLetters(String str) {
        String result=str;
        int i=0;
        while(str.equals(result))  {
            List<Integer> nums=generateRandomList(5);
            for(i=0;i<nums.size();i++) {
                switch (nums.get(i)) {
                    case 0:
                        result = result.replace("a", "4");
                        result = result.replace("A", "4");
                        i=nums.size();
                        break;
                    case 1:
                        result = result.replace("e", "3");
                        result = result.replace("E", "3");
                        i=nums.size();
                        break;
                    case 2:
                        result = result.replace("i", "1");
                        result = result.replace("I", "1");
                        i=nums.size();
                        break;
                    case 3:
                        result = result.replace("o", "0");
                        result = result.replace("O", "0");
                        i=nums.size();
                        break;
                    case 4:
                        result = result.replace("u", "2");
                        result = result.replace("U", "2");
                        i=nums.size();
                        break;
                    default:
                        break;
                } 
            }
        }
        if(str.equals(result)) {
            result+=str+"404";
        }
        return result;
    }

    /**
     * Generate random user names adding words
     * @param str
     * @return 
     */
    private String generateUsrAddWord(String str) {
        int s = generateRandom(5);
        switch (s) {
            case 0:
                str += "TheKing";
                break;
            case 1:
                str += "Rules";
                break;
            case 2:
                str += "Overall";
                break;
            case 3:
                str += str;
                break;
            case 4:
                str += "Biever";
                break;
            default:
                break;
        }
        return str;
    }

    /**
     * MEthod to generate random numbers
     * @param size
     * @return 
     */
    private int generateRandom(int size) {
        ArrayList<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list.get(size - 1);
    }
    
    /**
     * MEthod to generate random list
     * @param size
     * @return 
     */
    private List<Integer> generateRandomList(int size) {
        ArrayList<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }
}
