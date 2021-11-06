package org.SpecikMan.Controller.SatisticsSection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditImageController {
    @FXML
    private Button btnEdit;

    @FXML
    private TextField txtPath;

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imUser;

    public void initialize() throws FileNotFoundException {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        if (account.getPathImage() == null)
        {
            txtPath.setText("path default");
        }
        txtPath.setText(account.getPathImage());
        Image image = new Image(new FileInputStream(account.getPathImage()));
        imUser.setImage(image);
    }

    @FXML
    void onClickEdit(MouseEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
        String path = fileChooser.showOpenDialog(btnEdit.getScene().getWindow()).getPath();
        Image image = new Image(new FileInputStream(path));
        imUser.setImage(image);
        txtPath.setText(path);
    }

    @FXML
    void onClickSave(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        if (txtPath.getText() == null)
        {
            JOptionPane.showMessageDialog(null,"ALERT MESSAGE", "Please choose photo", JOptionPane.WARNING_MESSAGE);
        }
        account.setPathImage(txtPath.getText());
        accountDao.update(account);
        LoadForm.load("/fxml/SatisticsFXMLs/HomeUser.fxml","Home User",false);
        DisposeForm.Dispose(txtPath);

    }

    private void configuringFileChooser(FileChooser fileChooser) {

        // Set tiêu đề cho FileChooser
        fileChooser.setTitle("Select Text File");


        // Sét thư mục bắt đầu nhìn thấy khi mở FileChooser
        fileChooser.setInitialDirectory(new File("C:/"));


        // Thêm các bộ lọc file vào
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG ", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
    }
}
