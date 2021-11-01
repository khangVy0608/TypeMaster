package org.SpecikMan.Table;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

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
                        AccountDao accountDao = new AccountDao();
                        Account account = new Account();
                        for(Account i: accountDao.getAll()){
                            if(i.getUsername().trim().equals(item.getText().trim())){
                                account = i;
                            }
                        }
                        FileRW.Write(FilePath.getChooseProfile(),account.getIdAccount());
                        LoadForm.load("/fxml/PracticeFXMLs/Profile.fxml","Profile",false);
                    });
                }
            }
        };
    }
}
