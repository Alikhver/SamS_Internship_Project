package com.alikhver.web.facade;


import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.UserService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.web.WebApplication;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exception.organisation.NoOrganisationFoundException;
import com.alikhver.web.exception.organisation.OrganisationAlreadyExistsException;
import com.alikhver.web.exception.organisation.OrganisationIsAlreadyLaunchedException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class OrganisationFacadeTest {
    @SpyBean
    private OrganisationFacade organisationFacade;
    @MockBean
    private OrganisationService organisationService;
    @MockBean
    private UserService userService;
    @MockBean
    private WorkerService workerService;
    @MockBean
    private UtilityService utilityService;

    @Before
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new org.springframework.security.core.userdetails.User("redactor", "password", List.of(new SimpleGrantedAuthority("REDACTOR"))),
                        "password", List.of(new SimpleGrantedAuthority("REDACTOR"))
                ));
    }

    @Test
    public void getOrganisationWhenExistsTest() {
        //Given
        when(organisationService.getOrganisation(1L)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()));
        //When
        GetOrganisationResponse actual = organisationFacade.getOrganisation(1L);

        //Then
        GetOrganisationResponse expected = GetOrganisationResponse.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .build();

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.isActive(), actual.isActive());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void getOrganisationWhenNotExistsTest() {
        //Given
        when(organisationService.getOrganisation(2L)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class,
                () -> organisationFacade.getOrganisation(2L)
        );


        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void createOrganisationWhenOrganisationAlreadyExists() {
        //Given
        CreateOrganisationRequest request = CreateOrganisationRequest.builder()
                .name("OrgansiationName")
                .address("OrgansiationName")
                .description("Orgnonodknfodsnfdsnfnsfnsdnfos")
                .redactorLogin("loginlogin123")
                .redactorPassword("loginloginloginloginloginlogin123")
                .build();

        when(organisationService.existsOrganisationByName(request.getName())).thenReturn(true);

        //When
        Exception e = assertThrows(OrganisationAlreadyExistsException.class,
                () -> organisationFacade.createOrganisation(request)
        );


        String expected = "OrganisationAlreadyExistsException.msg";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void createOrganisationWhenUserAlready() {
        //Given
        CreateOrganisationRequest request = CreateOrganisationRequest.builder()
                .name("OrganisationName")
                .address("OrganisationName")
                .description("OrganisationName")
                .redactorLogin("loginlogin123")
                .redactorPassword("loginloginloginloginloginlogin123")
                .build();

        when(organisationService.existsOrganisationByName(request.getName())).thenReturn(false);
        when(userService.existsUserByLogin(request.getRedactorLogin())).thenReturn(true);

        //When
        Exception e = assertThrows(UserAlreadyExistsException.class,
                () -> organisationFacade.createOrganisation(request)
        );


        String expected = "UserAlreadyExistsException.msg.login";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void createOrganisationWhenDataCorrect() {
        //Given
        CreateOrganisationRequest request = CreateOrganisationRequest.builder()
                .name("OrganisationName")
                .address("OrganisationName")
                .description("OrganisationName")
                .redactorLogin("loginlogin123")
                .redactorPassword("loginloginloginloginloginlogin123")
                .build();

        Organisation organisation = Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();

        when(organisationService.existsOrganisationByName(request.getName())).thenReturn(false);
        when(organisationService.saveOrganisation(any())).thenReturn(organisation);
        when(userService.existsUserByLogin(request.getRedactorLogin())).thenReturn(false);

        //When
        var actual = organisationFacade.createOrganisation(request);

        //Then
        Assertions.assertEquals(organisation.getDescription(), actual.getDescription());
        Assertions.assertEquals(organisation.getAddress(), actual.getAddress());
        Assertions.assertEquals(organisation.getId(), actual.getId());
    }

    @Test
    public void getOrganisationsWhenPageIncorrect() {
        //Given
        when(organisationService.getAll(any())).thenReturn(new PageImpl<>(List.of(
                Organisation.builder()
                        .id(2L)
                        .name("Org123456")
                        .isActive(true)
                        .description("123457890098712345678900")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password1")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build(),
                Organisation.builder()
                        .id(34L)
                        .name("Org12334")
                        .isActive(true)
                        .description("1234578900987123456789012")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password2")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build()))
        );

        //When
        Exception e = assertThrows(IllegalArgumentException.class, () -> organisationFacade.getOrganisations(-11, 2));

        //Then
        String expected = "Page should be positive or zero";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void getOrganisationsWhenSizeIncorrect() {
        //Given
        when(organisationService.getAll(any())).thenReturn(new PageImpl<>(List.of(
                Organisation.builder()
                        .id(2L)
                        .name("Org123456")
                        .isActive(true)
                        .description("123457890098712345678900")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password1")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build(),
                Organisation.builder()
                        .id(34L)
                        .name("Org12334")
                        .isActive(true)
                        .description("1234578900987123456789012")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password2")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build()))
        );

        //When
        Exception e = assertThrows(IllegalArgumentException.class, () -> organisationFacade.getOrganisations(1, 0));

        //Then
        String expected = "Size must be positive";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void getOrganisationsWhenCorrect() {
        //Given
        when(organisationService.getAll(any())).thenReturn(new PageImpl<>(List.of(
                Organisation.builder()
                        .id(2L)
                        .name("Org123456")
                        .isActive(true)
                        .description("123457890098712345678900")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password1")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build(),
                Organisation.builder()
                        .id(34L)
                        .name("Org12334")
                        .isActive(true)
                        .description("1234578900987123456789012")
                        .workers(new ArrayList<>())
                        .utilities(new ArrayList<>())
                        .redactor(User.builder()
                                .id(1L)
                                .password("password2")
                                .role(UserRole.REDACTOR)
                                .login("redactor2")
                                .build())
                        .build()))
        );

        //When
        var actual = organisationFacade.getOrganisations(1, 3);

        //Then
        Assertions.assertEquals(2, actual.getTotalElements());
    }

    @Test
    public void getWorkersWhenNotExistingOrganisationId() {
        //Given
        Long incorrectOrdId = 1L;
        when(organisationService.existsById(incorrectOrdId)).thenReturn(false);

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.getWorkers(incorrectOrdId));

        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getWorkersWithPaginationWhenCorrect() {
        //Given
        Long correctId = 2L;

        when(organisationService.existsById(correctId)).thenReturn(true);

        when(workerService.findAllWorkersOfOrganisation(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(
                Worker.builder()
                        .id(1L)
                        .lastName("LastName")
                        .firstName("Fristanmknfd")
                        .description("123567890987654")
                        .utilities(new ArrayList<>())
                        .records(new ArrayList<>())
                        .organisation(Organisation.builder().id(2L).build())
                        .build(),
                Worker.builder()
                        .id(2L)
                        .lastName("LastName")
                        .firstName("Fristanmknfd")
                        .description("123567890987654")
                        .utilities(new ArrayList<>())
                        .records(new ArrayList<>())
                        .organisation(Organisation.builder().id(2L).build())
                        .build()
        )));

        when(organisationService.getOrganisation(2L)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()));

        //When
        var actual = organisationFacade.getWorkers(2L, 0, 10);

        //Then
        Assertions.assertEquals(2, actual.getTotalElements());
    }

    @Test
    public void getWorkerWhenCorrect() {
        //Given
        Long correctId = 2L;

        when(organisationService.existsById(correctId)).thenReturn(true);

        when(workerService.findAllWorkersOfOrganisation(correctId)).thenReturn(List.of(
                Worker.builder()
                        .id(1L)
                        .lastName("LastName")
                        .firstName("Fristanmknfd")
                        .description("123567890987654")
                        .utilities(new ArrayList<>())
                        .records(new ArrayList<>())
                        .organisation(Organisation.builder().id(2L).build())
                        .build(),
                Worker.builder()
                        .id(2L)
                        .lastName("LastName")
                        .firstName("Fristanmknfd")
                        .description("123567890987654")
                        .utilities(new ArrayList<>())
                        .records(new ArrayList<>())
                        .organisation(Organisation.builder().id(2L).build())
                        .build()
        ));

        when(organisationService.getOrganisation(2L)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()));

        //When
        var actual = organisationFacade.getWorkers(2L);

        //Then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void getUtilitiesWithPaginationWhenCorrect() {
        //Given
        Long correctId = 2L;

        when(organisationService.existsById(correctId)).thenReturn(true);

        when(utilityService.getAllUtilitiesOfOrganisation(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(
                Utility.builder()
                        .id(1L)
                        .price(213D)
                        .name("1234213123")
                        .workers(new ArrayList<>())
                        .description("123567890987654")
                        .organisation(Organisation.builder().id(2L).build())
                        .build(),
                Utility.builder()
                        .id(2L)
                        .price(213D)
                        .name("1234213123")
                        .workers(new ArrayList<>())
                        .description("123567890987654")
                        .organisation(Organisation.builder().id(2L).build())
                        .build()
        )));

        when(organisationService.getOrganisation(2L)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()));

        //When
        var actual = organisationFacade.getUtilities(2L, 0, 10);

        //Then
        Assertions.assertEquals(2, actual.getTotalElements());
    }

    @Test
    public void getUtilitiesWhenCorrect() {
        //Given
        Long correctId = 2L;

        when(organisationService.existsById(correctId)).thenReturn(true);

        when(utilityService.getAllUtilitiesOfOrganisation(anyLong())).thenReturn(List.of(
                Utility.builder()
                        .id(1L)
                        .price(213D)
                        .name("1234213123")
                        .workers(new ArrayList<>())
                        .description("123567890987654")
                        .organisation(Organisation.builder().id(2L).build())
                        .build(),
                Utility.builder()
                        .id(2L)
                        .price(213D)
                        .name("1234213123")
                        .workers(new ArrayList<>())
                        .description("123567890987654")
                        .organisation(Organisation.builder().id(2L).build())
                        .build()
        ));

        when(organisationService.getOrganisation(2L)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()));

        //When
        var actual = organisationFacade.getUtilities(2L);

        //Then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void getOrganisationIdByRedactorLogin() {
        //Given
        String correctLogin = "correctLogin";

        when(organisationService.getOrganisationByRedactorLogin(correctLogin)).thenReturn(Optional.ofNullable(Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build()
        ));

        //When
        Long actual = organisationFacade.getOrganisationIdByRedactorLogin(correctLogin);

        //Then
        Assertions.assertEquals(1L, actual);
    }

    @Test
    public void getOrganisationIdByRedactorLoginWhenLoginDoesNotExist() {
        //Given
        String correctLogin = "correctLogin";

        when(organisationService.getOrganisationByRedactorLogin(correctLogin)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.getOrganisationIdByRedactorLogin(correctLogin));

        //Then
        String expected = "NoOrganisationFoundException.msg.login";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void suspendOrganisationWhenCorrectOrgId() {
        //When
        Long correctId = 2L;

        Organisation organisation = Organisation.builder()
                .id(1L)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();


        when(organisationService.getOrganisation(correctId)).thenReturn(Optional.ofNullable(organisation));

        //When
        organisationFacade.suspendOrganisation(correctId);

        //Then
        Assertions.assertFalse(organisation.isActive());

        verify(organisationFacade, times(1)).suspendOrganisation(correctId);
    }

    @Test
    public void suspendOrganisationWhenIncorrectOrgId() {
        //When
        Long incorrectId = 2L;


        when(organisationService.getOrganisation(incorrectId)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.suspendOrganisation(incorrectId));

        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();

        verify(organisationFacade, times(1)).suspendOrganisation(incorrectId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void launchOrganisationWhenIncorrectOrgId() {
        //When
        Long incorrectId = 2L;

        when(organisationService.getOrganisation(incorrectId)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.suspendOrganisation(incorrectId));

        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();

        verify(organisationFacade, times(1)).suspendOrganisation(incorrectId);


        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void launchOrganisationWhenOrganisationAlreadyLaunched() {
        //When
        Long correctId = 2L;

        Organisation organisation = Organisation.builder()
                .id(correctId)
                .name("Org123")
                .isActive(true)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();

        when(organisationService.getOrganisation(correctId)).thenReturn(Optional.ofNullable(organisation));

        //When
        Exception e = assertThrows(OrganisationIsAlreadyLaunchedException.class, () -> organisationFacade.launchOrganisation(correctId));

        //Then
        String expected = "OrganisationIsAlreadyLaunchedException.msg.id";
        String actual = e.getMessage();

        verify(organisationFacade, times(1)).launchOrganisation(correctId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void launchOrganisationWhenCorrectOrgId() {
        //When
        Long correctId = 2L;

        Organisation organisation = Organisation.builder()
                .id(correctId)
                .name("Org123")
                .isActive(false)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();

        when(organisationService.getOrganisation(correctId)).thenReturn(Optional.ofNullable(organisation));

        //When
        organisationFacade.launchOrganisation(correctId);

        //Then
        verify(organisationFacade, times(1)).launchOrganisation(correctId);


        Assertions.assertTrue(organisation.isActive());
    }

    @Test
    public void deleteOrganisationWhenCorrect() {
        //Given
        Long correctId = 1L;

        when(organisationService.existsById(correctId)).thenReturn(true);

        Organisation organisation = Organisation.builder()
                .id(correctId)
                .name("Org123")
                .isActive(false)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();

        when(organisationService.getOrganisation(correctId)).thenReturn(Optional.ofNullable(organisation));

        //When
        organisationFacade.deleteOrganisation(correctId);

        //Then
        verify(organisationFacade, times(1)).deleteOrganisation(correctId);
        verify(organisationService, times(1)).deleteOrganisation(correctId);
    }

    @Test
    public void deleteOrganisationWhenIncorrectId() {
        //Given
        Long incorrectId = 1L;

        when(organisationService.existsById(incorrectId)).thenReturn(false);

        //When
        Exception e = assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.deleteOrganisation(incorrectId));


        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);

        verify(organisationFacade, times(1)).deleteOrganisation(incorrectId);
        verify(organisationService, times(0)).deleteOrganisation(incorrectId);
    }

    @Test
    public void updateOrganisationWhenCorrect() {
        //Given
        Long correctId = 1L;
        UpdateOrganisationRequest request = UpdateOrganisationRequest.builder()
                .name("12345567890-98765432")
                .description("123987654321234567890knfsg")
                .address("GeniousTurok")
                .build();

        Organisation organisation = Organisation.builder()
                .id(correctId)
                .name("Org123")
                .isActive(false)
                .description("12345789009871234567890")
                .workers(new ArrayList<>())
                .utilities(new ArrayList<>())
                .redactor(User.builder()
                        .id(1L)
                        .password("password")
                        .role(UserRole.REDACTOR)
                        .login("redactor")
                        .build())
                .build();

        when(organisationService.getOrganisation(correctId)).thenReturn(Optional.ofNullable(organisation));

        //When
        organisationFacade.updateOrganisation(correctId, request);

        //Then
        Assertions.assertEquals(request.getAddress(), organisation.getAddress());
        Assertions.assertEquals(request.getDescription(), organisation.getDescription());
        Assertions.assertEquals(request.getName(), organisation.getName());

        verify(organisationFacade, times(1)).updateOrganisation(correctId, request);

    }

    @Test
    public void updateOrganisationWhenIncorrectOrganisationId() {
        //Given
        Long incorrectId = 1L;
        UpdateOrganisationRequest request = UpdateOrganisationRequest.builder()
                .name("12345567890-98765432")
                .description("123987654321234567890knfsg")
                .address("GeniousTurok")
                .build();

        when(organisationService.getOrganisation(incorrectId)).thenReturn(Optional.empty());

        //When
        Exception e= assertThrows(NoOrganisationFoundException.class, () -> organisationFacade.updateOrganisation(incorrectId, request));

        //Then
        String expected = "NoOrganisationFoundException.msg.id";
        String actual = e.getMessage();


        Assertions.assertEquals(expected, actual);
        verify(organisationFacade, times(1)).updateOrganisation(incorrectId, request);
    }
}
