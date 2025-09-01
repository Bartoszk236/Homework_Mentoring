package com.example.SimpleCrudInJpaStudentsCatalog.setup;

import com.example.SimpleCrudInJpaStudentsCatalog.entity.Student;
import com.example.SimpleCrudInJpaStudentsCatalog.model.Status;
import com.example.SimpleCrudInJpaStudentsCatalog.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SampleData implements ApplicationRunner {
    private final StudentRepository studentRepository;

    @Override
    public void run(ApplicationArguments args) {
        studentRepository.saveAll(List.of(
                Student.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .email("jan.kowalski@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Anna")
                        .lastName("Nowak")
                        .email("anna.nowak@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Piotr")
                        .lastName("Wiśniewski")
                        .email("piotr.wisniewski@example.com")
                        .status(Status.INACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Katarzyna")
                        .lastName("Lewandowska")
                        .email("katarzyna.lewandowska@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Michał")
                        .lastName("Wójcik")
                        .email("michal.wojcik@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Agnieszka")
                        .lastName("Kamińska")
                        .email("agnieszka.kaminska@example.com")
                        .status(Status.INACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Tomasz")
                        .lastName("Zieliński")
                        .email("tomasz.zielinski@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Magdalena")
                        .lastName("Szymańska")
                        .email("magdalena.szymanska@example.com")
                        .status(Status.ACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Paweł")
                        .lastName("Woźniak")
                        .email("pawel.wozniak@example.com")
                        .status(Status.INACTIVE)
                        .build(),
                Student.builder()
                        .firstName("Ewa")
                        .lastName("Dąbrowska")
                        .email("ewa.dabrowska@example.com")
                        .status(Status.ACTIVE)
                        .build()
        ));
    }
}
