package com.leishianquan.util;

import javafx.scene.control.Alert;

public class Dialog {
    public static void alert_Dialog(String p_title, String p_header, String p_message)
    { Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle(p_title);
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.show();
    }
}
