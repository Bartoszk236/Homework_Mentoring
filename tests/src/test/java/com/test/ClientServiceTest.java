package com.test;

import com.test.entity.Address;
import com.test.entity.Client;
import com.test.service.ClientService;
import com.test.service.ExcelService;
import com.test.validator.ClientValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientServiceTest {
    private final ClientValidator mockValidator = mock(ClientValidator.class);
    private final Repository mockRepository = mock(Repository.class);
    private final ExcelService mockExcelService = mock(ExcelService.class);
    private final ClientService clientService = new ClientService(mockValidator, mockRepository, mockExcelService);
    private Client mockClient = mock(Client.class);
    private Address mockAddress = mock(Address.class);

    private static Stream<Arguments> clientsStream() {
        return Stream.of(
                Arguments.of(new Client("Jan", "Kowalski", LocalDate.of(1985, 4, 12), "jan.kowalski@example.com", "123456789", null), true),
                Arguments.of(new Client("Anna", "Nowak", LocalDate.of(1990, 9, 5), "anna.nowak@example.com", "987654321", null), true),
                Arguments.of(new Client("Piotr", "Wiśniewski", LocalDate.of(2015, 1, 22), "piotr.wisniewski@example.com", "555123456", null), false),
                Arguments.of(new Client("Maria", "Lewandowska", LocalDate.of(1995, 6, 30), "maria.lewandowska@example.com", "444987321", null), true),
                Arguments.of(new Client("Tomasz", "Zieliński", LocalDate.of(2007, 11, 15), "tomasz.zielinski@example.com", "333222111", null), false)
        );
    }

    Client validClient = new Client("Bartosz", "Kocyło",
            LocalDate.of(2003, 5, 24), "kocylo.bartosz@gmail.com",
            "123456789",
            new Address("Warszawa", "00-200", "Marszałkowska", "10")

    );

    @Test
    void givenValidClientWhenVerifyClientCallClientValidatorAndRepository() {
        //given
        Client givenClient = validClient;
        when(mockValidator.validate(givenClient)).thenReturn(true);

        //when
        clientService.saveClient(givenClient);

        //then
        verify(mockRepository, times(1)).save(givenClient);
        verify(mockValidator, times(1)).validate(givenClient);
    }

    //invalid test method
    @Test
    void givenValidEmailWhenOldGetClientCityByEmailThenReturnCity() {
        //given
        String givenEmail = "bartosz@gmail.com";
        when(mockRepository.findClientByEmail(givenEmail))
                .thenReturn(Optional.ofNullable(mockClient));
        when(mockClient.getAddress())
                .thenReturn(mockAddress);
        when(mockAddress.getCity())
                .thenReturn("Warszawa");

        //when
        String cityResult = clientService.oldGetClientCityByEmail(givenEmail);

        //then
        assertThat(cityResult).isEqualTo("Warszawa");
    }

    //refactored test method
    @Test
    void givenValidEmailWhenGetClientCityByEmailThenReturnCity() {
        //given
        String givenEmail = "bartosz@gmail.com";
        when(mockRepository.findCityByEmail(givenEmail))
                .thenReturn("Warszawa");

        //when
        String cityResult = clientService.getClientCityByEmail(givenEmail);

        //then
        assertThat(cityResult).isEqualTo("Warszawa");
    }

    @Test
    void givenInvalidClientWhenSaveClientThenDontCallRepositorySave() {
        //given
        Client givenClient = validClient;
        givenClient.setFirstName(null);
        when(mockValidator.validate(givenClient))
                .thenReturn(false);

        //when
        clientService.saveClient(givenClient);

        //then
        verify(mockRepository, never()).save(givenClient);
    }

    @Test
    void givenAnyStringWhenGetClientCityByEmailThenCallRepositoryFindCityByEmail() {
        //when
        clientService.getClientCityByEmail(anyString());

        //then
        verify(mockRepository, times(1)).findCityByEmail(anyString());
    }

    @Test
    void givenEmailWhenGetClientCityByEmailThenCallRepositoryFindCityByEmail() {
        //given
        String givenEmail = "bartosz@gmail.com";

        //when
        clientService.getClientCityByEmail(givenEmail);

        //then
        verify(mockRepository, times(1)).findCityByEmail(givenEmail);
    }

    @Test
    void givenFileDirectoryWhenGetClientsFromExcelThenCallExcelServiceParseExcel() {
        //given
        String filename = "test.xlsx";
        File file = new File(filename);
        when(mockExcelService.parseExcel(file))
                .thenReturn(Arrays.asList(validClient, validClient));

        //when
        clientService.getClientsFromExcel(filename);

        //then
        verify(mockExcelService, times(1)).parseExcel(file);
    }

    @ParameterizedTest
    @MethodSource("clientsStream")
    void givenValidClientsWhenIsAdultThenReturnTrueOrFalse(Client client, boolean result) {
        //when
        boolean actual = clientService.isAdult(client);

        //then
        assertEquals(actual, result);
    }
}
