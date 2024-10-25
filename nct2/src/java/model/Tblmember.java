package model;

import dao.MemberDAO;
import util.FacesUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "memberBean")
@ViewScoped
public class Tblmember implements Serializable {

    private String memberId;
    private Tblunit tblunit;
    private String memberImg;
    private String memberName;
    private String role;
    List<Tblmember> members;
    MemberDAO memberDAO;
    private Tblmember newMember;
    private String unitId;

    public Tblmember() {
        tblunit = new Tblunit();
        memberDAO = new MemberDAO();
        // Hindari inisialisiasi rekursif
        newMember = new Tblmember(false); // Gunakan konstruktor lain untuk menghindari rekursi
        newMember.setTblunit(new Tblunit());
    }

    public Tblmember(String memberId, String memberName, String role) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.role = role;
    }

    public Tblmember(String memberId, Tblunit tblunit, String memberImg, String memberName, String role) {
        this.memberId = memberId;
        this.tblunit = tblunit;
        this.memberImg = memberImg;
        this.memberName = memberName;
        this.role = role;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Tblunit getTblunit() {
        return this.tblunit;
    }

    public void setTblunit(Tblunit tblunit) {
        this.tblunit = tblunit;
    }

    public String getMemberImg() {
        return this.memberImg;
    }

    public void setMemberImg(String memberImg) {
        this.memberImg = memberImg;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Logika dari MemberBean



    public void updateMember(Tblmember member) {
        try {
            memberDAO.updateMember(member); // Pastikan ini memperbarui database
            FacesUtil.addSuccessMessage("Member berhasil diperbarui");
            members = memberDAO.getAllMembers(); // Refresh daftar anggota
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal memperbarui member: " + e.getMessage());
        }
    }

    public void deleteMember(Tblmember member) {
        try {
            memberDAO.deleteMember(member);
            members.remove(member);
            FacesUtil.addSuccessMessage("Member berhasil dihapus");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal menghapus member: " + e.getMessage());
        }
    }

    public void addNewMember() {
        try {
            if (newMember.getTblunit() == null) {
                newMember.setTblunit(new Tblunit());
            }

            if (newMember.getTblunit().getUnitId() != null) {
                Tblunit unit = new Tblunit();
                unit.setUnitId(newMember.getTblunit().getUnitId());
                newMember.setTblunit(unit);
            } else {
                FacesUtil.addErrorMessage("Unit ID tidak boleh kosong");
                return;
            }

            // Generate ID member berikutnya
            newMember.setMemberId(generateNextMemberId());

            memberDAO.addMember(newMember);
            FacesUtil.addSuccessMessage("Member baru berhasil ditambahkan");
            members = memberDAO.getAllMembers(); // Refresh daftar anggota
            newMember = new Tblmember(); // Reset newMember
            newMember.setTblunit(new Tblunit());
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal menambahkan member: " + e.getMessage());
        }
    }

    public List<Tblmember> getMembers() {
        if (members == null) {
            members = memberDAO.getAllMembers();
        }
        return members;
    }

    public Tblmember getNewMember() {
        return newMember;
    }

    public void setNewMember(Tblmember newMember) {
        this.newMember = newMember;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public List<Tblmember> getNct127Members() {
        return memberDAO.getMembersByUnitId("u1");
    }

    public List<Tblmember> getNctDreamMembers() {
        return memberDAO.getMembersByUnitId("u2");
    }

    public List<Tblmember> getNctUMembers() {
        return memberDAO.getMembersByUnitId("u3");
    }

    public List<Tblmember> getWayVMembers() {
        return memberDAO.getMembersByUnitId("u4");
    }

    // Tambahkan konstruktor baru untuk menghindari rekursi
    public Tblmember(boolean isNew) {
        if (isNew) {
            tblunit = new Tblunit();
            memberDAO = new MemberDAO();
        }
    }

    // Contoh metode untuk menghasilkan memberId unik
    String generateUniqueMemberId() {
        // Implementasi untuk menghasilkan ID unik, misalnya menggunakan UUID
        return UUID.randomUUID().toString();
    }

    public String generateNextMemberId() {
        List<Tblmember> allMembers = memberDAO.getAllMembers();
        int maxId = 0;
        for (Tblmember member : allMembers) {
            String memberId = member.getMemberId();
            if (memberId != null && memberId.startsWith("M")) {
                try {
                    int id = Integer.parseInt(memberId.substring(1));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                    // Abaikan jika format tidak sesuai
                }
            }
        }
        return "M" + String.format("%d", maxId + 1);
    }
}
