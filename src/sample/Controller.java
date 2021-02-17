package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Controller {
    @FXML
    private ListView<Note> lstNotes;
    @FXML
    private ListView<String> lstLogs;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClear;
    @FXML
    private Label lblAction;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblContent;
    @FXML
    private Label lblLogs;
    @FXML
    private Label lblCreateTime;
    @FXML
    private Label lblEditTime;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextArea txtContent;

    public void initialize() {


        btnAdd.setOnAction(event -> {
            Note selectedNote = getSelectedNote();
            if (getSelectedNote() == null) {
                createNewNote();

            } else {
                if (updateNote(selectedNote)) {
                    lblEditTime.setText("Data ostatniej edycji: " + convertToPrettyString(selectedNote.getEditTime()));
                }
            }
        });
        lstNotes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldSelectedNote, newSelectedNote) -> fillForm(newSelectedNote));
        // Opis akcji dla kliknięcia przycisku resetu formularza (Utwórz nową).
        // Usuniemy w takim wypadku zaznaczenie na liście, co pociągnie za sobą skutek resetu formularza.
        btnNew.setOnAction(event -> lstNotes.getSelectionModel().clearSelection());
        btnDelete.setOnAction(event -> {
            Note selectedNote = getSelectedNote();
            if (getSelectedNote() == null) {
                System.out.println("Nie wybrano notatki!!!!!!!!!");
            } else {
                deleteNote(selectedNote);
            }
        });

        btnClear.setOnAction(event -> {
            lstLogs.getItems().clear();
        });


    }

    private Note getSelectedNote() {
        return lstNotes.getSelectionModel().getSelectedItem();
    }

    private void createNewNote() {
        String title = txtTitle.getText();
        String content = txtContent.getText();
        if (title.equals("")) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Notatka musi posiadać tytuł!", ButtonType.OK);
            alert.setTitle("Okno potwierdzenia");
            alert.showAndWait();
        } else {
            Note note = new Note(title, content);
            lstNotes.getItems().add(note);
            lstNotes.getSelectionModel().selectLast();
            addNewLog("Stworzono " + actionMessage(note));
        }
    }

    private void resetForm() {
        lblAction.setText("TWORZENIE NOWEJ NOTATKI");
        btnAdd.setText("DODAJ");
        txtTitle.clear();
        txtContent.clear();
        lblCreateTime.setText("");
        lblEditTime.setText("");
    }

    private void fillForm(Note note) {
        if (note == null) {
            resetForm();
        } else {
            lblAction.setText("EDYCJA NOTATKI");
            btnAdd.setText("ZAPISZ ZMIANY");
            txtTitle.setText(note.getTitle());
            txtContent.setText(note.getContent());
            lblCreateTime.setText("Data utworzenia notatki: " + convertToPrettyString(note.getCreateTime()));
            if (note.getCreateTime() != note.getEditTime()) {
                lblEditTime.setText("Data ostatniej edycji: " + convertToPrettyString(note.getEditTime()));
            }
        }
    }

    private String convertToPrettyString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    private boolean updateNote(Note note) {
        String newTitle = txtTitle.getText();
        String newContent = txtContent.getText();
        if (newTitle.equals("")) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Notatka musi posiadać tytuł!", ButtonType.OK);
            alert.setTitle("Okno potwierdzenia");
            alert.showAndWait();
            return false;

        } else if (!anyChanges(note, newTitle, newContent)) {
            return false;

        } else {
            note.setTitle(newTitle);
            note.setContent(newContent);
            LocalDateTime editTime = LocalDateTime.now();
            note.setEditTime(editTime);
            addNewLog("Zmienino " + actionMessage(note));
            // "Poinformujemy" listę o aktualizacji jej elementu - wymusimy jej odświeżenie.
            lstNotes.refresh();
            return true;
        }
    }

    private String actionMessage(Note note) {
        LocalDateTime createTime = note.getCreateTime();
        LocalDateTime timeNow = LocalDateTime.now();
        return "notatkę [ Tytuł notatki: " + note.getTitle() + ", utworzonej: " + convertToPrettyString(createTime) + " ] - " + convertToPrettyString(timeNow);
    }

    private void deleteNote(Note note) {
        LocalDateTime deleteDateTime = LocalDateTime.now();
        Alert alert = new Alert(Alert.AlertType.NONE, "Czy na pewno chcesz usunąć: '" + note.getTitle() + "' ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Okno potwierdzenia");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            lstNotes.getItems().remove(note);
            lblAction.setText("TWORZENIE NOWEJ NOTATKI");
            btnAdd.setText("DODAJ");
            txtTitle.clear();
            txtContent.clear();
            lblCreateTime.setText("");
            lblEditTime.setText("");
            addNewLog("Usunięto " + actionMessage(note));
        }
    }

    private void addNewLog(String log) {
        lstLogs.getItems().add(log);
    }

    private boolean anyChanges(Note note, String newTitle, String newContent) {
        if (note.getTitle().equals(newTitle) && note.getContent().equals(newContent)) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Nie wprowadzono zmian.", ButtonType.OK);
            alert.setTitle("Okno informacyjne");
            alert.showAndWait();

            return false;
        } else return true;
    }

//    public void setup() {
//        setSaveAccelerator(btnAdd);
//    }


//    private void setSaveAccelerator(Button button) {
//        if (button == null) {
//            System.out.println("Button null!!");
//        }
//        assert button != null;
//        Scene scene = button.getScene();
//        if (scene == null) {
//            throw new IllegalArgumentException("setSaveAccelerator must be called when a button is attached to a scene");
//        }
//
//        scene.getAccelerators().put(
//                new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN),
//                new Runnable() {
//                    @FXML
//                    public void run() {
//
//                        button.fire();
//                    }
//                }
//        );
//    }

}