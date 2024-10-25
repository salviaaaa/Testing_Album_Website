package model;
// Generated Sep 30, 2024 2:35:09 PM by Hibernate Tools 4.3.1

import java.util.Base64;
import dao.NewsDAO;
import util.FacesUtil;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "newsBean")
@ViewScoped
public class Tblnews  implements java.io.Serializable {


     private Integer newsId;
     private Tblunit tblunit;
     private String news;
     private byte[] image;
     List<Tblnews> newsList;
     NewsDAO newsDAO;
     private Part newsImageFile;
     private Tblnews newNews;
     private Integer unitId;

    public Tblnews() {
        tblunit = new Tblunit();
        newsDAO = new NewsDAO();
        newNews = new Tblnews(false);
        newNews.setTblunit(new Tblunit());
    }

    // Tambahkan konstruktor tambahan untuk menghindari rekursi
    private Tblnews(boolean isNewNews) {
        if (isNewNews) {
            tblunit = new Tblunit();
            newsDAO = new NewsDAO();
            newNews = new Tblnews(false);
        }
    }

    public Tblnews(Tblunit tblunit, String news, byte[] image) {
       this.tblunit = tblunit;
       this.news = news;
       this.image = image;
    }
   
    public Integer getNewsId() {
        return this.newsId;
    }
    
    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }
    public Tblunit getTblunit() {
        return this.tblunit;
    }
    
    public void setTblunit(Tblunit tblunit) {
        this.tblunit = tblunit;
    }
    public String getNews() {
        return this.news;
    }
    
    public void setNews(String news) {
        this.news = news;
    }
    public byte[] getImage() {
        return this.image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
    public String getNewsImgBase64() {
        return image != null ? Base64.getEncoder().encodeToString(image) : null;
    }
    
    /*perpindahan pada newsbean*/



    public void updateNews(Tblnews news) {
        try {
            newsDAO.updateNews(news);
            int index = newsList.indexOf(news);
            if (index != -1) {
                newsList.set(index, news);
            }
            FacesUtil.addSuccessMessage("Berita berhasil diperbarui");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal memperbarui berita: " + e.getMessage());
        }
    }

    public void deleteNews(Tblnews news) {
        try {
            newsDAO.deleteNews(news);
            newsList.remove(news);
            FacesUtil.addSuccessMessage("Berita berhasil dihapus");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Gagal menghapus berita: " + e.getMessage());
        }
    }

    public void uploadNewsImage() {
        if (newsImageFile != null) {
            try (InputStream input = newsImageFile.getInputStream()) {
                byte[] fileContent = new byte[(int) newsImageFile.getSize()];
                input.read(fileContent);
                this.setImage(fileContent);
            } catch (IOException e) {
                FacesUtil.addErrorMessage("Gagal mengunggah gambar: " + e.getMessage());
            }
        }
    }

    public void addNewNews() {
        try {
            if (newNews.getTblunit() == null) {
                newNews.setTblunit(new Tblunit());
            }
            
            if (newNews.getTblunit().getUnitId() == null || newNews.getTblunit().getUnitId().isEmpty()) {
                FacesUtil.addErrorMessage("Unit ID tidak boleh kosong");
                return;
            }

            if (newsImageFile != null && newsImageFile.getSize() > 0) {
                uploadNewsImage(newNews);
            }
            
            newsDAO.addNews(newNews);
            FacesUtil.addSuccessMessage("Berita baru berhasil ditambahkan");
            newsList = null;
            newNews = new Tblnews();
            newNews.setTblunit(new Tblunit());
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage("Gagal menambahkan berita: " + e.getMessage());
        }
    }

    public void uploadNewsImage(Tblnews targetNews) {
        if (newsImageFile != null) {
            try (InputStream input = newsImageFile.getInputStream()) {
                byte[] fileContent = new byte[(int) newsImageFile.getSize()];
                input.read(fileContent);
                targetNews.setImage(fileContent);
            } catch (IOException e) {
                FacesUtil.addErrorMessage("Gagal mengunggah gambar: " + e.getMessage());
            }
        }
    }

    public List<Tblnews> getNewsList() {
        if (newsList == null) {
            newsList = newsDAO.getAllNews();
        }
        return newsList;
    }

    public void setNewsList(List<Tblnews> newsList) {
        this.newsList = newsList;
    }

    public Part getNewsImageFile() {
        return newsImageFile;
    }

    public void setNewsImageFile(Part newsImageFile) {
        this.newsImageFile = newsImageFile;
    }

    public Tblnews getNewNews() {
        return newNews;
    }

    public void setNewNews(Tblnews newNews) {
        this.newNews = newNews;
    }

    public Integer getUnitId() {
        if (this.tblunit != null) {
            return Integer.valueOf(this.tblunit.getUnitId());
        }
        return null;
    }

    public void setUnitId(Integer unitId) {
        if (this.tblunit == null) {
            this.tblunit = new Tblunit();
        }
        this.tblunit.setUnitId(unitId.toString());
    }

    public List<Tblnews> getNewsByUnitId(String unitId) {
        return newsDAO.getNewsByUnitId(unitId);
    }

    public void handleFileUpload() {
        if (newsImageFile != null) {
            try (InputStream input = newsImageFile.getInputStream()) {
                byte[] fileContent = new byte[(int) newsImageFile.getSize()];
                input.read(fileContent);
                this.setImage(fileContent);
                newsDAO.updateNews(this);
                FacesUtil.addSuccessMessage("Gambar berita berhasil diperbarui");
            } catch (IOException e) {
                FacesUtil.addErrorMessage("Gagal mengunggah gambar: " + e.getMessage());
            }
        }
    }

   
}
