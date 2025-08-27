package com.MultiTenantUserManagementSystem.service;

import com.MultiTenantUserManagementSystem.client.ExternalIdentityProviderFeignClient;
import com.MultiTenantUserManagementSystem.dto.CreateUserRequest;
import com.MultiTenantUserManagementSystem.dto.UserCreateBulkResponse;
import com.MultiTenantUserManagementSystem.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.STATUS_OPERATION_FAILED;
import static com.MultiTenantUserManagementSystem.utils.StaticsSheet.STATUS_OPERATION_SUCCESS;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private ExternalIdentityProviderFeignClient externalIdentityProviderFeignClient;
    @InjectMocks
    private UserService userService;

    @Test
    void givenNullRequestWhenCreateUserBulkThenReturnEmptyList() {
        //when
        var out = userService.createUserBulk("xyz123", null);

        //then
        assertThat(out).isEmpty();
        verifyNoInteractions(externalIdentityProviderFeignClient);
    }

    @Test
    void givenEmptyListWhenCreateUserBulkThenReturnEmptyList() {
        //when
        var out = userService.createUserBulk("t1", List.of());

        //then
        assertThat(out).isEmpty();
        verifyNoInteractions(externalIdentityProviderFeignClient);
    }

    @Test
    void givenValidRequestWhenCreateUserBulkThenReturnUserResponse() {
        //given
        String tenant = "t1";
        var r0 = mock(CreateUserRequest.class);
        var r1 = mock(CreateUserRequest.class);
        var r2 = mock(CreateUserRequest.class);

        when(externalIdentityProviderFeignClient.createUser(tenant, r0)).thenReturn(mock(UserResponse.class));
        when(externalIdentityProviderFeignClient.createUser(tenant, r1)).thenReturn(mock(UserResponse.class));
        when(externalIdentityProviderFeignClient.createUser(tenant, r2)).thenReturn(mock(UserResponse.class));

        //when
        var out = userService.createUserBulk(tenant, List.of(r0, r1, r2));

        //then
        assertThat(out).hasSize(3);
        assertThat(out).extracting(UserCreateBulkResponse::indexInQueue).containsExactly(0, 1, 2);
        assertThat(out).extracting(UserCreateBulkResponse::status)
                .containsExactly(STATUS_OPERATION_SUCCESS, STATUS_OPERATION_SUCCESS, STATUS_OPERATION_SUCCESS);

        verify(externalIdentityProviderFeignClient).createUser(tenant, r0);
        verify(externalIdentityProviderFeignClient).createUser(tenant, r1);
        verify(externalIdentityProviderFeignClient).createUser(tenant, r2);
        verifyNoMoreInteractions(externalIdentityProviderFeignClient);
    }

    @Test
    void givenOneExceptionResponseWhenCreateUserBulkThenReturnUserResponseWithOneStatusFailed() {
        //given
        String tenant = "t1";
        var r0 = mock(CreateUserRequest.class);
        var r1 = mock(CreateUserRequest.class);
        var r2 = mock(CreateUserRequest.class);

        when(externalIdentityProviderFeignClient.createUser(tenant, r0)).thenReturn(mock(UserResponse.class));
        when(externalIdentityProviderFeignClient.createUser(tenant, r1)).thenThrow(new RuntimeException("boom"));
        when(externalIdentityProviderFeignClient.createUser(tenant, r2)).thenReturn(mock(UserResponse.class));

        //when
        var out = userService.createUserBulk(tenant, List.of(r0, r1, r2));

        //then
        assertThat(out).hasSize(3);
        assertThat(out).extracting(UserCreateBulkResponse::indexInQueue).containsExactly(0, 1, 2);
        assertThat(out).extracting(UserCreateBulkResponse::status)
                .containsExactly(STATUS_OPERATION_SUCCESS, STATUS_OPERATION_FAILED, STATUS_OPERATION_SUCCESS);

        verify(externalIdentityProviderFeignClient).createUser(tenant, r0);
        verify(externalIdentityProviderFeignClient).createUser(tenant, r1);
        verify(externalIdentityProviderFeignClient).createUser(tenant, r2);
        verifyNoMoreInteractions(externalIdentityProviderFeignClient);
    }

    @Test
    void givenOneNullResponseWhenCreateUserBulkThenReturnUserResponseWithOneStatusFailed() {
        //given
        String tenant = "t1";
        var r0 = mock(CreateUserRequest.class);
        var r1 = mock(CreateUserRequest.class);
        var r2 = mock(CreateUserRequest.class);

        when(externalIdentityProviderFeignClient.createUser(tenant, r0)).thenReturn(mock(UserResponse.class));
        when(externalIdentityProviderFeignClient.createUser(tenant, r1)).thenReturn(null);
        when(externalIdentityProviderFeignClient.createUser(tenant, r2)).thenReturn(mock(UserResponse.class));

        //when
        var out = userService.createUserBulk(tenant, List.of(r0, r1, r2));

        //then
        assertThat(out).hasSize(3);
        assertThat(out).extracting(UserCreateBulkResponse::indexInQueue).containsExactly(0, 1, 2);
        assertThat(out).extracting(UserCreateBulkResponse::status)
                .containsExactly(STATUS_OPERATION_SUCCESS, STATUS_OPERATION_FAILED, STATUS_OPERATION_SUCCESS);
    }
}
