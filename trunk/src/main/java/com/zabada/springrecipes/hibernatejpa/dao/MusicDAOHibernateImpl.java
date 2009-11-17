package com.zabada.springrecipes.hibernatejpa.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zabada.springrecipes.hibernatejpa.entity.Artist;
import com.zabada.springrecipes.hibernatejpa.entity.ImageFile;
import com.zabada.springrecipes.hibernatejpa.entity.Record;
import com.zabada.springrecipes.hibernatejpa.entity.Track;

/**
 * Implementation of MusicDAO implemented by extending Spring's
 * HibernateDaoSupport
 * 
 * @author roblambert
 */
public class MusicDAOHibernateImpl extends HibernateDaoSupport implements
	MusicDAO {
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
}