package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Directory
{
    private String dirPath;
    private String dirName;
    private File dir;
    private List<File> files = new ArrayList();
    private List<Directory> directories = new ArrayList();

    public Directory(String path)
    {
        this.dir = new File(path);
        this.dirPath = path;
        this.dirName = this.dir.getName();
        if (this.dir.listFiles() != null)
        {
            this.fillLists();
        }
    }

    private void fillLists()
    {
        for(int i = 0; i < ((File[]) Objects.requireNonNull(this.dir.listFiles())).length; ++i) {
            File tempFile = new File(((File[])Objects.requireNonNull(this.dir.listFiles()))[i].getPath());
            if (tempFile.isDirectory()) {
                this.directories.add(new Directory(tempFile.getPath()));
            } else {
                this.files.add(tempFile);
            }
        }
    }

    public File getFile(int index) {
        return (File)this.files.get(index);
    }

    public String getFileContent(int index) throws IOException
    {
        return Files.readAllLines(Paths.get(((File)this.files.get(index)).getPath())).toString();
    }

    public int getDirSizeLocal() {
        return this.files.size() + this.directories.size();
    }

    public String getDirName() {
        return this.dirName;
    }
}

