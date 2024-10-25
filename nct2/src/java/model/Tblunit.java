package model;

import dao.UnitDAO;
import util.FacesUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "unitBean")
@ViewScoped
public class Tblunit implements Serializable {

    private String unitId;
    private String unitName;
    private Set tblalbums = new HashSet(0);
    private Set tblmembers = new HashSet(0);
    private Set tblnewses = new HashSet(0);
    private List<Tblunit> units;
    private UnitDAO unitDAO;

    public Tblunit() {
        unitDAO = new UnitDAO(); // Menggunakan UnitDAO yang sudah dimodifikasi
    }

    public Tblunit(String unitId, String unitName) {
        this.unitId = unitId;
        this.unitName = unitName;
    }

    public Tblunit(String unitId, String unitName, Set tblalbums, Set tblmembers, Set tblnewses) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.tblalbums = tblalbums;
        this.tblmembers = tblmembers;
        this.tblnewses = tblnewses;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Set getTblalbums() {
        return this.tblalbums;
    }

    public void setTblalbums(Set tblalbums) {
        this.tblalbums = tblalbums;
    }

    public Set getTblmembers() {
        return this.tblmembers;
    }

    public void setTblmembers(Set tblmembers) {
        this.tblmembers = tblmembers;
    }

    public Set getTblnewses() {
        return this.tblnewses;
    }

    public void setTblnewses(Set tblnewses) {
        this.tblnewses = tblnewses;
    }

    // Logika dari UnitBean

    public List<Tblunit> getUnits() {
        if (units == null) {
            units = unitDAO.getAllUnits();
        }
        return units;
    }

    // Metode untuk mendapatkan unit berdasarkan ID tetap ada
    public List<Tblunit> getNct127Units() {
        return unitDAO.getUnitsByUnitId("u1");
    }

    public List<Tblunit> getDreamUnits() {
        return unitDAO.getUnitsByUnitId("u2");
    }

    public List<Tblunit> getNctUnits() {
        return unitDAO.getUnitsByUnitId("u3");
    }

    public List<Tblunit> getWayVUnits() {
        return unitDAO.getUnitsByUnitId("u4");
    }
}
