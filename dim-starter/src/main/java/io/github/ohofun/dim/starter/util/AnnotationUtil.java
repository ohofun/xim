package io.github.ohofun.dim.starter.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/11 22:22
 */
@Component
public class AnnotationUtil {

    public List<Class<?>> getClassesWithAnnotationFromPackage(String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classList = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs = null;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        }
        catch (IOException e) {
            return null;
        }

        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();

            if ("file".equals(protocol) ) {
                String filePath = null;
                filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                filePath = filePath.substring(1);
                getClassesWithAnnotationFromFilePath(packageName, filePath, classList, annotation);
            } else if ("jar".equals(protocol)) {
                JarFile jar = null;
                try {
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                }
                catch (Exception e) {
                }

                List<Class<?>> alClassList = new ArrayList<>();
                findClassesByJar(packageName, jar, alClassList);
                getClassesWithAnnotationFromAllClasses(alClassList, annotation, classList);
            }
            else {
            }
        }

        return classList;
    }

    private static void findClassesByJar(String pkgName, JarFile jar, List<Class<?>> classes) {
        String pkgDir = pkgName.replace(".", "/");
        Enumeration<JarEntry> entry = jar.entries();

        while (entry.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文
            JarEntry jarEntry = entry.nextElement();
            String name = jarEntry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }

            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                continue;
            }
            //如果是一个.class文件 而且不是目录
            // 去掉后面的".class" 获取真正的类名
            String className = name.substring(0, name.length() - 6);
            Class<?> tempClass = loadClass(className.replace("/", "."));
            // 添加到集合中去
            if (tempClass != null) {
                classes.add(tempClass);
            }
        }
    }

    /**
     * 加载类
     * @param fullClsName 类全名
     * @return
     */
    private static Class<?> loadClass(String fullClsName ) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClsName );
        } catch (ClassNotFoundException e) {

        }
        return null;
    }


    private void getClassesWithAnnotationFromFilePath(String packageName, String filePath, List<Class<?>> classList,
                                                      Class<? extends Annotation> annotation) {
        Path dir = Paths.get(filePath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for(Path path : stream) {
                String fileName = String.valueOf(path.getFileName());

                boolean isDir = Files.isDirectory(path);
                if(isDir) {
                    getClassesWithAnnotationFromFilePath(packageName + "." + fileName , path.toString(), classList, annotation);
                }
                else  {
                    String className = fileName.substring(0, fileName.length() - 6);

                    Class<?> classes = null;
                    String fullClassPath = packageName + "." + className;
                    try {

                        classes = Thread.currentThread().getContextClassLoader().loadClass(fullClassPath);
                    }
                    catch (ClassNotFoundException e) {

                    }

                    if (null != classes && null != classes.getAnnotation(annotation)) {
                        classList.add(classes);
                    }
                }
            }
        }
        catch (IOException e) {

        }
    }

    private void getClassesWithAnnotationFromAllClasses(List<Class<?>> inClassList,
                                                        Class<? extends Annotation> annotation, List<Class<?>> outClassList) {
        for(Class<?> clazz : inClassList) {
            if (null != clazz && null != clazz.getAnnotation(annotation)) {
                outClassList.add(clazz);
            }
        }
    }


}
