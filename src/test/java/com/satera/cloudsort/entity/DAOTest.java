package com.satera.cloudsort.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.satera.cloudsort.dao.TurkDAO;
import com.satera.cloudsort.entity.Artist;
import com.satera.cloudsort.entity.ImageFile;
import com.satera.cloudsort.entity.Record;
import com.satera.cloudsort.entity.Track;
import com.satera.cloudsort.service.CategoryLoader;

public class DAOTest extends TestCase
{
  private TurkDAO turkDAO;
  
  public void setUp() throws Exception
  {
    super.setUp();
    ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring.xml");
    turkDAO = (TurkDAO)context.getBean("turkDAO");
    
    
    CategoryLoader categoryLoader = (CategoryLoader)context.getBean("categoryLoader");
    categoryLoader.load();
  }

  /**
   * Simple tests excersing the various methods of turkDAO
   */
  public void test()
  {
      
      String filename = "349587345834.jpg";
      com.satera.cloudsort.entity.ImageFile imageFile = new com.satera.cloudsort.entity.ImageFile();
      imageFile.setFilename(filename);
      turkDAO.saveImageFile(imageFile);
      assertNotNull(imageFile.getId()); 
      
    
      List<Category> topLevelCategories = turkDAO.getTopLevelCategories();
      
      assertNotNull(topLevelCategories);
      assertEquals(18,topLevelCategories.size());
    
    String artistName = "Tenacious D";
    Artist artist = new Artist();
    artist.setName(artistName);
    artist.setGenre("Comedy");
    turkDAO.saveArtist(artist);
    assertNotNull(artist.getId());
    
    //Save an additional Artist
    Artist artist2 = new Artist();
    artist2.setName("Spinal Tap");
    artist2.setGenre("Mock Rock");
    turkDAO.saveArtist(artist2);
    
    //Test "loadAll"
    Collection<Artist> loadedArtists = turkDAO.getArtists();
    assertEquals(2, loadedArtists.size());
    for(Artist a:loadedArtists)
    {
      System.out.println(a);      
    }
    
    //Create some records
    String record1Title = "The Pick of Destiny";
    String[] record1Tracks = {"Kickapoo", "Classico", "Baby", "Destiny", "History"};
    String record2Title = "Tenacious D";
    String[] record2Tracks = {"Tribute", "Wonderboy", "Dio"};
    
    Record record1 = createRecord(artist, record1Title, record1Tracks);    
    Record record2 = createRecord(artist, record2Title, record2Tracks); 
    
    //Load back artist1
    Artist artistOne = turkDAO.getArtistById(artist.getId());
    assertEquals("Tenacious D", artistOne.getName());
    assertEquals("Comedy", artistOne.getGenre());
    
    //Load back record 1
    Record recordOne = turkDAO.getRecordById(record1.getId());
    assertEquals(record1Title, recordOne.getTitle());
    assertEquals(artistName, recordOne.getArtist().getName());

    assertEquals(record1Tracks[0], recordOne.getTracks().get(0).getTitle());
    assertEquals(record1Tracks[1], recordOne.getTracks().get(1).getTitle());

    //Load back record 2
    Record recordTwo = turkDAO.getRecordById(record2.getId());
    assertEquals(record2Title, recordTwo.getTitle());
    assertEquals(artistName, recordTwo.getArtist().getName());

    assertEquals(record2Tracks[0], recordTwo.getTracks().get(0).getTitle());
    assertEquals(record2Tracks[1], recordTwo.getTracks().get(1).getTitle());  

    //Test Loading a Track By Id and Make sure that all associations exist
    Track loadedTrack = turkDAO.getTrackById(recordTwo.getTracks().get(1).getId());
    assertEquals("Wonderboy", loadedTrack.getTitle());
    assertEquals("Tenacious D", loadedTrack.getRecord().getTitle());
    assertEquals("Tenacious D", loadedTrack.getRecord().getArtist().getName());
    assertEquals("Dio", loadedTrack.getRecord().getTracks().get(2).getTitle());
  
    //Test "searchRecordsByTitle"
    List<Record> recordsSearch1 = turkDAO.searchRecordsByTitle("Destiny");
    assertEquals(1, recordsSearch1.size());
    
    List<Record> recordsSearch2 = turkDAO.searchRecordsByTitle("e");
    System.out.println("Searched records: " + recordsSearch2);
    assertEquals(2, recordsSearch2.size());    
    

    
    
  }
  
  private Record createRecord(Artist artist, String title, String[] tracks)
  {
    Record record = new Record();
    record.setArtist(artist);
    record.setTitle(title);

    List<Track> tracks1 = new ArrayList<Track>();
    for(String trackTitle:tracks)
    {
      Track track = new Track();
      track.setTitle(trackTitle);
      track.setRecord(record);
      tracks1.add(track);
    }
    record.setTracks(tracks1);
    turkDAO.saveRecord(record);
    assertNotNull(record.getId());
    return record;
  }
}