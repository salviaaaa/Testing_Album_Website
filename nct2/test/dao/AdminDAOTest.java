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
import java.lang.RuntimeException;

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
        
        // Pengujian untuk login yang gagal
        List<Tbladmin> errorResult = instance.getBy("admin", "wrongpassword");
        assertNotNull(errorResult); // Memastikan hasil tidak null
        assertTrue(errorResult.isEmpty()); // Memastikan hasil kosong
        
        // Pengujian untuk username yang tidak ada
        List<Tbladmin> noUserResult = instance.getBy("non_existing_user", "any_password");
        assertNotNull(noUserResult); // Memastikan hasil tidak null
        assertTrue(noUserResult.isEmpty()); // Memastikan hasil kosong
    }
}
