/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Tblmember;
import model.Tblunit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author USER
 */
public class MemberDAOTest {
    
    private MemberDAO memberDAO;
    private Tblmember testMember;
    
    @Before
    public void setUp() {
        memberDAO = new MemberDAO();
        testMember = new Tblmember();
        testMember.setMemberId("M1");
        testMember.setMemberName("Test Member");
        testMember.setRole("Test Role");
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        testMember.setTblunit(unit);
    }

    /**
     * Test of addMember method, of class MemberDAO.
     */
    @Test
    public void testAddMember() {
        memberDAO.addMember(testMember);
        Tblmember retrievedMember = memberDAO.getMemberById("M1");
        assertNotNull(retrievedMember);
        assertEquals(testMember.getMemberName(), retrievedMember.getMemberName());
    }

    /**
     * Test of updateMember method, of class MemberDAO.
     */
    @Test
    public void testUpdateMember() {
        memberDAO.addMember(testMember);
        testMember.setMemberName("Updated Name");
        memberDAO.updateMember(testMember);
        Tblmember updatedMember = memberDAO.getMemberById("M1");
        assertEquals("Updated Name", updatedMember.getMemberName());
    }

    /**
     * Test of deleteMember method, of class MemberDAO.
     */
    @Test
    public void testDeleteMember() {
        memberDAO.addMember(testMember);
        memberDAO.deleteMember(testMember);
        Tblmember deletedMember = memberDAO.getMemberById("M1");
        assertNull(deletedMember);
    }

    /**
     * Test of getMemberById method, of class MemberDAO.
     */
    @Test
    public void testGetMemberById() {
        memberDAO.addMember(testMember);
        Tblmember retrievedMember = memberDAO.getMemberById("M1");
        assertNotNull(retrievedMember);
        assertEquals(testMember.getMemberId(), retrievedMember.getMemberId());
    }

    /**
     * Test of getAllMembers method, of class MemberDAO.
     */
    @Test
    public void testGetAllMembers() {
        memberDAO.addMember(testMember);
        List<Tblmember> members = memberDAO.getAllMembers();
        assertNotNull(members);
        assertFalse(members.isEmpty());
    }

    /**
     * Test of getMembersByUnitId method, of class MemberDAO.
     */
    @Test
    public void testGetMembersByUnitId() {
        memberDAO.addMember(testMember);
        List<Tblmember> members = memberDAO.getMembersByUnitId("U1");
        assertNotNull(members);
        assertFalse(members.isEmpty());
        assertEquals("U1", members.get(0).getTblunit().getUnitId());
    }

    // Negative test case: Mengambil anggota dengan ID yang tidak valid
    @Test
    public void testGetMemberByInvalidId() {
        Tblmember member = memberDAO.getMemberById("M999"); // ID yang tidak ada
        assertNull(member); // Pastikan hasilnya null
    }
    
    
}