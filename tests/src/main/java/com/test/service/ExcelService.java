package com.test.service;

import com.test.entity.Client;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.*;

public class ExcelService {
    public List<Client> parseExcel(File file) {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Client> clients = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell firstNameCell = row.getCell(0);
                Cell lastNameCell = row.getCell(1);
                Cell dateOfBirthCell = row.getCell(2);
                Cell emailCell = row.getCell(3);
                Cell phoneCell = row.getCell(4);

                String firstName = firstNameCell.getStringCellValue();
                String lastName = lastNameCell.getStringCellValue();
                Date dateOfBirth = dateOfBirthCell.getDateCellValue();
                String email = emailCell.getStringCellValue();
                String phone = phoneCell.getStringCellValue();

                clients.add(new Client(
                        firstName,
                        lastName,
                        (dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                        email,
                        phone,
                        null));
            }
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
