package ru.dorofeev.graphqlapplication.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.dorofeev.graphqlapplication.model.enums.ViewMode;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings extends BaseEntity {

    private UUID id;

    private User user;

    private ViewMode viewMode;
}
