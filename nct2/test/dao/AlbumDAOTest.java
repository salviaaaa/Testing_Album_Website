
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Tblalbum;
import model.Tblunit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlbumDAOTest {

    private AlbumDAO albumDAO;
    private Tblalbum testAlbum;

    @Before
    public void setUp() {
        albumDAO = new AlbumDAO();
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        byte[] albumImage = new byte[0]; // Ganti dengan gambar album yang sesuai
        testAlbum = new Tblalbum(albumImage, "Test Album", "Test Description", "http://testurl.com", unit, "<iframe></iframe>");
        albumDAO.addAlbum(testAlbum); // Menambahkan album ke database
    }

    @After
    public void tearDown() {
        albumDAO.deleteAlbum(testAlbum); // Menghapus album setelah pengujian
    }

    @Test
    public void testAddAlbum() {
        // Menggunakan album yang sama
        Tblalbum retrievedAlbum = albumDAO.getAlbumById(testAlbum.getAlbumId());
        assertNotNull(retrievedAlbum); // Pastikan album tidak null
        assertEquals("Test Album", retrievedAlbum.getAlbumName()); // Pastikan nama album sesuai
    }

    @Test
    public void testUpdateAlbum() {
        testAlbum.setAlbumName("Updated Album");
        albumDAO.updateAlbum(testAlbum);
        Tblalbum updatedAlbum = albumDAO.getAlbumById(testAlbum.getAlbumId());
        assertEquals("Updated Album", updatedAlbum.getAlbumName()); // Pastikan nama album diperbarui
    }

    @Test
    public void testDeleteAlbum() {
        albumDAO.deleteAlbum(testAlbum);
        Tblalbum deletedAlbum = albumDAO.getAlbumById(testAlbum.getAlbumId());
        assertNull(deletedAlbum); // Pastikan album sudah dihapus
    }

    @Test
    public void testGetAlbumById() {
        Tblalbum album = albumDAO.getAlbumById(testAlbum.getAlbumId());
        assertNotNull(album); // Pastikan album tidak null
        assertEquals("Test Album", album.getAlbumName()); // Pastikan nama album sesuai
    }

    @Test
    public void testGetAllAlbums() {
        // Mengambil semua album dari database
        List<Tblalbum> albums = albumDAO.getAllAlbums();
        
        // Memastikan daftar album tidak null
        assertNotNull(albums); 
        
        // Memastikan ada album dalam daftar
        assertFalse(albums.isEmpty()); 
        
        // Memastikan album yang ditest ada dalam daftar
        boolean albumExists = albums.stream().anyMatch(album -> album.getAlbumId().equals(testAlbum.getAlbumId()));
        assertTrue(albumExists); // Pastikan album yang ditest ada dalam daftar
    }

    @Test
    public void testGetAlbumsByUnitId() {
        List<Tblalbum> albums = albumDAO.getAlbumsByUnitId("U1");
        assertNotNull(albums); // Pastikan daftar album tidak null
        assertFalse(albums.isEmpty()); // Pastikan ada album dalam daftar
        assertEquals("U1", albums.get(0).getTblunit().getUnitId()); // Pastikan unit ID sesuai
    }

    @Test
    public void testUpdateAlbumWithInvalidId() {
        Tblalbum nonExistentAlbum = new Tblalbum();
        nonExistentAlbum.setAlbumId(-1); // ID yang tidak ada
        nonExistentAlbum.setAlbumName("Non-existent Album");
        albumDAO.updateAlbum(nonExistentAlbum);
        Tblalbum result = albumDAO.getAlbumById(-1);
        assertNull(result); // Pastikan album tidak ada
    }
}