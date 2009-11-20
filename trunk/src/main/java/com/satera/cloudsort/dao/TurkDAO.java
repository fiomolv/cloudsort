package com.satera.cloudsort.dao;

import java.util.Collection;
import java.util.List;

import com.satera.cloudsort.entity.Artist;
import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.entity.ImageFile;
import com.satera.cloudsort.entity.Record;
import com.satera.cloudsort.entity.Track;

public interface TurkDAO
{
  public Collection<Artist> getArtists();

  /**
   * Get a Artist Object given the id
   * @param id
   */
  public Artist getArtistById(Integer id);

  /**
   * Save an Artist Object
   */
  public Artist saveArtist(Artist artist);

  /**
   * Get a Record given the id
   * @param id
   */
  public Record getRecordById(Integer id);

  /**
   * Save a record
   * @param record
   */
  public Record saveRecord(Record record);

  /**
   * Get a Track given the id
   * @param id
   * @return
   */
  public Track getTrackById(Integer id);

  /**
   * Search records given the title of the record
   * @param title
   */
  public List<Record> searchRecordsByTitle(String title);
  
  
  
  /**
   * Save an ImageFile Object
   */
  public ImageFile saveImageFile(ImageFile imageFile);

  
  /**
   * Save a Aategory Object
   */
  public Category saveCategory(Category category);
  
  /**
   * Get a Category given the id
   * @param id
   */
  public Category getCategoryById(Integer id);

  
  
  /**
   * Get a Category given the categoryCode
   * @param id
   */
  public Category getCategoryByCategoryCode(String categoryCode);
  
  /**
   * Get a Category given the categoryCode
   * @param id
   */
  public Category getCategoryByName(String name);

  /**
   * Get a top level categories
   */
  public List<Category> getTopLevelCategories();
 
  

  /**
   * Get categories
   */
  public List<Category> getCategories(String topLevelCategoryName);
 
      
  
}
