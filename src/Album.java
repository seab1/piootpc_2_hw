import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="albums")
public class Album implements java.io.Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="album_id")
	private Set<Photo> photos = new HashSet<Photo>();
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public Set<Photo> getPhotos() {return photos;}
	public void setPhotos(Set<Photo> photos) {this.photos = photos;}
	public void addPhoto(Photo photo) {photos.add(photo);}
	public void removePhoto(Photo photo) {photos.remove(photo);}
	
	public String toString() {return "Album: " + getName() + ", " + getDescription();}
}
