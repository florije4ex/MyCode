package com.cui.code.test;

import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 批量操作：git fetch拉取项目、mvn clean清除项目
 *
 * @author cuiswing
 * @date 2019-04-29
 */
public class GitMvnBatchOperation {

    private static final String GIT_FETCH_COMMAND = "git fetch";
    // 在终端中使用的是zsh，配置的path中也是zsh，所以可以直接执行mvn,
    // 但是java调用其他进程时不是用的zsh，所以默认的mvn没法执行，指定完整的路径即可
    private static final String MVN_CLEAN_COMMAND = "/Users/cuiswing/tools/apache-maven-3.6.0/bin/mvn clean";

    /**
     * 批量操作，最多遍历的目录深度不超过depth
     *
     * @param basePath        初始目录
     * @param depth           遍历目录的深度
     * @param excludeProjects 不更新的项目
     * @param command         执行的命令
     * @param filter          过滤条件
     */
    public void gitFetch(String basePath, int depth, Set<String> excludeProjects, String command, FileFilter filter) {
        if (depth <= 0) {
            return;
        }

        File file = new File(basePath);
        File[] rootFiles = file.listFiles();
        if (rootFiles != null) {
            for (File fileDir : rootFiles) {
                if (fileDir.isFile() || (excludeProjects != null && excludeProjects.contains(fileDir.getName()))) {
                    continue;
                }
                File[] gitFiles = fileDir.listFiles(filter);
                if (gitFiles != null && gitFiles.length > 0) {
                    try {
                        Process process = Runtime.getRuntime().exec(command, null, fileDir);
                        InputStream inputStream;
                        if (command.equals(GIT_FETCH_COMMAND)) {
                            inputStream = process.getErrorStream();
                        } else {
                            inputStream = process.getInputStream();
                        }
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                        System.out.println(fileDir.getPath() + " ----finish on depth：" + depth);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    gitFetch(fileDir.getPath(), depth - 1, excludeProjects, command, filter);
                }
            }
        }
    }


    /**
     * git批量更新
     */
    @Test
    public void testGitFetch() {
        String basePath = "/Users/cuiswing/IdeaProjects";
        Set<String> excludeProjects = new HashSet<>();
        excludeProjects.add("spring-framework");
        excludeProjects.add("spring-boot");
        FileFilter filter = file -> file.isDirectory() && file.getName().equals(".git");
        gitFetch(basePath, 3, excludeProjects, GIT_FETCH_COMMAND, filter);
    }

    /**
     * mvn批量清除
     */
    @Test
    public void testMvnClean() {
        String basePath = "/Users/cuiswing/IdeaProjects";
        FileFilter filter = file -> file.isFile() && file.getName().equals("pom.xml");
        gitFetch(basePath, 4, null, MVN_CLEAN_COMMAND, filter);
    }
}
