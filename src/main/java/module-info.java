module hmd.teatroABC {
    requires javafx.controls;
    requires javafx.fxml;

    opens hmd.teatroABC to javafx.fxml;
    exports hmd.teatroABC.controller;
    opens hmd.teatroABC.controller to javafx.fxml;
    exports hmd.teatroABC.view;
    opens hmd.teatroABC.view to javafx.fxml;
    exports hmd.teatroABC.model.entities;
    opens hmd.teatroABC.model.entities to javafx.fxml;
    exports hmd.teatroABC.model.objects;
    opens hmd.teatroABC.model.objects to javafx.fxml;
}