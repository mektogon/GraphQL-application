package ru.dorofeev.graphqlapplication.model.mutation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.dorofeev.graphqlapplication.model.enums.UserStatusType;
import ru.dorofeev.graphqlapplication.model.enums.ViewMode;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingMutationRequest {

    private UUID userId;

    private ViewMode viewMode;
}
