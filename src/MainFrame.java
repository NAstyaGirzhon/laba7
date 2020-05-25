

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
//import java.lang.String;


public class MainFrame extends JFrame {

    private static final String FRAME_TITLE = "Клиент мгновенных сообщений";

    private static final int FRAME_MINIMUM_WIDTH = 500;
    private static final int FRAME_MINIMUM_HEIGHT = 500;

    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;

    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;

    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;

    private static int SERVER_PORT = 0;

    private final JTextField textFieldFrom;
    private final JTextField textFieldTo;

    private final JTextArea textAreaIncoming;
    private final JTextArea textAreaOutgoing;

    public MainFrame() {
        super(FRAME_TITLE);
        setMinimumSize(
                new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));

        SERVER_PORT=(int)(Math.random()*2000)+ 3000;
        // Р¦РµРЅС‚СЂРёСЂРѕРІР°РЅРёРµ РѕРєРЅР°
        final Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - getWidth()) / 2,
                (kit.getScreenSize().height - getHeight()) / 2);

        // РўРµРєСЃС‚РѕРІР°СЏ РѕР±Р»Р°СЃС‚СЊ РґР»СЏ РѕС‚РѕР±СЂР°Р¶РµРЅРёСЏ РїРѕР»СѓС‡РµРЅРЅС‹С… СЃРѕРѕР±С‰РµРЅРёР№
        textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS, 0);
        textAreaIncoming.setEditable(false);

        // РљРѕРЅС‚РµР№РЅРµСЂ, РѕР±РµСЃРїРµС‡РёРІР°СЋС‰РёР№ РїСЂРѕРєСЂСѓС‚РєСѓ С‚РµРєСЃС‚РѕРІРѕР№ РѕР±Р»Р°СЃС‚Рё
        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);

        // РџРѕРґРїРёСЃРё РїРѕР»РµР№
        final JLabel labelFrom = new JLabel("Подпись");
        final JLabel labelTo = new JLabel("Получатель");
        final JLabel labelToPort = new JLabel("Порта");
        // РџРѕР»СЏ РІРІРѕРґР° РёРјРµРЅРё РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ Рё Р°РґСЂРµСЃР° РїРѕР»СѓС‡Р°С‚РµР»СЏ
        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
        textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);

        // РўРµРєСЃС‚РѕРІР°СЏ РѕР±Р»Р°СЃС‚СЊ РґР»СЏ РІРІРѕРґР° СЃРѕРѕР±С‰РµРЅРёСЏ
        textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);

        // РљРѕРЅС‚РµР№РЅРµСЂ, РѕР±РµСЃРїРµС‡РёРІР°СЋС‰РёР№ РїСЂРѕРєСЂСѓС‚РєСѓ С‚РµРєСЃС‚РѕРІРѕР№ РѕР±Р»Р°СЃС‚Рё
        final JScrollPane scrollPaneOutgoing =
                new JScrollPane(textAreaOutgoing);

        // РџР°РЅРµР»СЊ РІРІРѕРґР° СЃРѕРѕР±С‰РµРЅРёСЏ
        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(
                BorderFactory.createTitledBorder("Сообщение"));

        // РљРЅРѕРїРєР° РѕС‚РїСЂР°РІРєРё СЃРѕРѕР±С‰РµРЅРёСЏ
        final JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // РљРѕРјРїРѕРЅРѕРІРєР° СЌР»РµРјРµРЅС‚РѕРІ РїР°РЅРµР»Рё "РЎРѕРѕР±С‰РµРЅРёРµ"
        final GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);

        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP)
                                .addComponent(labelTo)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldTo))
                        .addComponent(scrollPaneOutgoing)
                        .addComponent(sendButton))
                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(labelTo)
                        .addComponent(textFieldTo))
                .addGap(MEDIUM_GAP)
                .addComponent(scrollPaneOutgoing)
                .addGap(MEDIUM_GAP)
                .addComponent(sendButton)
                .addContainerGap());

        // РљРѕРјРїРѕРЅРѕРІРєР° СЌР»РµРјРµРЅС‚РѕРІ С„СЂРµР№РјР°
        final GroupLayout layout1 = new GroupLayout(getContentPane());
        setLayout(layout1);

        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout1.createParallelGroup()
                        .addComponent(scrollPaneIncoming)
                        .addComponent(messagePanel))
                .addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneIncoming)
                .addGap(MEDIUM_GAP)
                .addComponent(messagePanel)
                .addContainerGap());

        // РЎРѕР·РґР°РЅРёРµ Рё Р·Р°РїСѓСЃРє РїРѕС‚РѕРєР°-РѕР±СЂР°Р±РѕС‚С‡РёРєР° Р·Р°РїСЂРѕСЃРѕРІ
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket();
                    while(true) {
                        try {

                            serverSocket =
                                    new ServerSocket(SERVER_PORT);
                            break;
                        } catch (Exception e) {
                            SERVER_PORT++;
                        }
                    }
                    textAreaIncoming.append("Сервер запущен!" + SERVER_PORT+ "\n");
                    while (!Thread.interrupted()) {
                        final Socket socket = serverSocket.accept();
                        final DataInputStream in = new DataInputStream(
                                socket.getInputStream());

                        // Р§РёС‚Р°РµРј РёРјСЏ РѕС‚РїСЂР°РІРёС‚РµР»СЏ
                        final String senderName = in.readUTF();

                        // Р§РёС‚Р°РµРј СЃРѕРѕР±С‰РµРЅРёРµ
                        final String message = in.readUTF();

                        // Р—Р°РєСЂС‹РІР°РµРј СЃРѕРµРґРёРЅРµРЅРёРµ
                        socket.close();

                        // Р’С‹РґРµР»СЏРµРј IP-Р°РґСЂРµСЃ
                        final String address =
                                ((InetSocketAddress) socket
                                        .getRemoteSocketAddress())
                                        .getAddress()
                                        .getHostAddress();

                        // Р’С‹РІРѕРґРёРј СЃРѕРѕР±С‰РµРЅРёРµ РІ С‚РµРєСЃС‚РѕРІСѓСЋ РѕР±Р»Р°СЃС‚СЊ
                        textAreaIncoming.append(senderName +
                                " (" + address + "): " +
                                message + "\n");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в работе сервера", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private void sendMessage() {
        try {
            // РџРѕР»СѓС‡Р°РµРј РЅРµРѕР±С…РѕРґРёРјС‹Рµ РїР°СЂР°РјРµС‚СЂС‹
            final String senderName = textFieldFrom.getText();
            final String destinationAddress = textFieldTo.getText();
            final String message = textAreaOutgoing.getText();
            String[] nud = destinationAddress.split(":");
            String address = nud[0];
            String f= nud[1];
            int potrt = Integer.parseInt(f);


            if(!destinationAddress.isEmpty()){

                int index =0;
                int count =0;
                 index = destinationAddress.indexOf(',');

                String[] nuder = address.split("\\.");
               if(nuder.length > 4 ){
                   JOptionPane.showMessageDialog(this,
                           "Неверно введен IP. Больше 4 чисел", "Ошибка",
                           JOptionPane.ERROR_MESSAGE);
                   return;
               }
                if( index !=-1 ){
                    JOptionPane.showMessageDialog(this,
                            "Неверно введен IP. Проверьте разделитель", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (String number : nuder){
                    int num = Integer.parseInt(number);
                    count++;
                    if( count ==1 && num > 127 ){
                        JOptionPane.showMessageDialog(this,
                                "Неверно введен IP. Первая число > 127 либо <127 ", "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(num > 255){
                        JOptionPane.showMessageDialog(this,
                                "Неверно введен IP", "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            // РЈР±РµР¶РґР°РµРјСЃСЏ, С‡С‚Рѕ РїРѕР»СЏ РЅРµ РїСѓСЃС‚С‹Рµ
            if (senderName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите имя отправителя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (destinationAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите адрес узла-получателя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                textFieldTo.grabFocus();
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите текст сообщения", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // РЎРѕР·РґР°РµРј СЃРѕРєРµС‚ РґР»СЏ СЃРѕРµРґРёРЅРµРЅРёСЏ
            final Socket socket =
                    new Socket(address, potrt);

            // РћС‚РєСЂС‹РІР°РµРј РїРѕС‚РѕРє РІС‹РІРѕРґР° РґР°РЅРЅС‹С…
            final DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            // Р—Р°РїРёСЃС‹РІР°РµРј РІ РїРѕС‚РѕРє РёРјСЏ
            out.writeUTF(senderName);

            // Р—Р°РїРёСЃС‹РІР°РµРј РІ РїРѕС‚РѕРє СЃРѕРѕР±С‰РµРЅРёРµ
            out.writeUTF(message);

            // Р—Р°РєСЂС‹РІР°РµРј СЃРѕРєРµС‚
            socket.close();

            // РџРѕРјРµС‰Р°РµРј СЃРѕРѕР±С‰РµРЅРёСЏ РІ С‚РµРєСЃС‚РѕРІСѓСЋ РѕР±Р»Р°СЃС‚СЊ РІС‹РІРѕРґР°
            textAreaIncoming.append("Я -> " + destinationAddress + ": "
                    + message + "\n");

            // РћС‡РёС‰Р°РµРј С‚РµРєСЃС‚РѕРІСѓСЋ РѕР±Р»Р°СЃС‚СЊ РІРІРѕРґР° СЃРѕРѕР±С‰РµРЅРёСЏ
            textAreaOutgoing.setText("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Не удалось отправить сообщение: узел-адресат не найден",

                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Не удалось отправить сообщение", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        int k=0;

        while(true) {


            SwingUtilities.invokeLater(() -> {

                final MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
            k++;
            if(k== 2){
                break;
            }
        }
    }
}

