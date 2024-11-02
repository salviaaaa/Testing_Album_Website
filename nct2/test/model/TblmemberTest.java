/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.MemberDAO;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import util.FacesContextWrapper;
import util.FacesUtil;

/**
 *
 * @author USER
 */
public class TblmemberTest {
    
    private Tblmember member;
    private MemberDAO memberDAO;
    private FacesContextWrapper facesContextWrapper;
    
    @Before
    public void setUp() {
        member = new Tblmember();
        memberDAO = mock(MemberDAO.class);
        member.memberDAO = memberDAO;
        
        // Mock FacesContextWrapper
        facesContextWrapper = mock(FacesContextWrapper.class);
        FacesUtil.setFacesContextWrapper(facesContextWrapper);
    }

    @Test
    public void testGetMemberId() {
        String expectedId = "M1";
        member.setMemberId(expectedId);
        assertEquals(expectedId, member.getMemberId());
    }

    @Test
    public void testSetMemberId() {
        String expectedId = "M2";
        member.setMemberId(expectedId);
        assertEquals(expectedId, member.getMemberId());
    }

    @Test
    public void testGetTblunit() {
        Tblunit unit = new Tblunit("U1", "NCT 127");
        member.setTblunit(unit);
        assertEquals(unit, member.getTblunit());
    }

    @Test
    public void testSetTblunit() {
        Tblunit unit = new Tblunit("U2", "NCT Dream");
        member.setTblunit(unit);
        assertEquals(unit, member.getTblunit());
    }

    @Test
    public void testGetMemberImg() {
        String expectedImg = "image.jpg";
        member.setMemberImg(expectedImg);
        assertEquals(expectedImg, member.getMemberImg());
    }

    @Test
    public void testSetMemberImg() {
        String expectedImg = "newimage.jpg";
        member.setMemberImg(expectedImg);
        assertEquals(expectedImg, member.getMemberImg());
    }

    @Test
    public void testGetMemberName() {
        String expectedName = "Taeyong";
        member.setMemberName(expectedName);
        assertEquals(expectedName, member.getMemberName());
    }

    @Test
    public void testSetMemberName() {
        String expectedName = "Mark";
        member.setMemberName(expectedName);
        assertEquals(expectedName, member.getMemberName());
    }

    @Test
    public void testGetRole() {
        String expectedRole = "Leader";
        member.setRole(expectedRole);
        assertEquals(expectedRole, member.getRole());
    }

    @Test
    public void testSetRole() {
        String expectedRole = "Vocalist";
        member.setRole(expectedRole);
        assertEquals(expectedRole, member.getRole());
    }



    @Test
    public void testGetNct127Members() {
        List<Tblmember> members = member.getNct127Members();
        assertNotNull(members);
        for (Tblmember m : members) {
            assertEquals("U1", m.getTblunit().getUnitId());
        }
    }

    @Test
    public void testGetNctDreamMembers() {
        List<Tblmember> members = member.getNctDreamMembers();
        assertNotNull(members);
        for (Tblmember m : members) {
            assertEquals("U2", m.getTblunit().getUnitId());
        }
    }

    @Test
    public void testGetNctUMembers() {
        List<Tblmember> members = member.getNctUMembers();
        assertNotNull(members);
        for (Tblmember m : members) {
            assertEquals("U3", m.getTblunit().getUnitId());
        }
    }

    @Test
    public void testGetWayVMembers() {
        List<Tblmember> members = member.getWayVMembers();
        assertNotNull(members);
        for (Tblmember m : members) {
            assertEquals("U4", m.getTblunit().getUnitId());
        }
    }

    @Test
    public void testConstructorWithThreeParameters() {
        String memberId = "M1";
        String memberName = "John Doe";
        String role = "Singer";
        
        Tblmember member = new Tblmember(memberId, memberName, role);
        
        assertEquals(memberId, member.getMemberId());
        assertEquals(memberName, member.getMemberName());
        assertEquals(role, member.getRole());
    }

