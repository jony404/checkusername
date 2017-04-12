/*
 * FileUserAccess.java
 * Created on 9/04/2017
 */
package com.castelan.checkusername.dao;

import com.castelan.checkusername.exceptions.DaoException;
import com.castelan.checkusername.util.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Access a file to read user names
 *
 * @author Juan Castelan
 */
public class FileUserAccess {

    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private FileUserAccess()  {
        
    }
    
    private static class Loader {
         static FileUserAccess INSTANCE = new FileUserAccess();
    }
    
     public static FileUserAccess getInstance() {
         return FileUserAccess.Loader.INSTANCE;
     }
    /**
     * Reads existing users from the user list file
     * @return a sorted set of user names
     * @throws DaoException 
     */
    public SortedSet readExistingUsers() throws DaoException {
        return readFile(Constants.getInstance().getString("USERDB_FILENAME"));
    }

    /**
     * Reads restricted words from the restricted list file
     * @return a sorted set of restricted words from file
     * @throws DaoException 
     */
    public SortedSet readRestrictedWords() throws DaoException {
        return readFile(Constants.getInstance().getString("RESTRICTDB_FILENAME"));
    }

    /**
     * Adds a word to the restricted list file
     * @param word 
     * @throws exceptions.DaoException 
     */
    public void addRestrictedWord(String word)  throws DaoException {
        writeLine(Constants.getInstance().getString("RESTRICTDB_FILENAME"), word);
    }

    /**
     * Appends a line in a file
     * 
     * @param fileName
     * @param newLine 
     * @throws exceptions.DaoException 
     */
    public void writeLine(String fileName, String newLine) throws DaoException {
        rwlock.writeLock().lock();
        try (FileWriter fw = new FileWriter(getClass().getClassLoader().getResource(fileName).getFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(newLine);
        } catch (IOException e) {
            Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error reading file.", e);
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    /**
     * Reads a file of words and fills a set
     * @param fileName
     * @return a SortedSet with the words from the file
     * @throws DaoException 
     */
    public SortedSet readFile(String fileName) throws DaoException {
        BufferedReader br = null;
        FileReader fr = null;
        //List<String> result= new ArrayList<>();
        SortedSet resultSet;
        resultSet = new TreeSet();
        rwlock.readLock().lock();
        try {
            fr = new FileReader(getClass().getClassLoader().getResource(fileName).getFile());
            br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                resultSet.add(sCurrentLine.trim());
            }
        } catch (IOException e) {
            Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error reading file.", e);
        } finally {

            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(FileUserAccess.class.getName()).log(Level.SEVERE, null, ex);
                throw new DaoException("Error reading file.", ex);
            }
            rwlock.readLock().unlock();
        }
        return resultSet;
    }
}
