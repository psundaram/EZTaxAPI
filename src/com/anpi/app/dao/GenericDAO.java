package com.anpi.app.dao;

import org.hibernate.Session;

import com.anpi.app.util.HibernateUtil;

public class GenericDAO {
	
	private Session getCurrentSession() {       
	    return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	 
	
	
	
	

}