    @Test
    public void testConstructorWithFiveParameters() {
        String memberId = "M2";
        Tblunit tblunit = new Tblunit("U1", "NCT 127");
        String memberImg = "image.jpg";
        String memberName = "Jane Doe";
        String role = "Dancer";
        
        Tblmember member = new Tblmember(memberId, tblunit, memberImg, memberName, role);
        
        assertEquals(memberId, member.getMemberId());
        assertEquals(tblunit, member.getTblunit());
        assertEquals(memberImg, member.getMemberImg());
        assertEquals(memberName, member.getMemberName());
        assertEquals(role, member.getRole());
    }

    @Test
    public void testAddNewMember() {
        // Persiapan
        Tblmember newMember = new Tblmember();
        newMember.setMemberName("New Member");
        newMember.setRole("New Role");
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        newMember.setTblunit(unit);
        
        member.setNewMember(newMember);
        
        when(memberDAO.getAllMembers()).thenReturn(Arrays.asList(
            new Tblmember("M1", "Existing Member", "Role")
        ));
        doNothing().when(memberDAO).addMember(any(Tblmember.class));
        
        // Eksekusi
        member.addNewMember();
        
        // Verifikasi
        verify(memberDAO).addMember(any(Tblmember.class));
        verify(memberDAO, times(2)).getAllMembers(); // Dipanggil untuk generateNextMemberId dan refresh
        verify(facesContextWrapper).addMessage(eq(null), any(FacesMessage.class));
        
        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(memberDAO).addMember(any(Tblmember.class));
        member.addNewMember();
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class)); // Verifikasi pesan error ditambahkan
    }

    @Test
    public void testUpdateMember() {
        // Persiapan
        Tblmember memberToUpdate = new Tblmember("M1", "Updated Member", "Updated Role");
        doNothing().when(memberDAO).updateMember(any(Tblmember.class));
        when(memberDAO.getAllMembers()).thenReturn(Arrays.asList(memberToUpdate));
        
        // Eksekusi
        member.updateMember(memberToUpdate);
        
        // Verifikasi
        verify(memberDAO).updateMember(memberToUpdate);
        verify(memberDAO).getAllMembers();
        verify(facesContextWrapper).addMessage(eq(null), any(FacesMessage.class));
        
        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(memberDAO).updateMember(any(Tblmember.class));
        member.updateMember(memberToUpdate);
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class)); // Verifikasi pesan error ditambahkan
    }

    @Test
    public void testDeleteMember() {
        // Persiapan
        Tblmember memberToDelete = new Tblmember("M1", "Member to Delete", "Role");
        doNothing().when(memberDAO).deleteMember(any(Tblmember.class));
        member.members = new ArrayList<>(Arrays.asList(memberToDelete));
        
        // Eksekusi
        member.deleteMember(memberToDelete);
        
        // Verifikasi
        verify(memberDAO).deleteMember(memberToDelete);
        verify(facesContextWrapper).addMessage(eq(null), any(FacesMessage.class));
        assertTrue(member.members.isEmpty());
        
        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(memberDAO).deleteMember(any(Tblmember.class));
        member.deleteMember(memberToDelete);
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class)); // Verifikasi pesan error ditambahkan
    }

    @Test
    public void testGetMembers() {
        List<Tblmember> expectedMembers = Arrays.asList(
            new Tblmember("M1", "Mark Lee", "Rapper"),
            new Tblmember("M2", "Taeyong", "Leader")
        );
        when(memberDAO.getAllMembers()).thenReturn(expectedMembers);
        
        List<Tblmember> actualMembers = member.getMembers();
        
        assertEquals(expectedMembers, actualMembers);
        verify(memberDAO).getAllMembers();
    }

    @Test
    public void testGenerateUniqueMemberId() {
        String uniqueId = member.generateUniqueMemberId();
        assertNotNull(uniqueId);
        assertTrue(uniqueId.length() > 0);
    }

    /**
     *
     */
    @Test
    public void testGenerateNextMemberId() {
        // Menyiapkan data pengujian
        List<Tblmember> existingMembers = Arrays.asList(
            new Tblmember("M1", "Member 1", "Role 1"),
            new Tblmember("M2", "Member 2", "Role 2"),
            new Tblmember("M3", "Member 3", "Role 3")
        );
        when(memberDAO.getAllMembers()).thenReturn(existingMembers);
        
        // Memanggil metode yang diuji
        String nextId = member.generateNextMemberId();
        
        // Verifikasi hasil
        assertEquals("M4", nextId);
        
        // Verifikasi bahwa getAllMembers() dipanggil
        verify(memberDAO).getAllMembers();
        
        // Pengujian tambahan untuk kasus khusus
        when(memberDAO.getAllMembers()).thenReturn(Arrays.asList(
            new Tblmember("M9", "Member 9", "Role 9")
        ));
        nextId = member.generateNextMemberId();
        assertEquals("M10", nextId);
        
        // Pengujian untuk kasus tanpa anggota
        when(memberDAO.getAllMembers()).thenReturn(new ArrayList<>());
        nextId = member.generateNextMemberId();
        assertEquals("M1", nextId);
    }

    @Test
    public void testSetUnitId() {
        String expectedUnitId = "U1";
        member.setUnitId(expectedUnitId);
        assertEquals(expectedUnitId, member.getUnitId());
    }

    @Test
    public void testGetNewMember() {
        Tblmember newMember = new Tblmember("M10", "New Member", "Role");
        member.setNewMember(newMember);
        assertEquals(newMember, member.getNewMember());
    }

    @Test
    public void testGetUnitId() {
        String expectedUnitId = "U2";
        member.setUnitId(expectedUnitId);
        assertEquals(expectedUnitId, member.getUnitId());
    }

    @Test
    public void testTblmemberConstructorWithBoolean() {
        Tblmember newMember = new Tblmember(true);
        assertNotNull(newMember.getTblunit());
        assertNotNull(newMember.memberDAO);

        Tblmember existingMember = new Tblmember(false);
        assertNull(existingMember.getTblunit());
        assertNull(existingMember.memberDAO);
    }

    // Negative test case: Menguji getMembers ketika DAO mengembalikan null
    @Test
    public void testGetMembersWhenDaoReturnsNull() {
        when(memberDAO.getAllMembers()).thenReturn(null); // Simulasi DAO mengembalikan null
        
        List<Tblmember> actualMembers = member.getMembers();
        
        assertNull(actualMembers); // Pastikan hasilnya null
        verify(memberDAO).getAllMembers(); // Verifikasi bahwa DAO dipanggil
    }

    // Negative test case: Menguji generateNextMemberId ketika tidak ada anggota
    @Test
    public void testGenerateNextMemberIdWhenNoMembers() {
        when(memberDAO.getAllMembers()).thenReturn(new ArrayList<>()); // Simulasi tidak ada anggota
        
        String nextId = member.generateNextMemberId();
        
        assertEquals("M1", nextId); // Diharapkan ID pertama adalah M1
        verify(memberDAO).getAllMembers(); // Verifikasi bahwa DAO dipanggil
    }

    // Negative test case: Menguji generateNextMemberId ketika ada anggota dengan ID tertinggi
    @Test
    public void testGenerateNextMemberIdWithHighestId() {
        when(memberDAO.getAllMembers()).thenReturn(Arrays.asList(
            new Tblmember("M5", "Member 5", "Role 5"),
            new Tblmember("M6", "Member 6", "Role 6")
        )); // Simulasi anggota dengan ID tertinggi M6
        
        String nextId = member.generateNextMemberId();
        
        assertEquals("M7", nextId); // Diharapkan ID berikutnya adalah M7
        verify(memberDAO).getAllMembers(); // Verifikasi bahwa DAO dipanggil
    }
}
