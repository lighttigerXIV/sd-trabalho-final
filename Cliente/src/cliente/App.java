package cliente;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import rmi.ServerInterface;
import java.rmi.registry.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import rmi.Result;
import rmi.User;

/// Referencias
/// Dialogo -> https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
/// Seletor de ficheiros -> https://www.rgagnon.com/javadetails/java-0370.html
/// Listagem de ficheiros -> https://www.geeksforgeeks.org/list-all-files-from-a-directory-recursively-in-java/
/// Dialog de confirmação -> https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
/// Listener do textfield -> https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield

public class App extends javax.swing.JFrame {

    private String ip;
    private String username;
    private File sharedFolder;
    private ServerInterface serverInterface;
    private LogsThread logsThread;
    private UsersThread usersThread;
    private SendFilesThread sendFilesThread;
    private boolean loggedIn = false;
    private String selectedUsername = "";
    private FileTransfer fileTransfer;

    public App() {
        initComponents();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });

        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            usernameField.setText(hostname);

            clientsList.addListSelectionListener((ListSelectionEvent e) -> {
                try {
                    if (!e.getValueIsAdjusting()) {
                        JList<String> list = (JList<String>) e.getSource();
                        selectedUsername = list.getSelectedValue();

                        for (User user : serverInterface.getUsers()) {
                            if (user.getUserName().equals(selectedUsername)) {

                                DefaultListModel<String> listModel = new DefaultListModel<>();

                                user.getFiles().forEach(path -> {
                                    File file = new File(path);
                                    listModel.addElement(file.getName());
                                });

                                filesList.setModel(listModel);

                                break;
                            }
                        }
                    }
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

            });

            filesList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        if (e.getClickCount() == 2) {
                            downloadFile();
                        }
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }
        
        usernameField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmptyFields();
            }
        });
        
        portField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmptyFields();
            }
        });
        
        ipField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmptyFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkEmptyFields();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        clientsList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        filesList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        labelConfiguracoes = new javax.swing.JLabel();
        labelUsername = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        labelServidor = new javax.swing.JLabel();
        ipField = new javax.swing.JTextField();
        labelServidor1 = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        labelPartilha = new javax.swing.JLabel();
        labelPasta = new javax.swing.JLabel();
        selectFolderButton = new javax.swing.JButton();
        sessionButton = new javax.swing.JButton();
        labelConfiguracoes1 = new javax.swing.JLabel();
        labelPartilha1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        logsList = new javax.swing.JList<>();
        labelUsername1 = new javax.swing.JLabel();
        labelUsername2 = new javax.swing.JLabel();
        refreshFilesButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente");
        setBackground(new java.awt.Color(246, 246, 246));
        setResizable(false);

        clientsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(clientsList);

        filesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(filesList);

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));

        labelConfiguracoes.setFont(new java.awt.Font("Inter Display", 1, 18)); // NOI18N
        labelConfiguracoes.setText("Configurações");

        labelUsername.setFont(new java.awt.Font("Inter Display", 1, 15)); // NOI18N
        labelUsername.setText("Nome de Utilizador");

        labelServidor.setFont(new java.awt.Font("Inter Display", 1, 15)); // NOI18N
        labelServidor.setText("Endereço Servidor");

        labelServidor1.setFont(new java.awt.Font("Inter Display", 1, 15)); // NOI18N
        labelServidor1.setText("Porta");

        portField.setText("1099");

        labelPartilha.setFont(new java.awt.Font("Inter Display", 1, 18)); // NOI18N
        labelPartilha.setText("Logs");

        labelPasta.setText("Nenhuma pasta selecionada");

        selectFolderButton.setText("Selecionar Pasta");
        selectFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFolderButtonActionPerformed(evt);
            }
        });

        sessionButton.setText("Iniciar Sessão");
        sessionButton.setEnabled(false);
        sessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionButtonActionPerformed(evt);
            }
        });

        labelConfiguracoes1.setFont(new java.awt.Font("Inter Display", 1, 18)); // NOI18N
        labelConfiguracoes1.setText("Servidor");

        labelPartilha1.setFont(new java.awt.Font("Inter Display", 1, 18)); // NOI18N
        labelPartilha1.setText("Partilha");

        logsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(logsList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameField)
                    .addComponent(sessionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ipField)
                    .addComponent(selectFolderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelConfiguracoes)
                            .addComponent(labelUsername)
                            .addComponent(labelServidor)
                            .addComponent(labelServidor1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPartilha)
                            .addComponent(labelPasta)
                            .addComponent(labelConfiguracoes1)
                            .addComponent(labelPartilha1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConfiguracoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sessionButton)
                .addGap(31, 31, 31)
                .addComponent(labelConfiguracoes1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelServidor1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(labelPartilha1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPasta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectFolderButton)
                .addGap(48, 48, 48)
                .addComponent(labelPartilha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelUsername1.setFont(new java.awt.Font("Inter Display", 1, 15)); // NOI18N
        labelUsername1.setText("Ficheiros");

        labelUsername2.setFont(new java.awt.Font("Inter Display", 1, 15)); // NOI18N
        labelUsername2.setText("Utilizadores");

        refreshFilesButton.setText("Atualizar");
        refreshFilesButton.setEnabled(false);
        refreshFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFilesButtonActionPerformed(evt);
            }
        });

        downloadButton.setText("Transferir ");
        downloadButton.setEnabled(false);
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUsername2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(refreshFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelUsername1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsername1)
                    .addComponent(labelUsername2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(refreshFilesButton)
                            .addComponent(downloadButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFolderButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione uma pasta para partilhar");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            sharedFolder = chooser.getSelectedFile();
            labelPasta.setText(sharedFolder.toString());

            sessionButton.setEnabled(true);
        }
    }//GEN-LAST:event_selectFolderButtonActionPerformed

    private void sessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionButtonActionPerformed
        try {

            if (loggedIn == false) {
                login();
            } else {
                logout();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_sessionButtonActionPerformed

    private void refreshFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFilesButtonActionPerformed

        try {

            sendFilesThread.refreshFiles();

            for (User user : serverInterface.getUsers()) {
                if (user.getUserName().equals(selectedUsername)) {

                    DefaultListModel<String> filesModel = new DefaultListModel<>();

                    user.getFiles().forEach(path -> {
                        File file = new File(path);
                        filesModel.addElement(file.getName());
                    });

                    filesList.setModel(filesModel);

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_refreshFilesButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        downloadFile();
    }//GEN-LAST:event_downloadButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    private void toggleElements() {
        selectFolderButton.setEnabled(!loggedIn);
        ipField.setEnabled(!loggedIn);
        portField.setEnabled(!loggedIn);
        usernameField.setEnabled(!loggedIn);
        refreshFilesButton.setEnabled(loggedIn);
        downloadButton.setEnabled(loggedIn);

        sessionButton.setText(loggedIn ? "Terminar Sessão" : "Iniciar Sessão");
    }

    private void login() {
        try {
            ip = ipField.getText();
            username = usernameField.getText();

            boolean canConnect = InetAddress.getByName(ip).isReachable(400);

            if (!canConnect) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao tentar fazer ligacão com o servidor",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            Registry registry = LocateRegistry.getRegistry(ip, 1099);
            serverInterface = (ServerInterface) registry.lookup("projeto-sd");
            fileTransfer = new FileTransfer(serverInterface, sharedFolder, this);

            List<String> files = new ArrayList<>();

            Files.newDirectoryStream(sharedFolder.toPath()).forEach(path -> {
                if (new File(path.toString()).isFile()) {
                    files.add(path.toString());
                }
            });

            Result loginResult = serverInterface.login(username, sharedFolder.getAbsolutePath(), files, fileTransfer);

            if (!loginResult.getSuccess()) {
                JOptionPane.showMessageDialog(this,
                        loginResult.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            loggedIn = true;
            toggleElements();

            logsThread = new LogsThread(serverInterface, username, logsList);
            Thread lThread = new Thread(logsThread);
            lThread.start();

            usersThread = new UsersThread(serverInterface, clientsList, filesList);
            Thread uThread = new Thread(usersThread);
            uThread.start();

            sendFilesThread = new SendFilesThread(filesList, serverInterface, username, sharedFolder);
            Thread sfThread = new Thread(sendFilesThread);
            sfThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            if (loggedIn) {
                serverInterface.logout(username);
                loggedIn = false;
                username = null;

                logsThread.kill();
                usersThread.kill();
                sendFilesThread.kill();

                toggleElements();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile() {
        try {
            String fileName = filesList.getSelectedValue();

            if (fileName != null) {
                String finalPath = sharedFolder + "/" + fileName;

                int option = JOptionPane.showConfirmDialog(this, "Quer transferir o ficheiro '" + fileName + "' de " + selectedUsername + "?", null, JOptionPane.YES_NO_OPTION);

                if (option == 1) {
                    return;
                }

                String selectedUsername = clientsList.getSelectedValue();

                if (new File(finalPath).exists()) {
                    option = JOptionPane.showConfirmDialog(this, "Quer substituir o ficheiro na sua pasta partilhada?", null, JOptionPane.YES_NO_OPTION);

                    if (option == 1) {
                        return;
                    }
                }

                serverInterface.requestFile(fileName, username, selectedUsername);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void checkEmptyFields(){
        String usernameText = usernameField.getText();
        String portText = portField.getText();
        String ipText = ipField.getText();
        
        sessionButton.setEnabled(
                !usernameText.trim().isEmpty()
                && !portText.trim().isEmpty()
                && !ipText.trim().isEmpty()
                && sharedFolder != null
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> clientsList;
    private javax.swing.JButton downloadButton;
    private javax.swing.JList<String> filesList;
    private javax.swing.JTextField ipField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelConfiguracoes;
    private javax.swing.JLabel labelConfiguracoes1;
    private javax.swing.JLabel labelPartilha;
    private javax.swing.JLabel labelPartilha1;
    private javax.swing.JLabel labelPasta;
    private javax.swing.JLabel labelServidor;
    private javax.swing.JLabel labelServidor1;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelUsername1;
    private javax.swing.JLabel labelUsername2;
    private javax.swing.JList<String> logsList;
    private javax.swing.JTextField portField;
    private javax.swing.JButton refreshFilesButton;
    private javax.swing.JButton selectFolderButton;
    private javax.swing.JButton sessionButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

}
