/*
 * Result.java
 * Created on April 4th, 2017
 */
package com.castelan.checkusername.dto;

/**
 * DTO to transfer results
 * @author Juan Castelan
 */
public class Result {

    private boolean existingUsrname;
    private String[] suggestions;

    public Result()  {
        existingUsrname = false;
        suggestions = null;
    }
    /**
     * @return the existingUsrname
     */
    public boolean isExistingUsrname() {
        return existingUsrname;
    }

    /**
     * @param existingUsrname the existingUsrname to set
     */
    public void setExistingUsrname(boolean existingUsrname) {
        this.existingUsrname = existingUsrname;
    }

    /**
     * @return the suggestions
     */
    public String[] getSuggestions() {
        return suggestions;
    }

    /**
     * @param suggestions the suggestions to set
     */
    public void setSuggestions(String[] suggestions) {
        this.suggestions = suggestions;
    }

}
