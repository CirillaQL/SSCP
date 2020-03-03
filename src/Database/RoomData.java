package Database;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.DriverManager;

public class RoomData {
    private StringProperty room_id;
    private StringProperty room_type;
    private StringProperty room_size;
    private StringProperty room_state;

    public RoomData(String _roomid, String _roomtype, String _roomsize, String _roomstate){
        this.room_id = new SimpleStringProperty(_roomid);
        this.room_type = new SimpleStringProperty(_roomtype);
        this.room_size = new SimpleStringProperty(_roomsize);
        this.room_state = new SimpleStringProperty(_roomstate);
    }

    public String getRoom_id() {
        return room_id.get();
    }

    public StringProperty room_idProperty() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id.set(room_id);
    }

    public String getRoom_type() {
        return room_type.get();
    }

    public StringProperty room_typeProperty() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type.set(room_type);
    }

    public String getRoom_size() {
        return room_size.get();
    }

    public StringProperty room_sizeProperty() {
        return room_size;
    }

    public void setRoom_size(String room_size) {
        this.room_size.set(room_size);
    }

    public String getRoom_state() {
        return room_state.get();
    }

    public StringProperty room_stateProperty() {
        return room_state;
    }

    public void setRoom_state(String room_state) {
        this.room_state.set(room_state);
    }
}
