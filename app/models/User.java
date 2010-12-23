package models;

import javax.persistence.Entity;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class User extends Model {
	
	public String name;
	public String email;
	public int age;
	public Blob image;

}
