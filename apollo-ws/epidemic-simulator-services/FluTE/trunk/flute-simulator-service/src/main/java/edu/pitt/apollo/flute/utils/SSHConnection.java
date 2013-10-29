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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(SSHConnection.class.getName());
    private JSch jsch = null;
    private Session session = null;

    public SSHConnection(String host, String user, String password) throws JSchException {

        jsch = new JSch();
        JSch.setConfig("StrictHostKeyChecking", "no");

        session = jsch.getSession(user, host, 22);
        session.setPassword(password);

        session.connect(30000);
    }

    public String executeCommand(String command) throws JSchException, InterruptedException {

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);


        StringBuilder stBuild = new StringBuilder();
        channel.setInputStream(null);

        channel.connect();

        try {
            InputStream in = channel.getInputStream();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    String outputLine = new String(tmp, 0, i);
                    stBuild.append(outputLine);
                }

                if (channel.isClosed()) {
                    if (channel.getExitStatus() != 0) { // only report the exit status if it is nonzero
                        LOGGER.log(Level.INFO, "exit-status: " + channel.getExitStatus());
                    }
                    break;
                }

                Thread.sleep(1000);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "IOException getting output from channel input stream");
        }

        channel.disconnect();

        return stBuild.toString();
    }

    public void scpUploadFile(String localFilePath, String newFileName, String remoteDirectory) throws JSchException,
            SftpException, FileNotFoundException {

        // get sftp channel
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        // change directory
        channel.cd(remoteDirectory);
        // upload the file
        channel.put(new FileInputStream(localFilePath), newFileName);
        // disconnect
        channel.disconnect();
    }

    public void scpUploadFileFromURL(String url, String newFileName, String remoteDirectory)
            throws SftpException, JSchException, MalformedURLException, IOException {
        // get sftp channel
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        // change directory
        channel.cd(remoteDirectory);
        // upload the file
        channel.put(new URL(url).openStream(), newFileName);
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

    public Session getSession() {
        return session;
    }

    public void closeConnections() {

        if (session != null) {
            session.disconnect();
        }
    }
}
