package model;

import dao.AlbumDAO;
import util.FacesUtil;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Base64;

@ManagedBean(name = "albumBean")
@ViewScoped
public class Tblalbum implements Serializable {

    private Integer albumId;
    private Tblunit tblunit;
    private byte[] albumImg;
    private String albumName;
    private String albumDescription;
    private String url;
    private String url1;
    List<Tblalbum> albums;
    AlbumDAO albumDAO;
    private Part albumImageFile;
    private Tblalbum newAlbum;
    private String unitId;
    private Integer selectedAlbumId;

    public Tblalbum() {
        tblunit = new Tblunit();
        albumDAO = new AlbumDAO();
        newAlbum = new Tblalbum(false);
        newAlbum.setTblunit(new Tblunit());
    }

    private Tblalbum(boolean isNewAlbum) {
        if (isNewAlbum) {
            tblunit = new Tblunit();
            albumDAO = new AlbumDAO();
            newAlbum = new Tblalbum(false);
        }
    }

    public Tblalbum(String albumName, String albumDescription, String url) {
        this.albumName = albumName;
        this.albumDescription = albumDescription;
        this.url = url;
    }

    public Tblalbum(byte[] albumImg, String albumName, String albumDescription, String url, Tblunit tblunit, String url1) {
        this.albumImg = albumImg;
        this.albumName = albumName;
        this.albumDescription = albumDescription;
        this.url = url;
        this.tblunit = tblunit;
        this.url1 = url1;
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Tblunit getTblunit() {
        return this.tblunit;
    }

    public void setTblunit(Tblunit tblunit) {
        this.tblunit = tblunit;
    }

    public byte[] getAlbumImg() {
        return this.albumImg;
    }

    public void setAlbumImg(byte[] albumImg) {
        this.albumImg = albumImg;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDescription() {
        return this.albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl1() {
        return this.url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getAlbumImgBase64() {
        return albumImg != null ? Base64.getEncoder().encodeToString(albumImg) : null;
    }

    // Logika dari AlbumBean
    public void loadAlbums() {
        albums = albumDAO.getAllAlbums();
    }

    public Part getAlbumImageFile() {
        return albumImageFile;
    }

    public void setAlbumImageFile(Part albumImageFile) {
        this.albumImageFile = albumImageFile;
    }
    
    // ini juga baru dipindahin penempatannnya 
    
    public Tblalbum getNewAlbum() {
        return newAlbum;
    }

    public void setNewAlbum(Tblalbum newAlbum) {
        this.newAlbum = newAlbum;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public List<Tblalbum> getNct127Albums() {
        return albumDAO.getAlbumsByUnitId("u1");
    }

    public List<Tblalbum> getNctDreamAlbums() {
        return albumDAO.getAlbumsByUnitId("u2");
    }

    public List<Tblalbum> getNctUAlbums() {
        return albumDAO.getAlbumsByUnitId("u3");
    }

    public List<Tblalbum> getWayVAlbums() {
        return albumDAO.getAlbumsByUnitId("u4");
    } 
    
    public List<Tblalbum> getAlbums() {
        if (albums == null) {
            albums = albumDAO.getAllAlbums();
        }
        return albums;
    }
    

    public void updateAlbum(Tblalbum album) {
        try {
            albumDAO.updateAlbum(album);
            FacesUtil.addSuccessMessage("Album berhasil diperbarui");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal memperbarui album: " + e.getMessage());
        }
    }
    
    public void uploadAlbumImage(Tblalbum targetAlbum) {
        if (albumImageFile != null) {
            try (InputStream input = albumImageFile.getInputStream()) {
                byte[] fileContent = new byte[(int) albumImageFile.getSize()];
                input.read(fileContent);
                targetAlbum.setAlbumImg(fileContent);
            } catch (IOException e) {
                FacesUtil.addErrorMessage("Gagal mengunggah gambar: " + e.getMessage());
            }
        }
    }
 

    public void handleFileUpload(Tblalbum album) {
        if (albumImageFile != null) {
            try (InputStream input = albumImageFile.getInputStream()) {
                byte[] fileContent = new byte[(int) albumImageFile.getSize()];
                input.read(fileContent);
                album.setAlbumImg(fileContent);
                albumDAO.updateAlbum(album);
                FacesUtil.addSuccessMessage("Gambar album berhasil diperbarui");
            } catch (IOException e) {
                FacesUtil.addErrorMessage("Gagal mengunggah gambar: " + e.getMessage());
            }
        }
    }

    public void addNewAlbum() {
        try {
            System.out.println("Attempting to add new album");
            System.out.println("New Album: " + newAlbum);
            System.out.println("Unit: " + (newAlbum.getTblunit() != null ? newAlbum.getTblunit().getUnitId() : "null"));

            if (albumImageFile != null && albumImageFile.getSize() > 0) {
                uploadAlbumImage(newAlbum);
            }

            if (newAlbum.getTblunit() == null) {
                newAlbum.setTblunit(new Tblunit());
            }

            if (newAlbum.getTblunit().getUnitId() != null) {
                String unitIdString = newAlbum.getTblunit().getUnitId();
                Tblunit unit = new Tblunit();
                unit.setUnitId(unitIdString);
                newAlbum.setTblunit(unit);
            } else {
                FacesUtil.addErrorMessage("Unit ID tidak boleh kosong");
                return;
            }

            albumDAO.addAlbum(newAlbum);
            FacesUtil.addSuccessMessage("Album baru berhasil ditambahkan");
            albums = null;
            newAlbum = new Tblalbum();
            newAlbum.setTblunit(new Tblunit());
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage("Gagal menambahkan album: " + e.getMessage());
        }
    }



    public void deleteAlbum(Tblalbum album) {
        try {
            albumDAO.deleteAlbum(album);
            albums.remove(album);
            FacesUtil.addSuccessMessage("Album berhasil dihapus");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal menghapus album: " + e.getMessage());
        }
    }

    public Integer getSelectedAlbumId() {
        return selectedAlbumId;
    }

    public void setSelectedAlbumId(Integer selectedAlbumId) {
        this.selectedAlbumId = selectedAlbumId;
    }
}
