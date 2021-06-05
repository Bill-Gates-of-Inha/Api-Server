package bill_gates_of_inha.dto;

import bill_gates_of_inha.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @AllArgsConstructor
    @Builder
    @Getter
    public static class User {
        private Long id;
        private String userId;
        private String name;
        private String address;
        private Double score;

        public static User toDto(bill_gates_of_inha.domain.User user) {

            return User.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .name(user.getName())
                    .address(user.getAddress())
                    .score(user.getScore())
                    .build();
        }
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class SignUpResult {
        private Boolean result;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Creation {
        private String userId;
        private String password;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Update {
        private String name;
        private String address;
        private Double score;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class SignIn {
        private String userId;
        private String password;
    }
}
