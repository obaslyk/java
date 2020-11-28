package ru.stqa.pft.sandbox;

public class Collections {

    public static void  main(String[] args) {
        String[] langs = {"Java", "C#", "Python", "PHP"};

//        for (int i = 0; i < langs.length; i++) {
//            System.out.println("Я хочу выучить " + langs[i]);
//        }
        for (String l : langs) {
            System.out.println("Я хочу выучить " + l);
        }
    }
}
