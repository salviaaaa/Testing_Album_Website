/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.UnitDAO;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TblunitTest {
    
    private Tblunit tblunit;
    private UnitDAO unitDAO;
    
    @Before
    public void setUp() {
        tblunit = new Tblunit();
        tblunit.setUnitId("U1");
        tblunit.setUnitName("NCT 127");
        unitDAO = new UnitDAO();
    }

    @Test
    public void testGetUnitId() {
        assertEquals("U1", tblunit.getUnitId());
    }

    @Test
    public void testGetUnitName() {
        assertEquals("NCT 127", tblunit.getUnitName());
    }

    @Test
    public void testGetUnits() {
        List<Tblunit> units = tblunit.getUnits();
        assertNotNull(units);
        assertFalse(units.isEmpty());
    }

    @Test
    public void testGetNct127Units() {
        List<Tblunit> units = tblunit.getNct127Units();
        assertNotNull(units);
        assertEquals(1, units.size());
        assertEquals("NCT 127", units.get(0).getUnitName());
    }

    @Test
    public void testGetDreamUnits() {
        List<Tblunit> units = tblunit.getDreamUnits();
        assertNotNull(units);
        assertEquals(1, units.size());
        assertEquals("NCT Dream", units.get(0).getUnitName());
    }

    @Test
    public void testGetNctUnits() {
        List<Tblunit> units = tblunit.getNctUnits();
        assertNotNull(units);
        assertEquals(1, units.size());
        assertEquals("NCT U", units.get(0).getUnitName());
    }

    @Test
    public void testGetWayVUnits() {
        List<Tblunit> units = tblunit.getWayVUnits();
        assertNotNull(units);
        assertEquals(1, units.size());
        assertEquals("WAYV", units.get(0).getUnitName());
    }
    
    @Test
    public void testGetTblalbums() {
        assertNotNull(tblunit.getTblalbums());
    }

    @Test
    public void testGetTblmembers() {
        assertNotNull(tblunit.getTblmembers());
    }

    @Test
    public void testGetTblnewses() {
        assertNotNull(tblunit.getTblnewses());
    }

    @Test
    public void testConstructorWithParameters() {
        Tblunit unit = new Tblunit("U1", "NCT 127");
        assertEquals("U1", unit.getUnitId());
        assertEquals("NCT 127", unit.getUnitName());
    }

    @Test
    public void testConstructorWithAllParameters() {
        Set<Tblalbum> albums = new HashSet<>();
        Set<Tblmember> members = new HashSet<>();
        Set<Tblnews> newses = new HashSet<>();
        Tblunit unit = new Tblunit("U1", "NCT 127", albums, members, newses);
        
        assertEquals("U1", unit.getUnitId());
        assertEquals("NCT 127", unit.getUnitName());
        assertNotNull(unit.getTblalbums());
        assertNotNull(unit.getTblmembers());
        assertNotNull(unit.getTblnewses());
    }

}