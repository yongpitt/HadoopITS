General

the FTP server which works on a top of HDFS. It allows to connect to HDFS using any FTP client.
FTP server is configurable by hdfs-over-ftp.conf and users.conf. Also it allows to use secure connection over SSL and supports all HDFS permissions.

Installation

1. Download hdfs-over-ftp.tar.gz, unpack it.
2. Set users in users.conf. All passwords are md5 encrypted.
3. Set connection port, data-ports and hdfs-uri etc in hdfs-over-ftp.conf.
4. Start and stop server using hdfs-over-ftp.sh (start/stop)

Under linux you can mount ftp using curlftpfs:
sudo curlftpfs  -o allow_other ftp://user:pass@localhost:21 ftpfs

Frequently used commands
create new user:
     ./register-user.sh username password >> users.conf
start ftp server:
     ./hdfs-over-ftp.sh start
stop ftp server:
     ./hdfs-over-ftp.sh stop

History
version hdfs-over-ftp-0.20.0
1.hadoop version 0.20.0 is be supported
2.fixed bugs
  a.simulatneous is limited in the ftp server,the following:
       The maximum number of simultaneous users  :    10
       The maximum number of simultaneous anonymous users  : 10
  b.homedirectory is invalid in users.conf

