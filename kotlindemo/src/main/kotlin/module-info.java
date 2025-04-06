module com.coocpu.kotlindemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.coocpu.kotlindemo to javafx.fxml;
    exports com.coocpu.kotlindemo;
}