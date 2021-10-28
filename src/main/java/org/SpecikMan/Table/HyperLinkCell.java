package org.SpecikMan.Table;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import javax.swing.*;

public class HyperLinkCell implements Callback<TableColumn<TableViewItem, Hyperlink>, TableCell<TableViewItem,Hyperlink>> {
    @Override
    public TableCell<TableViewItem,Hyperlink> call(TableColumn<TableViewItem,Hyperlink> arg){
        return new TableCell<>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : item);
                if(!empty){
                    item.setOnAction(e->{
                        JOptionPane.showMessageDialog(null,item.getText());
                    });
                }
            }
        };
    }
}
