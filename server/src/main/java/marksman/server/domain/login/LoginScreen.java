package marksman.server.domain.login;

import marksman.server.domain.Screen;
import marksman.server.domain.login.user.LoginUser;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class LoginScreen implements Screen {
    private final LoginUser user;

    public LoginScreen(final LoginUser user) {
        this.user = user;
    }

    public Element serialize() {
        Element element = DocumentHelper.createElement("screen");
        element.addElement("name").addText("login");
        element.add(this.user.serialize());
        return element;
    }
}
