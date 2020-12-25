package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

    private final ApplicationManager app;
    private FTPClient ftp;

    // Инициализация, создаётся FTP client
    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    // Загрузка нового файла, старый временно переименовывается
    public void upload(File file, String target, String backup) throws IOException {
//        метод upload принимает три параметра:
//        file - локальный файл, который должен быть загружен на машину
//        target - имя файла, куда загружается
//        backup - имя резервной копии, если файл уже существует
        // Установка соединения с сервером
        ftp.connect(app.getProperty("ftp.host"));
        // Выполняется логин
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        // Удаление предыдущей резервной копии
        ftp.deleteFile(backup);
        // Переименовываем удалённый файл, делаем резервную копию
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        // Передача локального файла, из которого берутся данные и передаются на удалённую машину
        ftp.storeFile(target, new FileInputStream(file));
        // Разрыв соединения
        ftp.disconnect();
    }

    // Восстановление старого файла (исходной конфигурации тестируемой системы)
    public void restore(String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        // Удаление файла, который мы изначально загрузили
        ftp.deleteFile(target);
        // Восстановление оригинального файла из резервной копии
        ftp.rename(backup, target);
        ftp.disconnect();
    }
}
