package edu.pitt.apollo.flute.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 25, 2013
 * Time: 11:15:11 AM
 * Class: SSHConnection
 * IDE: NetBeans 6.9.1
 */
public class SSHConnection {

    private static final boolean LOG_OUTPUT = true;
    private JSch jsch = null;
    private Session session = null;

    public SSHConnection(String host, String user, String password) throws JSchException {

        jsch = new JSch();
        JSch.setConfig("StrictHostKeyChecking", "no");

        session = jsch.getSession(user, host, 22);
        session.setPassword(password);

        session.connect(30000);
    }

    public void executeCommand(String command) throws JSchException, InterruptedException {

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);


        if (LOG_OUTPUT) {
            channel.setInputStream(null);
            channel.setOutputStream(System.out);
            ((ChannelExec) channel).setErrStream(System.err);
        }

        channel.connect();

        if (LOG_OUTPUT) {
            try {
                InputStream in = channel.getInputStream();
                byte[] tmp = new byte[1024];
                while (true) {
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        System.out.print(new String(tmp, 0, i));
                    }

                    if (channel.isClosed()) {
                        System.out.println("exit-status: " + channel.getExitStatus());
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    }
                }
            } catch (IOException ex) {
                System.err.println("IOException getting output from channel input stream");
            }
        } else {
            while (!channel.isClosed()) {
                Thread.sleep(1000);
            }
        }

        channel.disconnect();
    }

    public void scpUploadFile(String localFilePath, String newFileName, String remoteDirectory) throws JSchException, 
            SftpException, FileNotFoundException {

        // get sftp channel
        ChannelSftp channel = (ChannelSftp)session.openChannel("sftp");
        channel.connect();
        // change directory
        channel.cd(remoteDirectory);
        // upload the file
        channel.put(new FileInputStream(localFilePath), newFileName);
        // disconnect
        channel.disconnect();
    }
    
    public void scpDownloadFile(String remoteDirectory, String remoteFileName, String localFilePath) throws JSchException, 
            SftpException, FileNotFoundException {
        
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        
        channel.cd(remoteDirectory);
        channel.get(remoteFileName, new FileOutputStream(localFilePath));
        channel.disconnect();
    }

    public void closeConnections() {
        
        if (session != null) {
            session.disconnect();
        }
    }
}
