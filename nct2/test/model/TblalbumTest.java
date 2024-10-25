package model;

import model.Tblalbum;
import model.Tblunit;
import dao.AlbumDAO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import java.util.Arrays;
import util.FacesContextWrapper;

public class TblalbumTest {

    private Tblalbum tblalbum;

    @Mock
    private AlbumDAO albumDAO;

    @Mock
    private Part albumImageFile;

    @Mock
    private FacesContextWrapper facesContextWrapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tblalbum = new Tblalbum();
        tblalbum.albumDAO = albumDAO;

        // Set the mock wrapper
        FacesUtil.setFacesContextWrapper(facesContextWrapper);
    }


    @Test
    public void testGetNct127Albums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAlbumsByUnitId("u1")).thenReturn(mockAlbums);
        List<Tblalbum> result = tblalbum.getNct127Albums();
        assertSame(mockAlbums, result);
        verify(albumDAO).getAlbumsByUnitId("u1");
    }

    @Test
    public void testGetNctDreamAlbums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAlbumsByUnitId("u2")).thenReturn(mockAlbums);
        List<Tblalbum> result = tblalbum.getNctDreamAlbums();
        assertSame(mockAlbums, result);
        verify(albumDAO).getAlbumsByUnitId("u2");
    }

    @Test
    public void testGetNctUAlbums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAlbumsByUnitId("u3")).thenReturn(mockAlbums);
        List<Tblalbum> result = tblalbum.getNctUAlbums();

        assertSame(mockAlbums, result);
        verify(albumDAO).getAlbumsByUnitId("u3");
    }

    @Test
    public void testGetWayVAlbums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAlbumsByUnitId("u4")).thenReturn(mockAlbums);

        List<Tblalbum> result = tblalbum.getWayVAlbums();

        assertSame(mockAlbums, result);
        verify(albumDAO).getAlbumsByUnitId("u4");
    }

    @Test
    public void testGetAlbums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAllAlbums()).thenReturn(mockAlbums);

        // Test when albums is null
        tblalbum.albums = null;
        List<Tblalbum> result = tblalbum.getAlbums();
        assertSame(mockAlbums, result);
        verify(albumDAO).getAllAlbums();

        // Test when albums is not null
        tblalbum.albums = new ArrayList<>();
        result = tblalbum.getAlbums();
        assertSame(tblalbum.albums, result);
        verifyNoMoreInteractions(albumDAO);
    }

   


    @Test
    public void testUploadAlbumImage() throws IOException {
        Tblalbum targetAlbum = new Tblalbum();
        byte[] mockFileContent = {1, 2, 3, 4, 5};
        InputStream mockInputStream = new ByteArrayInputStream(mockFileContent);

        when(albumImageFile.getInputStream()).thenReturn(mockInputStream);
        when(albumImageFile.getSize()).thenReturn((long) mockFileContent.length);

        tblalbum.setAlbumImageFile(albumImageFile);
        tblalbum.uploadAlbumImage(targetAlbum);

        assertArrayEquals(mockFileContent, targetAlbum.getAlbumImg());

        // Pengujian untuk kasus error
        doThrow(new IOException("Test Exception")).when(albumImageFile).getInputStream();
        tblalbum.uploadAlbumImage(targetAlbum);
        verify(facesContextWrapper).addMessage(eq(null), any(FacesMessage.class));
    }

     @Test
    public void testHandleFileUpload() throws IOException {
        Tblalbum album = new Tblalbum();
        album.setAlbumId(1);

        when(albumImageFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}));
        when(albumImageFile.getSize()).thenReturn(5L);

        tblalbum.setAlbumImageFile(albumImageFile);
        tblalbum.handleFileUpload(album);

        verify(albumDAO).updateAlbum(album);
        assertNotNull(album.getAlbumImg());
        assertEquals(5, album.getAlbumImg().length);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message -> 
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Gambar album berhasil diperbarui".equals(message.getDetail())
        ));

        // Pengujian untuk kasus error
        doThrow(new IOException("Test Exception")).when(albumImageFile).getInputStream();
        tblalbum.handleFileUpload(album);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message -> 
            message.getSeverity() == FacesMessage.SEVERITY_ERROR &&
            "Error".equals(message.getSummary()) &&
            "Gagal mengunggah gambar: Test Exception".equals(message.getDetail())
        ));
    }


    @Test
    public void testAddNewAlbum() throws IOException {
        Tblalbum newAlbum = new Tblalbum();
        newAlbum.setAlbumName("New Test Album");
        newAlbum.setTblunit(new Tblunit());
        newAlbum.getTblunit().setUnitId("U1");

        when(albumImageFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}));
        when(albumImageFile.getSize()).thenReturn(5L);

        tblalbum.setNewAlbum(newAlbum);
        tblalbum.setAlbumImageFile(albumImageFile);
        tblalbum.addNewAlbum();

        verify(albumDAO).addAlbum(newAlbum);
        assertNotNull(newAlbum.getAlbumImg());
        assertEquals(5, newAlbum.getAlbumImg().length);

        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(albumDAO).addAlbum(any(Tblalbum.class));
        tblalbum.addNewAlbum();
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class));
    }
    
     @Test
    public void testUpdateAlbum() {
        Tblalbum album = new Tblalbum();
        album.setAlbumId(1);
        album.setAlbumName("Test Album");

        tblalbum.updateAlbum(album);

        verify(albumDAO).updateAlbum(album);
        verify(facesContextWrapper).addMessage(eq(null), argThat(message ->
            message.getSeverity() == FacesMessage.SEVERITY_INFO &&
            "Success".equals(message.getSummary()) &&
            "Album berhasil diperbarui".equals(message.getDetail())
        ));

        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(albumDAO).updateAlbum(any(Tblalbum.class));
        tblalbum.updateAlbum(album);
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class));
    }


   @Test
    public void testDeleteAlbum() {
        Tblalbum album = new Tblalbum();
        album.setAlbumId(1);

        tblalbum.albums = new ArrayList<>(Arrays.asList(album));
        tblalbum.deleteAlbum(album);

        verify(albumDAO).deleteAlbum(album);
        assertTrue(tblalbum.albums.isEmpty());
        verify(facesContextWrapper).addMessage(eq(null), any(FacesMessage.class));

        // Pengujian untuk kasus error
        doThrow(new RuntimeException("Test Exception")).when(albumDAO).deleteAlbum(any(Tblalbum.class));
        tblalbum.deleteAlbum(album);
        verify(facesContextWrapper, times(2)).addMessage(eq(null), any(FacesMessage.class));
    }


    @Test
    public void testGetAlbumImgBase64() {
        byte[] mockImg = {1, 2, 3, 4, 5};
        tblalbum.setAlbumImg(mockImg);

        String result = tblalbum.getAlbumImgBase64();

        assertEquals(Base64.getEncoder().encodeToString(mockImg), result);
    }

    @Test
    public void testLoadAlbums() {
        List<Tblalbum> mockAlbums = new ArrayList<>();
        when(albumDAO.getAllAlbums()).thenReturn(mockAlbums);

        tblalbum.loadAlbums();

        verify(albumDAO).getAllAlbums();
        assertSame(mockAlbums, tblalbum.albums);
    }

    // Tests for getters and setters
    @Test
    public void testGetSetAlbumId() {
        Integer albumId = 1;
        tblalbum.setAlbumId(albumId);
        assertEquals(albumId, tblalbum.getAlbumId());
    }

    @Test
    public void testGetSetTblunit() {
        Tblunit unit = new Tblunit();
        tblalbum.setTblunit(unit);
        assertSame(unit, tblalbum.getTblunit());
    }

    @Test
    public void testGetSetAlbumImg() {
        byte[] albumImg = {1, 2, 3};
        tblalbum.setAlbumImg(albumImg);
        assertArrayEquals(albumImg, tblalbum.getAlbumImg());
    }

    @Test
    public void testGetSetAlbumName() {
        String albumName = "Test Album";
        tblalbum.setAlbumName(albumName);
        assertEquals(albumName, tblalbum.getAlbumName());
    }

    @Test
    public void testGetSetAlbumDescription() {
        String albumDescription = "Test Description";
        tblalbum.setAlbumDescription(albumDescription);
        assertEquals(albumDescription, tblalbum.getAlbumDescription());
    }

    @Test
    public void testGetSetUrl() {
        String url = "http://test.com";
        tblalbum.setUrl(url);
        assertEquals(url, tblalbum.getUrl());
    }

    @Test
    public void testGetSetUrl1() {
        String url1 = "http://test1.com";
        tblalbum.setUrl1(url1);
        assertEquals(url1, tblalbum.getUrl1());
    }

    @Test
    public void testGetSetAlbumImageFile() {
        tblalbum.setAlbumImageFile(albumImageFile);
        assertSame(albumImageFile, tblalbum.getAlbumImageFile());
    }

    @Test
    public void testGetSetNewAlbum() {
        Tblalbum newAlbum = new Tblalbum();
        tblalbum.setNewAlbum(newAlbum);
        assertSame(newAlbum, tblalbum.getNewAlbum());
    }

    @Test
    public void testGetSetUnitId() {
        String unitId = "u1";
        tblalbum.setUnitId(unitId);
        assertEquals(unitId, tblalbum.getUnitId());
    }

    @Test
    public void testGetSetSelectedAlbumId() {
        Integer selectedAlbumId = 1;
        tblalbum.setSelectedAlbumId(selectedAlbumId);
        assertEquals(selectedAlbumId, tblalbum.getSelectedAlbumId());
    }
}
