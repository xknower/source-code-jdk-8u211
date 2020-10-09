package com.xknower.utils;

/**
 * 文件单元
 */
 class FileUnit {

    public FileUnit(boolean isDirectory, String absolutePath) {
        if (isDirectory) {
            directory = absolutePath;
        } else {
            if (absolutePath.contains("/")) {
                fileName = absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
                directory = absolutePath.replace(absolutePath, "/" + fileName);
            } else {
                fileName = absolutePath.substring(absolutePath.lastIndexOf("\\") + 1);
                directory = absolutePath.replace("\\" + fileName, "");
            }
        }
    }

    /**
     * 目录 (目录绝对路径)
     */
    private String directory;
    /**
     * 文件名
     */
    private String fileName;

    public String getFileName() {
        return fileName != null ? fileName : "";
    }

    /**
     * 文件后缀, 为目录时为空
     */
    public String getSuffix() {
        if (fileName != null && fileName.split(".").length == 2) {
            return fileName.split(".")[1];
        }
        return "";
    }

    /**
     * 文件绝对路径 / 目录绝对路径
     */
    public String getAbsolutePath() {
        if (fileName != null) {
            return combine(directory, fileName);
        }
        return directory;
    }

    /**
     * 是否为目录
     *
     * @return true 是目录
     */
    public boolean isDirectory() {
        if (directory != null && fileName == null) {
            return true;
        }
        return false;
    }

    private static String combine(String path, String filename) {
        if (path.endsWith("\\") || filename.startsWith("\\") || path.endsWith("/") || filename.startsWith("/")) {
            return path + filename;
        } else {
            return path + "//" + filename;
        }
    }
}
