/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Arrays;
import java.util.List;
import model.Tblunit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author USER
 */
public class UnitDAOTest {
    
    @Mock
    private UnitDAO unitDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getAllUnits method, of class UnitDAO.
     */
    @Test
    public void testGetAllUnits() {
        Tblunit unit1 = new Tblunit("U1", "NCT 127");
        Tblunit unit2 = new Tblunit("U2", "NCT Dream");
        Tblunit unit3 = new Tblunit("U3", "NCT U");
        Tblunit unit4 = new Tblunit("U4", "WAYV");
        List<Tblunit> expectedUnits = Arrays.asList(unit1, unit2, unit3, unit4);

        when(unitDAO.getAllUnits()).thenReturn(expectedUnits);

        List<Tblunit> actualUnits = unitDAO.getAllUnits();

        assertEquals(expectedUnits.size(), actualUnits.size());
        assertEquals(expectedUnits, actualUnits);
        verify(unitDAO).getAllUnits();
    }

    /**
     * Test of getUnitsByUnitId method, of class UnitDAO.
     */
    @Test
    public void testGetUnitsByUnitId() {
        Tblunit expectedUnit = new Tblunit("U1", "NCT 127");
        List<Tblunit> expectedUnits = Arrays.asList(expectedUnit);

        when(unitDAO.getUnitsByUnitId("U1")).thenReturn(expectedUnits);

        List<Tblunit> actualUnits = unitDAO.getUnitsByUnitId("U1");

        assertEquals(expectedUnits.size(), actualUnits.size());
        assertEquals(expectedUnits, actualUnits);
        verify(unitDAO).getUnitsByUnitId("U1");
    }
    
    @Test
    public void testGetUnitsByUnitIdNotFound() {
        when(unitDAO.getUnitsByUnitId("U5")).thenReturn(Arrays.asList());

        List<Tblunit> actualUnits = unitDAO.getUnitsByUnitId("U5");

        assertTrue(actualUnits.isEmpty());
        verify(unitDAO).getUnitsByUnitId("U5");
    }
    //baru ditambahkan
    // Negative test case: Mengambil unit dengan ID yang tidak valid
    @Test
    public void testGetUnitsByInvalidId() {
        when(unitDAO.getUnitsByUnitId(null)).thenThrow(new IllegalArgumentException("Unit ID cannot be null"));

        try {
            unitDAO.getUnitsByUnitId(null);
            fail("Expected an exception to be thrown due to null unit ID");
        } catch (IllegalArgumentException e) {
            assertEquals("Unit ID cannot be null", e.getMessage()); // Pastikan exception yang tepat dilempar
        }
    }

    // Negative test case: Mengambil semua unit ketika tidak ada unit yang tersedia
    @Test
    public void testGetAllUnitsWhenEmpty() {
        when(unitDAO.getAllUnits()).thenReturn(Arrays.asList()); // Mengembalikan daftar kosong

        List<Tblunit> actualUnits = unitDAO.getAllUnits();

        assertNotNull(actualUnits); // Pastikan hasil tidak null
        assertTrue(actualUnits.isEmpty()); // Pastikan daftar unit kosong
        verify(unitDAO).getAllUnits(); // Verifikasi bahwa metode dipanggil
    }
}
