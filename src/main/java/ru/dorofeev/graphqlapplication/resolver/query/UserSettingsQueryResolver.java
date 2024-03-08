package ru.dorofeev.graphqlapplication.resolver.query;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import ru.dorofeev.graphqlapplication.model.entity.UserSettings;

import java.util.UUID;

@Component
public class UserSettingsQueryResolver implements GraphQLQueryResolver {
    public DataFetcherResult<UserSettings> getUserSettings(UUID id) {
        return DataFetcherResult.<UserSettings>newResult()
                .data(UserSettings.builder()
                        .id(id)
                        .build())
                .build();
    }
}