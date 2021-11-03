package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private StudentRepository repository;

    public ReportService(StudentRepository repository) {
        this.repository = repository;
    }

    public String exportReport(String reportFormat) throws JRException, FileNotFoundException {
        String path = "/home/develop/Downloads/jasper_reports";
        List<Student> students = repository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:jasper/students.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getPath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("created with", "Jasper Reports");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/students.html");
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/students.pdf");
        }
        return "report generated in path: " + path;
    }
}
