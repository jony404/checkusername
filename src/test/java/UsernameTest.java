/*
 * UsernameTest.java
 * Created on April 2017
 */

import com.castelan.checkusername.dto.Result;
import com.castelan.checkusername.exceptions.RestrictedWordException;
import com.castelan.checkusername.exceptions.UsernameLengthException;
import com.castelan.checkusername.process.ProcessUsername;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests for main check user name functionality
 * @author Juan Castelan
 */
public class UsernameTest {
    
    public UsernameTest() {
    }
    
    @Test
    public void checkNonExistingUserNameTest() {
        ProcessUsername procUser = new ProcessUsername();
        try {
            Result res = procUser.checkUsername("javier404");
            assertEquals("User name was found", false, res.isExistingUsrname());
        } catch (RestrictedWordException ex) {
            Logger.getLogger(UsernameTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UsernameLengthException ex) {
            Logger.getLogger(UsernameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void checkExistingUserNameTest() {
        ProcessUsername procUser = new ProcessUsername();
        String userPropose="matias";
        try {
            Result res = procUser.checkUsername(userPropose);
            assertEquals("User name was not found", true, res.isExistingUsrname());
            String[] suges=res.getSuggestions();
            for (String suge : suges) {
                Result resSugestion = procUser.checkUsername(suge);
                assertEquals("Suggested user name was found", false, resSugestion.isExistingUsrname());
            }
        } catch (RestrictedWordException ex) {
            Logger.getLogger(UsernameTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UsernameLengthException ex) {
            Logger.getLogger(UsernameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
