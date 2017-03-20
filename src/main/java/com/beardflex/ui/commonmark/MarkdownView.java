package com.beardflex.ui.commonmark;

import com.beardflex.bean.HtmlContent;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * Created by David on 20/03/2017.
 */
public class MarkdownView extends AnchorPane implements Initializable {

    public enum Mode {
        READ_ONLY,
        READ_WRITE,
        WRITE_ONLY;
    }

    private final String Fxml = "fxml/MarkdownView.fxml";
    private final Logger log = LogManager.getLogger();
    private final boolean debugWebView = false;

    @FXML SplitPane splitPane;
    @FXML RadioButton showEditorButton;
    @FXML RadioButton showMarkdownButton;
    @FXML RadioButton showBothButton;
    @FXML TextArea markdownEditor;
    @FXML WebView markdownViewer;

    @FXML ListView<Image> imageList;
    @FXML Label imageFilePathLabel;
    @FXML Button browseButton;
    @FXML Button uploadButton;

    @FXML VBox sidebar;
    @FXML AnchorPane webViewAnchor;

    private BorderPane contentPane;
    private Parser parser;
    private HtmlRenderer renderer;
    private HtmlContent bean;

    public MarkdownView(ResourceBundle bundle, HtmlContent bean) {
        this.bean = bean;
        // Try to get the relative path to the FXML.
        URL fxmlUrl = getClass().getResource(Fxml);
        if(fxmlUrl != null) {
            // Create the FXML loader and set this instance as its controller.
            FXMLLoader loader = new FXMLLoader(fxmlUrl, bundle);
            loader.setController(this);
            try {
                // Load the FXML into a VBox instance.
                contentPane = loader.load();
                // Add the VBox to _this_.
                AnchorPane.setTopAnchor(contentPane, 0.0);
                AnchorPane.setLeftAnchor(contentPane, 0.0);
                AnchorPane.setBottomAnchor(contentPane, 0.0);
                AnchorPane.setRightAnchor(contentPane, 0.0);
                this.getChildren().add(contentPane);

            } catch(IOException e) {
                log.error(e);
            }
        } else {
            // Handle Error.
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialise the parser and renderer.
        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();

        sidebar.setMaxHeight(Double.MAX_VALUE);

        // Add a listener to the web engine to load in the CSS for commonmark.
        WebEngine engine = markdownViewer.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if(newState == Worker.State.SUCCEEDED) {
                Document doc = engine.getDocument();
                Element styleNode = doc.createElement("style");
                StringBuilder cssContent = new StringBuilder();
                URL url = getClass().getResource("css/markdownView.css");
                if(url != null) {
                    try (Stream<String> stream = Files.lines(Paths.get(url.toURI()))) {
                        stream.forEach(item -> {
                            cssContent.append(item);
                        });
                    } catch(Exception e) {
                        log.error(e);
                    }
                }

                Text styleContent = doc.createTextNode(cssContent.toString());
                styleNode.appendChild(styleContent);
                doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);

                if(debugWebView)
                engine.executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");

            }
        });

        // Add a listener to reload the markdown on pressing 'R'.
        Platform.runLater(() -> {
            splitPane.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN),
                    () ->{
                        String markdownRaw = markdownEditor.getText();
                        Node node = parser.parse(markdownRaw);
                        String htmlContent = renderer.render(node);
                        markdownViewer.getEngine().loadContent(htmlContent);
                    });
        });

        showEditorButton.setOnAction(e -> {
            setMode(Mode.WRITE_ONLY);
        });
        showMarkdownButton.setOnAction(e -> {
            setMode(Mode.READ_ONLY);
        });
        showBothButton.setOnAction(e -> {
            setMode(Mode.READ_WRITE);
        });
    }

    private void setMode(Mode mode) {
        switch(mode) {
            case READ_ONLY :
                unbindAll();
                splitPane.getItems().add(0, webViewAnchor);

                splitPane.setDividerPositions(0);
                break;
            case READ_WRITE:
                unbindAll();

                splitPane.getItems().add(0, markdownEditor);
                splitPane.getItems().add(1, webViewAnchor);

                splitPane.setDividerPositions(0.5);


                break;
            case WRITE_ONLY:
                unbindAll();

                splitPane.getItems().add(0, markdownEditor);

                splitPane.setDividerPositions(1);
                break;
        }
    }

    private void unbindAll(){
        splitPane.getItems().removeAll();
    }
}
