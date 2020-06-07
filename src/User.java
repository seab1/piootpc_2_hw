import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="users")
public class User implements java.io.Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String username;
	
	@Column
	private String joinDate;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Album> albums = new HashSet<Album>();
	
	@ManyToMany(mappedBy="likingUsers", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Photo> likedPhotos = new HashSet<Photo>();
	
	@ManyToMany
	@JoinTable(name="friends",
	 joinColumns=@JoinColumn(name="friend1_id"),
	 inverseJoinColumns=@JoinColumn(name="friend2_id")
	)
	private Set<User> friendsOf = new HashSet<User>();
	
	@ManyToMany(mappedBy="friendsOf")
	private Set<User> friendOf = new HashSet<User>(); 
	
	public User() {}
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getJoinDate() {return joinDate;}
	public void setJoinDate(String joinDate) {this.joinDate = joinDate;}
	
	public Set<Album> getAlbums() {return albums;}
	public void setAlbums(Set<Album> albums) {this.albums = albums;}
	public void addAlbum(Album album) {albums.add(album);}
	public void removeAlbum(Album album) {albums.remove(album);}
	
	public Set<Photo> getLikedPhotos() {return likedPhotos;}
	public void setLikedPhotos(Set<Photo> photos) {this.likedPhotos = photos;}
	public void likePhoto(Photo photo) {likedPhotos.add(photo);}
	public void unlikePhoto(Photo photo) {likedPhotos.remove(photo);}
	
	public Set<User> getFriendsOf() {return friendsOf;}
	public void setFriendsOf(Set<User> friends) {this.friendsOf = friends;}
	public void makeFriend(User friend) {friendsOf.add(friend);}
	public void unfriend(User friend) {friendsOf.remove(friend);}
	
	public String toString() {return "User: " + getUsername() + ", joined on: " + getJoinDate();}
}
