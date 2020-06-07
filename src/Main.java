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
		User user1 = new User();
		user1.setUsername("prethor7");
		user1.setJoinDate("27.06.1995");
		User user2 = new User();
		user2.setUsername("gadzina13");
		user2.setJoinDate("20.02.1998");
		
		Album album1 = new Album();
		album1.setName("Wakacje");
		album1.setDescription("Zdjêcia z wakacji");
		Album album2 = new Album();
		album2.setName("Zwiedzanie");
		album2.setDescription("Zdjêcia z wycieczki do Bukaresztu");
		
		Photo photo1 = new Photo();
		photo1.setName("Nad morzem");
		photo1.setDate("01.08.2020");
		Photo photo2 = new Photo();
		photo2.setName("W górach");
		photo2.setDate("04.08.2020");
		Photo photo3 = new Photo();
		photo3.setName("Rynek");
		photo3.setDate("01.08.2019");
		
		album1.addPhoto(photo1);
		album1.addPhoto(photo2);
		album2.addPhoto(photo3);
		user1.addAlbum(album1);
		user2.addAlbum(album2);
		
		user1.makeFriend(user2);
		user2.makeFriend(user1);
		
		photo1.linkLikingUser(user1);
		photo2.linkLikingUser(user1);
		photo3.linkLikingUser(user1);
		
		//photo2.removeLikingUser(user1);
		user1.unfriend(user2);
	
		Transaction transaction = session.beginTransaction();
		session.persist(user1);
		session.persist(user2);
		session.save(album1);
		session.save(album2);
		session.save(photo1);
		session.save(photo2);
		session.save(photo3);
		transaction.commit();
		session.clear();
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
