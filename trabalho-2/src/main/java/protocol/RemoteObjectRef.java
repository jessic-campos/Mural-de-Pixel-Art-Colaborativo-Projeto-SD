package protocol;

import java.io.Serializable;

public class RemoteObjectRef implements Serializable {
    private static final long serialVersionUID = 1L;

    private String objectReference;

    public RemoteObjectRef(String objectReference) {
        this.objectReference = objectReference;
    }

    public String getObjectReference() { return objectReference; }
}
