import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="photos")
public class Photo
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String date;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name="photo_likes",
		joinColumns=@JoinColumn(name="photo_id"),
		inverseJoinColumns=@JoinColumn(name="user_id")
	)
	Set<User> likingUsers = new HashSet<User>();
	
	public Photo() {}
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getDate() {return date;}
	public void setDate(String date) {this.date = date;}
	
	public Set<User> getLikingUsers() {return likingUsers;}
	public void setLikingUsers(Set<User> users) {this.likingUsers = users;}
	
	public boolean verifyPhotoPossession(User friend, User liker)
	{
		for(Album album : friend.getAlbums())
		{
			if(album.getPhotos().contains(this)) return true;
		}
		
		for(Album album : liker.getAlbums())
		{
			if(album.getPhotos().contains(this)) return true;
		}
		
		return false;
	}
	
	public void linkLikingUser(User user)
	{
		for(User friend : user.getFriendsOf())
		{
			if(verifyPhotoPossession(friend, user))
			{
				likingUsers.add(user);
				break;
			}
		}
	}

	public void removeLikingUser(User user) {likingUsers.remove(user);}
	
	public String toString() {return "Photo: " + getName() + ", posted on: " + getDate();}
}
