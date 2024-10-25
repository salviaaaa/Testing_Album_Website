/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.faces.context.FacesContext;
import org.mockito.Mockito;
import javax.faces.component.UIViewRoot;
import javax.faces.lifecycle.Lifecycle;

/**
 *
 * @author USER
 */
public class TbladminTest {

    private Tbladmin instance;

    public TbladminTest() {
    }

    @Before
    public void setUp() {
        instance = new Tbladmin("faza_and_via", "fv@nctadmin.com", "123", "admin");
    }

    /**
     * Test of getAdminId method, of class Tbladmin.
     */
    @Test
    public void testGetAdminId() {
        Integer expResult = 3;
        instance.setAdminId(expResult);
        Integer result = instance.getAdminId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class Tbladmin.
     */
    @Test
    public void testGetUsername() {
        String expResult = "faza_and_via";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEmail method, of class Tbladmin.
     */
    @Test
    public void testGetEmail() {
        String expResult = "fv@nctadmin.com";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class Tbladmin.
     */
    @Test
    public void testGetPassword() {
        String expResult = "123";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRole method, of class Tbladmin.
     */
    @Test
    public void testGetRole() {
        String expResult = "admin";
        String result = instance.getRole();
        assertEquals(expResult, result);
    }

    /**
     * Test of validasiLogin method for successful login, of class Tbladmin.
     */
    
    
    @Test
    public void testValidasiLogin() {
        System.out.println("validasiLogin");
        
        // Pengujian untuk login yang berhasil
        instance = new Tbladmin("faza_and_via", "fv@nctadmin.com", "123", "admin");
        String expResult = "admin?faces-redirect=true";
        String result = instance.validasiLogin();
        assertEquals(expResult, result);

       
    }
    
    @Test
    public void testValidasiLoginWithInvalidCredentials() {
        instance.setUsername("invalid_user");
        instance.setPassword("wrong_password");
        String expResult = "signin.xhtml?faces-redirect=true";
        String result = instance.validasiLogin();
        assertEquals(expResult, result);
    }

    /**
     * Test of logout method, of class Tbladmin.
     */
    @Test
    public void testLogout() {
        System.out.println("logout");
        String expResult = "/index.xhtml?faces-redirect=true";
        String result = instance.logout();
        assertEquals(expResult, result);
    }

    /**
     * Test of isLoggedIn method, of class Tbladmin.
     */
    @Test
    public void testIsLoggedIn() {
        boolean expResult = true;
        boolean result = instance.isLoggedIn();
        assertEquals(expResult, result);
    }

    /**
     * Test of afterPhase method, of class Tbladmin.
     */
    @Test
    public void testAfterPhase() {
        // Buat mock FacesContext
        FacesContext mockContext = Mockito.mock(FacesContext.class);
        UIViewRoot mockViewRoot = Mockito.mock(UIViewRoot.class);   
        // Set up mock behavior
        Mockito.when(mockContext.getViewRoot()).thenReturn(mockViewRoot);
        Mockito.when(mockViewRoot.getViewId()).thenReturn("/admin.xhtml"); 
        // Buat PhaseEvent dengan mock FacesContext
        PhaseEvent event = new PhaseEvent(mockContext, PhaseId.RESTORE_VIEW, Mockito.mock(Lifecycle.class)); 
        // Jalankan metode afterPhase
        instance.afterPhase(event);   
        // Tidak ada assertion yang diperlukan jika tidak ada pengecualian yang dilempar
    }

    /**
     * Test of beforePhase method, of class Tbladmin.
     */
    @Test
    public void testBeforePhase() {
        PhaseEvent event = null; // Mock or create a PhaseEvent if needed
        instance.beforePhase(event);
        // No assertion needed if no exception is thrown
    }

    /**
     * Test of getPhaseId method, of class Tbladmin.
     */
    @Test
    public void testGetPhaseId() {
        PhaseId expResult = PhaseId.RESTORE_VIEW;
        PhaseId result = instance.getPhaseId();
        assertEquals(expResult, result);
    }

    

}
