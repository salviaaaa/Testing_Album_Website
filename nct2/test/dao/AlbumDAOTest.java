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
    /*
    @After
    public void tearDown() {
        albumDAO.deleteAlbum(testAlbum);
    }
    */

    

    @Test
    public void testAddAlbum() {
        // Buat album baru
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        byte[] albumImage = new byte[0];
        Tblalbum album = new Tblalbum(albumImage, "Album Baru", "Deskripsi Album", "http://example.com", unit, "<iframe></iframe>");
        
        // Tambah album
        albumDAO.addAlbum(album);
        
        // Verifikasi dengan mengambil data
        List<Tblalbum> albumList = albumDAO.getAllAlbums();
        assertTrue(albumList.stream().anyMatch(a -> a.getAlbumName().equals("Album Baru")));
    }

    @Test
    public void testUpdateAlbum() {
        // Buat dan tambah album baru
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        byte[] albumImage = new byte[0];
        Tblalbum album = new Tblalbum(albumImage, "Album Original", "Deskripsi", "http://example.com", unit, "<iframe></iframe>");
        albumDAO.addAlbum(album);
        
        // Update album
        album.setAlbumName("Album Updated");
        albumDAO.updateAlbum(album);
        
        // Verifikasi update
        Tblalbum updatedAlbum = albumDAO.getAlbumById(album.getAlbumId());
        assertEquals("Album Updated", updatedAlbum.getAlbumName());
    }

    @Test
    public void testAddAlbumExceptionCaught() {
        // Buat album dengan data tidak valid
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        byte[] albumImage = new byte[0];
        Tblalbum invalidAlbum = new Tblalbum(albumImage, null, "Deskripsi", "http://example.com", unit, "<iframe></iframe>");
        
        // Tambah album
        albumDAO.addAlbum(invalidAlbum);
        
        // Verifikasi dengan mengambil data
        List<Tblalbum> albumList = albumDAO.getAllAlbums();
        assertTrue(albumList.stream().noneMatch(a -> a.getAlbumName() == null));
    }

    @Test
    public void testUpdateAlbumNegativeCase() {
        // Buat album dengan ID yang tidak ada
        Tblunit unit = new Tblunit();
        unit.setUnitId("U1");
        byte[] albumImage = new byte[0];
        Tblalbum invalidAlbum = new Tblalbum(albumImage, "Album Invalid", "Deskripsi", "http://example.com", unit, "<iframe></iframe>");
        invalidAlbum.setAlbumId(-1); // ID yang tidak valid
        
        // Update album
        albumDAO.updateAlbum(invalidAlbum);
        
        // Verifikasi bahwa tidak ada album dengan ID negatif
        List<Tblalbum> albumList = albumDAO.getAllAlbums();
        assertTrue(albumList.stream().noneMatch(a -> a.getAlbumId() < 0));
    }
    
}