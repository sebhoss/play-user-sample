package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

/**
 * <p>User controller which supports basic HTTP operations like {@link #show(Long) GET}, {@link #persist(User) POST},
 * {@link #update(Long) PUT} and {@link #delete(Long) DELETE}.</p>
 * 
 * <p>Additionally it shows an index page which lists all known {@link User users}.</p>
 */
public class Users extends Controller {

	/**
	 * Shows a list of all users in the DB.
	 */
    public static void index() {
    	final List<User> users = User.all().fetch();
        
    	render(users);
    }
    
    /**
     * Persists a new user. 
     * 
     * @param user	The user to persist.
     */
    public static void persist(User user) {
    	// Persist the user
        user.save();
        
        // Show the user
        show(user.id);
    }

    /**
     * Updates a single user.
     * 
     * @param id	The ID of the user to update.
     */
    public static void update(Long id) {
    	// Fetch from user from DB
        User user = safeFindById(id);
        
        // Set new values
        user.edit("user", params.all());

        // Persist user
        user.save();
        
        // Show updated user
        show(id);
    }

    /**
     * Deletes a single user.
     * 
     * @param id	The ID of the user to delete.
     */
    public static void delete(Long id) {
    	// Fetch user from DB
    	User deletable = safeFindById(id);
        
    	// Delete user
        deletable.delete();
        
        // Show index page
        index();
    }

    /**
     * Shows a single user.
     * 
     * @param id	The ID of the user to show.
     */
    public static void show(Long id)  {
    	// Fetch user from DB
        User user = safeFindById(id);
        
        // Show user
        render(user);
    }
    
    /**
     * Offers the image attachment of a single user to download.
     * 
     * @param id	The ID of the user whose image should be shown.
     */
    public static void image(Long id) {
    	// Fetch user from DB
        User user = safeFindById(id);

        // Check image availability
        if (user.image != null && user.image.exists()) {
        	// Send image
        	response.contentType = user.image.type();
        	renderBinary(user.image.get(), user.image.length());
        } else {
        	// Send 404
        	notFound();
        }
    }
    
    /**
     * Tries to fetch an {@link User} from the database. If the user could not be found
     * a 404 error will be thrown.
     * 
     * @param id	The ID of the user to fetch.
     * @return		The user with the given ID or a 404.
     */
    private static User safeFindById(Long id) {
    	User user = User.findById(id);
    	
    	notFoundIfNull(user);
    	
    	return user;
    }

}