package com.satera.cloudsort.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.satera.cloudsort.entity.Artist;
import com.satera.cloudsort.entity.Category;
import com.satera.cloudsort.entity.ImageFile;
import com.satera.cloudsort.entity.Record;
import com.satera.cloudsort.entity.Track;


public class TurkDAOHibernateImpl extends HibernateDaoSupport implements
	TurkDAO {
    public Collection<Artist> getArtists() {
	return getHibernateTemplate().loadAll(Artist.class);
    }

    public Artist getArtistById(Integer id) {
	Artist artist = (Artist) getHibernateTemplate().load(Artist.class, id);
	System.out.println("Got artist: " + artist);
	return artist;
    }

    public Artist saveArtist(Artist artist) {
	getHibernateTemplate().saveOrUpdate(artist);
	return artist;
    }

    public Record getRecordById(Integer id) {
	Record record = (Record) getHibernateTemplate().load(Record.class, id);
	System.out.println("Got record: " + record);
	return record;
    }

    public Record saveRecord(Record record) {
	getHibernateTemplate().saveOrUpdate(record);
	return record;
    }

    public Track getTrackById(Integer id) {
	Track track = (Track) getHibernateTemplate().load(Track.class, id);
	System.out.println("Got track: " + track);
	return track;
    }

    public List<Record> searchRecordsByTitle(String title) {
	return getHibernateTemplate().findByNamedQuery("record.titleLike",
		"%" + title + "%");
    }

    public ImageFile saveImageFile(ImageFile imageFile) {
	getHibernateTemplate().saveOrUpdate(imageFile);
	return imageFile;
    }

    public Category saveCategory(Category category) {
	getHibernateTemplate().saveOrUpdate(category);
	return category;
    }
    
    
    public Category getCategoryById(Integer id) {
	Category category = (Category) getHibernateTemplate().load(Category.class, id);
	System.out.println("Got category: " + category);
	return category;
    }   
    
    public Category getCategoryByName(String name) {
	Category category = null;
	List<Category> list = getHibernateTemplate().find("from Category where name=?", name);  
	if(list!=null&&list.size()>0){
	    category = list.get(0);
	}
	
	return category;
    } 
    
    
    
    public Category getCategoryByCategoryCode (String categoryCode) {
	Category category = null;
	List<Category> list = getHibernateTemplate().find("from Category where categoryCode=?", categoryCode);  
	if(list!=null&&list.size()>0){
	    category = list.get(0);
	}
	return category;
    }

    
    public List<Category> getTopLevelCategories() {
	List<Category> list = getHibernateTemplate().find("from Category where parentID is null");  
	return list;
    }     
    
    
}