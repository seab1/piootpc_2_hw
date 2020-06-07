import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main
{
	Session session;

	public static void main(String[] args)
	{
		Main main = new Main();
		main.testDatabase();
		//main.removePhotosQuery();
		//main.removeAlbumsQuery();
		//main.removeUsersQuery();
		main.print();
		main.close();
	}

	public Main() {session = HibernateUtil.getSessionFactory().openSession();}

	public void close()
	{
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void testDatabase()
	{
		User newUser = new User();
		newUser.setUsername("prethor7");
		newUser.setJoinDate("27.06.1995");
		
		Album newAlbum = new Album();
		newAlbum.setName("Wakacje");
		newAlbum.setDescription("Zdjêcia z wakacji");
		
		Photo newPhoto = new Photo();
		newPhoto.setName("Nad morzem");
		newPhoto.setDate("01.08.2020");
		Photo newerPhoto = new Photo();
		newerPhoto.setName("W górach");
		newerPhoto.setDate("04.08.2020");
		
		newAlbum.addPhoto(newPhoto);
		newAlbum.addPhoto(newerPhoto);
		newUser.addAlbum(newAlbum);
		
		newUser.likePhoto(newPhoto);
		newPhoto.linkLikingUser(newUser);
		newUser.likePhoto(newerPhoto);
		newerPhoto.linkLikingUser(newUser);
		
		//newUser.unlikePhoto(newerPhoto);
		//newerPhoto.removeLikingUser(newUser);
	
		Transaction transaction = session.beginTransaction();
		session.save(newUser);
		session.save(newAlbum);
		session.save(newPhoto);
		session.save(newerPhoto);
		transaction.commit();
	}
	
	private void removePhotosQuery()
	{
		String hql = "FROM Photo";
        Query query = session.createQuery(hql);
        List<Photo> allPhotos = query.list();
        Transaction transaction = session.beginTransaction();
        for(Photo photo : allPhotos)
        {
        	session.delete(photo);
        }
        transaction.commit();
	}
	
	private void removeAlbumsQuery()
	{
		String hql = "FROM Album";
        Query query = session.createQuery(hql);
        List<Album> allAlbums = query.list();
        Transaction transaction = session.beginTransaction();
        for(Album album : allAlbums)
        {
        	session.delete(album);
        }
        transaction.commit();
	}
	
	private void removeUsersQuery()
	{
		String hql = "FROM User";
        Query query = session.createQuery(hql);
        List<User> allUsers = query.list();
        Transaction transaction = session.beginTransaction();
        for(User user : allUsers)
        {
        	session.delete(user);
        }
        transaction.commit();
	}
	
	private void print()
	{
		Criteria crit = session.createCriteria(User.class);
		List<User> users = crit.list();

		System.out.println("Printing social media website database:");
		for (User user : users)
		{
			System.out.println(user);
			for (Album album : user.getAlbums())
			{
				System.out.println("    " + album);
				for (Photo photo : album.getPhotos())
				{
					System.out.println("        " + photo);
				}
			}
		}
	}
}
