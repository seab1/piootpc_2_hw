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
	
	public String toString() {return "User: " + getUsername() + ", joined on: " + getJoinDate();}
}
