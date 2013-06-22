package org.apache.hadoop.contrib.ftp;

import org.apache.ftpserver.ftplet.FileObject;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.hadoop.fs.Path;

/**
 * Implemented FileSystemView to use HdfsFileObject
 */
public class HdfsFileSystemView implements FileSystemView {

  // the root directory will always end with '/'.
  private String rootDir = Path.SEPARATOR;

  // the first and the last character will always be '/'
  // It is always with respect to the root directory.
  private String currDir = Path.SEPARATOR;

  private User user;

  // private boolean writePermission;

  private boolean caseInsensitive = false;

  /**
   * Constructor - set the user object.
   */
  protected HdfsFileSystemView(User user) throws FtpException {
    this(user, true);
  }

  /**
   * Constructor - set the user object.
   */
  protected HdfsFileSystemView(User user, boolean caseInsensitive)
          throws FtpException {
    if (user == null) {
      throw new IllegalArgumentException("user can not be null");
    }
    if (user.getHomeDirectory() == null) {
      throw new IllegalArgumentException(
              "User home directory can not be null");
    }

    this.caseInsensitive = caseInsensitive;

    // add last '/' if necessary
    String rootDir = user.getHomeDirectory();
    //  rootDir = NativeFileObject.normalizeSeparateChar(rootDir);
    if (!rootDir.endsWith(Path.SEPARATOR)) {
      rootDir += Path.SEPARATOR;
    }
    this.rootDir = rootDir;

    this.user = user;


  }

  /**
   * Get the user home directory. It would be the file system root for the
   * user.
   */
  public FileObject getHomeDirectory() {
    return new HdfsFileObject(this.rootDir, user);
  }

  /**
   * Get the current directory.
   */
  public FileObject getCurrentDirectory() {
    return new HdfsFileObject(this.currDir, user);
  }

  /**
   * Get file object.
   */
  public FileObject getFileObject(String file) {
    String path;
    if (file.startsWith(Path.SEPARATOR)) {
      path = file;
    } else if (currDir.length() > 1) {
      path = currDir + Path.SEPARATOR + file;
    } else {
      path = Path.SEPARATOR + file;
    }
    return new HdfsFileObject(path, user);
  }

  /**
   * Change directory.
   */
  public boolean changeDirectory(String dir) {
    String path;
    if (dir.startsWith(Path.SEPARATOR)) {
      path = dir;
    } else if (currDir.length() > 1) {
      path = currDir + Path.SEPARATOR + dir;
    } else {
      path = Path.SEPARATOR + dir;
    }
    HdfsFileObject file = new HdfsFileObject(path, user);
    if (file.isDirectory() && file.hasReadPermission()) {
      currDir = path;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Is the file content random accessible?
   */
  public boolean isRandomAccessible() {
    return true;
  }

  /**
   * Dispose file system view - does nothing.
   */
  public void dispose() {
  }
}
