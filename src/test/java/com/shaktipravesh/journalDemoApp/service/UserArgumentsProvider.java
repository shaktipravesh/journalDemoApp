package com.shaktipravesh.journalDemoApp.service;

import com.shaktipravesh.journalDemoApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        try {
            return Stream.of(
                    Arguments.of(User.builder().userName("spraveshshakti").password("1234").build()),
                    Arguments.of(User.builder().userName("shaktipraveshsp").password("1234").build())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
