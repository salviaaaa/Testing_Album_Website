package model;

import dao.NewsDAO;
import util.FacesUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import javax.faces.application.FacesMessage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import util.FacesContextWrapper;

public class TblnewsTest {

    private Tblnews tblnews;

    @Mock
    private NewsDAO newsDAO;

    @Mock
    private Part newsImageFile;

    @Mock
    private FacesContextWrapper facesContextWrapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tblnews = new Tblnews();
        tblnews.newsDAO = newsDAO;

        // Set the mock wrapper
        FacesUtil.setFacesContextWrapper(facesContextWrapper);
    }

    @Test
    public void testConstructor() {
        Tblnews news = new Tblnews();
        assertNotNull(news.getTblunit());
        assertNotNull(news.newsDAO);
        assertNotNull(news.getNewNews());
        assertNotNull(news.getNewNews().getTblunit());
    }

    @Test
    public void testParameterizedConstructor() {
        Tblunit unit = new Tblunit();
        String newsContent = "Test News";
        byte[] image = {1, 2, 3};
        Tblnews news = new Tblnews(unit, newsContent, image);
        
        assertEquals(unit, news.getTblunit());
        assertEquals(newsContent, news.getNews());
        assertArrayEquals(image, news.getImage());
    }

    @Test
    public void testGetNewsList() {
        List<Tblnews> mockNews = new ArrayList<>();
        when(newsDAO.getAllNews()).thenReturn(mockNews);

        // Test when newsList is null
        tblnews.setNewsList(null);
        List<Tblnews> result = tblnews.getNewsList();
        assertSame(mockNews, result);
        verify(newsDAO).getAllNews();

        // Test when newsList is not null
        tblnews.setNewsList(new ArrayList<>());
        result = tblnews.getNewsList();
        assertSame(tblnews.getNewsList(), result);
        verifyNoMoreInteractions(newsDAO);
    }

    @Test
    public void testUpdateNews() {
        Tblnews news = new Tblnews();
        news.setNewsId(1);
        news.setNews("Test News");
        List<Tblnews> mockList = new ArrayList<>();
        mockList.add(news);
        tblnews.setNewsList(mockList);

        tblnews.updateNews(news);

        verify(newsDAO).updateNews(news);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Berita berhasil diperbarui".equals(message.getDetail())
        ));
        assertEquals(news, mockList.get(0));
    }

    @Test
    public void testUpdateNewsException() {
        Tblnews news = new Tblnews();
        doThrow(new RuntimeException("Update failed")).when(newsDAO).updateNews(news);

        tblnews.updateNews(news);

        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_ERROR &&
            "Error".equals(message.getSummary()) &&
            "Gagal memperbarui berita: Update failed".equals(message.getDetail())
        ));
    }

    @Test
    public void testDeleteNews() {
        Tblnews news = new Tblnews();
        news.setNewsId(1);
        List<Tblnews> mockList = new ArrayList<>();
        mockList.add(news);
        tblnews.setNewsList(mockList);

        tblnews.deleteNews(news);

        verify(newsDAO).deleteNews(news);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Berita berhasil dihapus".equals(message.getDetail())
        ));
        assertTrue(mockList.isEmpty());
    }

    @Test
    public void testDeleteNewsException() {
        Tblnews news = new Tblnews();
        doThrow(new RuntimeException("Delete failed")).when(newsDAO).deleteNews(news);

        tblnews.deleteNews(news);

        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_ERROR &&
            "Error".equals(message.getSummary()) &&
            "Gagal menghapus berita: Delete failed".equals(message.getDetail())
        ));
    }

    @Test
    public void testUploadNewsImage() throws IOException {
        byte[] mockFileContent = {1, 2, 3, 4, 5};
        InputStream mockInputStream = new ByteArrayInputStream(mockFileContent);

        when(newsImageFile.getInputStream()).thenReturn(mockInputStream);
        when(newsImageFile.getSize()).thenReturn((long) mockFileContent.length);

        tblnews.setNewsImageFile(newsImageFile);
        tblnews.uploadNewsImage();

        assertArrayEquals(mockFileContent, tblnews.getImage());
    }

    @Test
    public void testUploadNewsImageException() throws IOException {
        when(newsImageFile.getInputStream()).thenThrow(new IOException("Upload failed"));

        tblnews.setNewsImageFile(newsImageFile);
        tblnews.uploadNewsImage();

        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_ERROR &&
            "Error".equals(message.getSummary()) &&
            "Gagal mengunggah gambar: Upload failed".equals(message.getDetail())
        ));
    }

    @Test
    public void testAddNewNews() throws IOException {
        Tblnews newNews = new Tblnews();
        newNews.setNews("New Test News");
        newNews.setTblunit(new Tblunit());
        newNews.getTblunit().setUnitId("U1");

        byte[] mockFileContent = {1, 2, 3, 4, 5};
        when(newsImageFile.getInputStream()).thenReturn(new ByteArrayInputStream(mockFileContent));
        when(newsImageFile.getSize()).thenReturn((long) mockFileContent.length);

        tblnews.setNewNews(newNews);
        tblnews.setNewsImageFile(newsImageFile);
        tblnews.addNewNews();

        verify(newsDAO).addNews(newNews);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Berita baru berhasil ditambahkan".equals(message.getDetail())
        ));
        assertArrayEquals(mockFileContent, newNews.getImage());
        assertNotNull(tblnews.getNewNews());
        assertNotNull(tblnews.getNewNews().getTblunit());
    }

    @Test
    public void testAddNewNewsWithoutUnit() {
        Tblnews newNews = new Tblnews();
        newNews.setNews("New Test News");
        tblnews.setNewNews(newNews);

        tblnews.addNewNews();

        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_ERROR &&
            "Error".equals(message.getSummary()) &&
            "Unit ID tidak boleh kosong".equals(message.getDetail())
        ));
    }

    @Test
    public void testHandleFileUpload() throws IOException {
        byte[] mockFileContent = {1, 2, 3, 4, 5};
        when(newsImageFile.getInputStream()).thenReturn(new ByteArrayInputStream(mockFileContent));
        when(newsImageFile.getSize()).thenReturn((long) mockFileContent.length);

        tblnews.setNewsImageFile(newsImageFile);
        tblnews.handleFileUpload();

        verify(newsDAO).updateNews(tblnews);
        assertArrayEquals(mockFileContent, tblnews.getImage());
        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Gambar berita berhasil diperbarui".equals(message.getDetail())
        ));
    }

    @Test
    public void testGetNewsImgBase64() {
        byte[] mockImg = {1, 2, 3, 4, 5};
        tblnews.setImage(mockImg);

        String result = tblnews.getNewsImgBase64();

        assertEquals(Base64.getEncoder().encodeToString(mockImg), result);
    }

    @Test
    public void testGetNewsImgBase64Null() {
        tblnews.setImage(null);
        assertNull(tblnews.getNewsImgBase64());
    }

    @Test
    public void testGetNewsByUnitId() {
        String unitId = "U1";
        List<Tblnews> mockNews = new ArrayList<>();
        when(newsDAO.getNewsByUnitId(unitId)).thenReturn(mockNews);

        List<Tblnews> result = tblnews.getNewsByUnitId(unitId);

        assertSame(mockNews, result);
        verify(newsDAO).getNewsByUnitId(unitId);
    }

    // Tests for getters and setters
    @Test
    public void testGetSetNewsId() {
        Integer newsId = 1;
        tblnews.setNewsId(newsId);
        assertEquals(newsId, tblnews.getNewsId());
    }

    @Test
    public void testGetSetTblunit() {
        Tblunit unit = new Tblunit();
        tblnews.setTblunit(unit);
        assertSame(unit, tblnews.getTblunit());
    }

    @Test
    public void testGetSetNews() {
        String news = "Test News";
        tblnews.setNews(news);
        assertEquals(news, tblnews.getNews());
    }

    @Test
    public void testGetSetImage() {
        byte[] image = {1, 2, 3};
        tblnews.setImage(image);
        assertArrayEquals(image, tblnews.getImage());
    }

    @Test
    public void testGetSetNewsImageFile() {
        tblnews.setNewsImageFile(newsImageFile);
        assertSame(newsImageFile, tblnews.getNewsImageFile());
    }

    @Test
    public void testGetSetNewNews() {
        Tblnews newNews = new Tblnews();
        tblnews.setNewNews(newNews);
        assertSame(newNews, tblnews.getNewNews());
    }

    @Test
    public void testGetSetUnitId() {
        Integer unitId = 1;
        tblnews.setUnitId(unitId);
        assertEquals(unitId, tblnews.getUnitId());
    }

    @Test
    public void testGetUnitIdWithNullTblunit() {
        tblnews.setTblunit(null);
        assertNull(tblnews.getUnitId());
    }

  
}