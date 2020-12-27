package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

    @Test
    public void testCommits() throws IOException {
//        устанавливаем соединение с github
        Github github = new RtGithub("840ed096c616bbf2fe4e074867c6e35dbaf8afc9");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("obaslyk", "java_pft")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
