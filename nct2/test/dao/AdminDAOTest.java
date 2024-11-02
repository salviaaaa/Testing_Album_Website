/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Tbladmin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USER
 */
public class AdminDAOTest {
    
    private AdminDAO instance;

    @Before
    public void setUp() {
        instance = new AdminDAO();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getBy method, of class AdminDAO.
     */
    @Test
    public void testGetBy() {
        System.out.println("testGetBy");

        // Pengujian untuk login yang berhasil
        List<Tbladmin> result = instance.getBy("faza_and_via", "123");
        assertNotNull(result); // Memastikan hasil tidak null
        assertFalse(result.isEmpty()); // Memastikan hasil tidak kosong
        
        //sebelumnya
        // Pengujian untuk login yang gagal
        //List<Tbladmin> errorResult = instance.getBy("admin", "wrongpassword");
        //assertNotNull(errorResult); // Memastikan hasil tidak null
        //assertTrue(errorResult.isEmpty()); // Memastikan hasil kosong
        
        // Pengujian untuk username yang tidak ada
        //List<Tbladmin> noUserResult = instance.getBy("non_existing_user", "any_password");
        //assertNotNull(noUserResult); // Memastikan hasil tidak null
        //assertTrue(noUserResult.isEmpty()); // Memastikan hasil kosong
    }

    @Test
    public void testLoginWithWrongPassword() {
        List<Tbladmin> errorResult = instance.getBy("admin", "wrongpassword");
        assertNotNull(errorResult); // Memastikan hasil tidak null
        assertTrue(errorResult.isEmpty()); // Memastikan hasil kosong
    }

    @Test
    public void testLoginWithNonExistingUser() {
        List<Tbladmin> noUserResult = instance.getBy("non_existing_user", "any_password");
        assertNotNull(noUserResult); // Memastikan hasil tidak null
        assertTrue(noUserResult.isEmpty()); // Memastikan hasil kosong
    }

    // Negative test case: Menguji login dengan username dan password kosong
    @Test
    public void testLoginWithEmptyCredentials() {
        List<Tbladmin> emptyResult = instance.getBy("", "");
        assertNotNull(emptyResult); // Memastikan hasil tidak null
        assertTrue(emptyResult.isEmpty()); // Memastikan hasil kosong
    }

    // Negative test case: Menguji login dengan username kosong dan password valid
    @Test
    public void testLoginWithEmptyUsername() {
        List<Tbladmin> result = instance.getBy("", "123");
        assertNotNull(result); // Memastikan hasil tidak null
        assertTrue(result.isEmpty()); // Memastikan hasil kosong
    }

    // Negative test case: Menguji login dengan username valid dan password kosong
    @Test
    public void testLoginWithEmptyPassword() {
        List<Tbladmin> result = instance.getBy("faza_and_via", "");
        assertNotNull(result); // Memastikan hasil tidak null
        assertTrue(result.isEmpty()); // Memastikan hasil kosong
    }

    // Negative test case: Menguji login dengan input yang tidak valid
    @Test
    public void testLoginWithSpecialCharacters() {
        List<Tbladmin> result = instance.getBy("!@#$%^&*", "123456");
        assertNotNull(result); // Memastikan hasil tidak null
        assertTrue(result.isEmpty()); // Memastikan hasil kosong
    }
}
