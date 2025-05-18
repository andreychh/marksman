package marksman.server.domain.login.user;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class LoginUser {
    private final String name;

    public LoginUser(final String name) {
        this.name = name;
    }

    public Element serialize() {
        Element element = DocumentHelper.createElement("user");
        element.addElement("name").addText(this.name);
        return element;
    }
}
