package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private final ApplicationManager app;

    // Конструктор для SoapHelper
    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        // Установка соединения с Mantis SOAP
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-2.24.2/mantisbt-2.24.2/api/soap/mantisconnect.php"));
        return mc;
    }

//    метод для извлечения проекта
    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        // Открытие соединения
        MantisConnectPortType mc = getMantisConnect();
        // Получить список проектов, к которым пользователь имеет доступ
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
        // Преобразование полученных данных в модельные объекты
        return Arrays.asList(projects).stream().map((p) -> new Project()
                .withId(p.getId().intValue()).withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        // Открываем соединение
        MantisConnectPortType mc = getMantisConnect();
        // запрашиваем категорию
        String[] categories = mc.mc_project_get_categories("administrator", "root",
                BigInteger.valueOf(issue.getProject().getId()));
        // Создаем из своего модельного объекта объект для передачи его в метод удаленного интерфейса
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        // setProject с параметром ObjectRef project, т.е. ссылка на проект
        // ObjectRef имеет два параметра, один из них с типом BigInteger
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        // Нужно заполнить категорию 9это обязательное поле). Выбираем первую попавшуюся из списка categories
        issueData.setCategory(categories[0]);
        // Создание баг-репорта
        BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
        // получение объекта
        IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
        // преобразуем обратно объект Mantis SOAP в новый модельный объект и заполняем его
        return new Issue().withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getId().intValue())
                                          .withName(createdIssueData.getProject().getName()));
    }
}

