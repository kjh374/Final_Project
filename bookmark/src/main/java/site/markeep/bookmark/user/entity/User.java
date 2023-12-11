package site.markeep.bookmark.user.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import site.markeep.bookmark.folder.entity.Folder;
import site.markeep.bookmark.follow.entity.Follower;
import site.markeep.bookmark.follow.entity.Following;
import site.markeep.bookmark.pinn.entity.Pin;
import site.markeep.bookmark.user.dto.request.JoinRequestDTO;
import site.markeep.bookmark.user.dto.response.LoginResponseDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString @EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
@Entity
@DynamicInsert
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    @CreationTimestamp
    @Column(nullable = false, name = "join_date")
    private LocalDateTime joinDate;

    private String profileImage;

    @Column(unique = true)
    private String refreshToken;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean autoLogin;

    @Column(nullable = false, unique = true)
    private String nickName;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Following> followings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Pin> pins = new ArrayList<>();

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
