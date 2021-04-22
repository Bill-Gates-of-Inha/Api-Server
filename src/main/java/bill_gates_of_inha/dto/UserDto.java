package bill_gates_of_inha.dto;

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
    public static class SignIn {
        private String userId;
        private String password;
    }
}
