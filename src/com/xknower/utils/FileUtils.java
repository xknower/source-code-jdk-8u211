package com.xknower.utils;

import java.io.File;
import java.util.*;

/**
 * 文件工具类
 *
 * @author xknower
 */
 class FileUtils {

    public static List<FileUnit> folderTraversal(String path) {
        return folderTraversal(new File(path));
    }

    /**
     * 遍历所给的目录, 返回根目录及子目录及所有文件列表
     *
     * @param file 目录
     * @return
     */
    public static List<FileUnit> folderTraversal(File file) {
        List<FileUnit> fileUnitList = new ArrayList<>();
        if (file != null && file.exists()) {
//            if (file.isDirectory()) {
//                fileUnitList.add(new FileUnit(true, file.getAbsolutePath()));
//            } else {
//                fileUnitList.add(new FileUnit(false, file.getAbsolutePath()));
//            }
            if (null == file.listFiles()) {
                // 非目录, 或者目录下没有文件
                return fileUnitList;
            }

            //
            LinkedList<File> list = new LinkedList<>();
            list.addAll(Arrays.asList(file.listFiles()));
            while (!list.isEmpty()) {
                File fileTemp = list.removeFirst();
                //
                if (fileTemp != null) {
                    if (fileTemp.isDirectory()) {
                        fileUnitList.add(new FileUnit(true, fileTemp.getAbsolutePath()));
                    } else {
                        fileUnitList.add(new FileUnit(false, fileTemp.getAbsolutePath()));
                    }

                    File[] files = fileTemp.listFiles();
                    if (files != null) {
                        for (File f : files) {
                            if (f.isDirectory()) {
                                list.add(f);
                            } else {
                                fileUnitList.add(new FileUnit(false, f.getAbsolutePath()));
                            }
                        }
                    }
                }
            }
        } else {
            // 目录不存在
            return fileUnitList;
        }
        return fileUnitList;
    }

    private static void folderTraversal2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());


                        folderTraversal2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}
