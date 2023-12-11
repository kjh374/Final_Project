package site.markeep.bookmark.user.dto.request;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private boolean autoLoginCheck;

}
