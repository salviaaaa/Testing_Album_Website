

package dao;

import java.util.List;
import model.Tblnews;
import model.Tblunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NewsDAOTest {

    private NewsDAO newsDAO;
    private Tblnews testNews;

    @Before
    public void setUp() {
        newsDAO = new NewsDAO();
        
        // Setup data untuk berita baru
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        unit.setUnitName("NCT 127");

        testNews = new Tblnews();
        testNews.setNews("Berita terbaru NCT");
        testNews.setImage(new byte[]{1, 2, 3, 4, 5});
        testNews.setTblunit(unit);

        // Menambahkan berita ke database
        newsDAO.addNews(testNews);
    }

    @After
    public void tearDown() {
        // Menghapus berita setelah pengujian
        newsDAO.deleteNews(testNews);
    }

    @Test
    public void testAddNews() {
        // Menggunakan berita yang sama
        Tblnews retrievedNews = newsDAO.getNewsById(testNews.getNewsId());
        assertNotNull(retrievedNews); // Pastikan berita tidak null
        assertEquals("Berita terbaru NCT", retrievedNews.getNews()); // Pastikan nama berita sesuai
    }

    @Test
    public void testUpdateNews() {
        testNews.setNews("Berita yang diperbarui");
        newsDAO.updateNews(testNews);
        Tblnews updatedNews = newsDAO.getNewsById(testNews.getNewsId());
        assertEquals("Berita yang diperbarui", updatedNews.getNews()); // Pastikan berita diperbarui
    }

    @Test
    public void testDeleteNews() {
        newsDAO.deleteNews(testNews);
        Tblnews deletedNews = newsDAO.getNewsById(testNews.getNewsId());
        assertNull(deletedNews); // Pastikan berita sudah dihapus
    }

    @Test
    public void testGetNewsById() {
        Tblnews news = newsDAO.getNewsById(testNews.getNewsId());
        assertNotNull(news); // Pastikan berita tidak null
        assertEquals("Berita terbaru NCT", news.getNews()); // Pastikan nama berita sesuai
    }

    @Test
    public void testGetAllNews() {
        // Mengambil semua berita dari database
        List<Tblnews> newsList = newsDAO.getAllNews();
        
        // Memastikan daftar berita tidak null
        assertNotNull(newsList); 
        
        // Memastikan ada berita dalam daftar
        assertFalse(newsList.isEmpty()); 
        
        // Memastikan berita yang ditest ada dalam daftar
        boolean newsExists = newsList.stream().anyMatch(news -> news.getNewsId().equals(testNews.getNewsId()));
        assertTrue(newsExists); // Pastikan berita yang ditest ada dalam daftar
    }

    @Test
    public void testGetNewsByUnitId() {
        List<Tblnews> newsList = newsDAO.getNewsByUnitId("U1");
        assertNotNull(newsList); // Pastikan daftar berita tidak null
        assertFalse(newsList.isEmpty()); // Pastikan ada berita dalam daftar
        assertEquals("U1", newsList.get(0).getTblunit().getUnitId()); // Pastikan unit ID sesuai
    }
}
