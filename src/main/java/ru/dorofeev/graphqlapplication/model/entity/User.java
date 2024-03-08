package ru.dorofeev.graphqlapplication.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.dorofeev.graphqlapplication.model.enums.UserStatusType;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private UUID id;

    private UUID externalUserId;

    private UserStatusType status;

    private UserSettings settings;
}
