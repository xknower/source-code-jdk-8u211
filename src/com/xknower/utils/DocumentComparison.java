package com.xknower.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件对比器
 * > 1、实现, 目录及文件对比 (相同目录路径及文件名), 输出<相同文件、各自不同文件>
 *
 * @author xknower
 */
class DocumentComparison {

    public static void main(String[] args) {

        comparison("src", "F:\\ComCR\\code\\x\\src", "F:\\ComCR\\code\\x\\rt\\src");
        System.out.println();
    }

    public static void comparison(String pack, String pathDirectoryOne, String pathDirectoryTow) {
        File pathDirectoryOneFile = new File(pathDirectoryOne);
        if (!pathDirectoryOneFile.exists() || !pathDirectoryOneFile.isDirectory()) {
        }

        File pathDirectoryTowFile = new File(pathDirectoryTow);
        if (!pathDirectoryTowFile.exists() || !pathDirectoryTowFile.isDirectory()) {
        }

        //
        List<FileUnit> one = FileUtils.folderTraversal(pathDirectoryOneFile);
        List<FileUnit> two = FileUtils.folderTraversal(pathDirectoryTow);
        // 相同的, one 独有的, two 独有的
        List<FileUnit> identical = new ArrayList<>();
        List<FileUnit> oneUnique = new ArrayList<>();
        List<FileUnit> twoUnique = new ArrayList<>();

        List<FileUnit> del = new ArrayList<>();
        for (FileUnit o : one) {
            if (o.getFileName().contains("$")) {
                del.add(o);
            }
        }
        one.removeAll(del);
        del.clear();
        for (FileUnit o : two) {
            if (o.getFileName().contains("$")) {
                del.add(o);
            }
        }
        two.removeAll(del);

        // 在相同目录下, 文件匹配数
        for (FileUnit o : one) {
            //
            for (FileUnit t : two) {
                String a = o.getAbsolutePath().split(pack)[1].replace(".class", "").replace(".java", "");
                String b = t.getAbsolutePath().split(pack)[1].replace(".class", "").replace(".java", "");
                if (a.equalsIgnoreCase(b)) {
                    identical.add(o);
                    identical.add(t);
                }
            }
        }
        //
        two.removeAll(identical);
        one.removeAll(identical);
        List<FileUnit> oneFileUnits = one.stream().filter(f -> f.isDirectory()).collect(Collectors.toList()); // DEL
        List<FileUnit> twoFileUnits = two.stream().filter(f -> f.isDirectory()).collect(Collectors.toList()); // 新增
        List<FileUnit> identicalFileUnits = identical.stream().filter(f -> f.isDirectory()).collect(Collectors.toList());
        // 新增
        List<FileUnit> identicalA = new ArrayList<>();
        List<FileUnit> oneUniqueA = new ArrayList<>();
        List<FileUnit> twoUniqueA = new ArrayList<>();
        for (FileUnit o : twoFileUnits) {
            //
            for (FileUnit t : identicalFileUnits) {
                String a = o.getAbsolutePath().split(pack)[1].replace(".class", "").replace(".java", "");
                String b = t.getAbsolutePath().split(pack)[1].replace(".class", "").replace(".java", "");
                if (a.equalsIgnoreCase(b)) {
                    // 同包新增
                    identicalA.add(o);
                }
            }
        }
        // 非同包新增
        twoFileUnits.removeAll(identicalA);
        // 纯新增包
        twoFileUnits.forEach(a -> System.out.println(a.getAbsolutePath()));
        // 同包新增
        identicalA.forEach(a -> System.out.println(a.getAbsolutePath()));
        //
        System.out.println();
    }
}
